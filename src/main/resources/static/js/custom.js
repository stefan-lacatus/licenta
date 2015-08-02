/**
 *   I don't recommend using this plugin on large tables, I just wrote it to make the demo useable. It will work fine for smaller tables
 *   but will likely encounter performance issues on larger tables.
 *
 *        <input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="Filter Developers" />
 *        $(input-element).filterTable()
 *
 *    The important attributes are 'data-action="filter"' and 'data-filters="#table-selector"'
 */
(function () {
    'use strict';
    var $ = jQuery;
    $.fn.extend({
        filterTable: function () {
            return this.each(function () {
                $(this).on('keyup', function (e) {
                    $('.filterTable_no_results').remove();
                    var $this = $(this), search = $this.val().toLowerCase(), target = $this.attr('data-filters'), $target = $(target), $rows = $target.find('tbody tr');
                    if (search == '') {
                        $rows.show();
                    } else {
                        $rows.each(function () {
                            var $this = $(this);
                            $this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
                        });
                        if ($target.find('tbody tr:visible').size() === 0) {
                            var col_count = $target.find('tr').first().find('td').size();
                            var no_results = $('<tr class="filterTable_no_results"><td colspan="' + col_count + '">No results found</td></tr>');
                            $target.find('tbody').append(no_results);
                        }
                    }
                });
            });
        }
    });
    $('[data-action="filter"]').filterTable();
})(jQuery);

$(function () {
    // attach table filter plugin to inputs
    $('[data-action="filter"]').filterTable();

    $('.container').on('click', '.panel-heading span.filter', function (e) {
        var $this = $(this),
            $panel = $this.parents('.panel');

        $panel.find('.panel-body').slideToggle();
        if ($this.css('display') != 'none') {
            $panel.find('.panel-body input').focus();
        }
    });
    $('[data-toggle="tooltip"]').tooltip();
});

$(document).ready(function () {
    $('a[data-toggle=modal], button[data-toggle=modal]').click(function () {
        var data_id = '';
        if (typeof $(this).data('id') !== 'undefined') {
            data_id = $(this).data('id');
        }
        $('#entityId').val(data_id);
    })
});

(function ($, undefined) {
    $(function () {
        var bodyTag = $('body');
        // enabling back button with JavaScript
        setTimeout(function () {
            $(window).bind("popstate", function (e) {
                $("#pageContent").load(location.href);
            });
        }, 0);

        // partial content rendering when clicking on a link
        bodyTag.on("click", "a.render-partial", allowNewTabShortcuts(function (e) {
            var url = this.href;
            $("#pageContent").load(url);
            history.pushState(null, null, url);
        }));

        // partial content rendering when submitting a form via post
        bodyTag.on("submit", "form[method='post'].render-partial", function (e) {
            e.preventDefault();
            var form = $(this);
            var url = form.attr("action");
            $.post(url, form.serialize(), function (html, textStatus, jqXHR) {
                // custom event that anybody can listen to
                form.trigger("submitComplete");

                $("#pageContent").html(html);
                // if the current url does not reflect the content, the correct url may be specified by a header attribute
                var redirectUrl = jqXHR.getResponseHeader("redirectUrl");
                if (redirectUrl) {
                    history.pushState(null, null, redirectUrl);
                } else {
                    history.pushState(null, null, url);
                }
            });
        });

        // content rendering in a modal window when submitting a form via post
        bodyTag.on("submit", "form[method='post'].render-modal", function (e) {
            e.preventDefault();
            var form = $(this);
            var url = form.attr("action");
            $.post(url, form.serialize(), function (html, textStatus, jqXHR) {
                // custom event that anybody can listen to
                form.trigger("submitComplete");
                var genericModalTag = $("#genericModal");

                genericModalTag.find(".modal-body").html(html);
                genericModalTag.find(".render-partial").removeClass("render-partial").addClass("render-modal");
            });
        });

        // partial content rendering when submitting a form via get
        bodyTag.on("submit", "form[method='get'].render-partial", function (e) {
            e.preventDefault();
            var form = $(this);
            var queryParams = form.serialize();
            var url = form.attr("action") + "?" + queryParams;
            $("#pageContent").load(url);
            history.pushState(null, null, url);
        });

        // content rendering in a modal window when clicking on a link
        bodyTag.on("click", "a.render-modal", allowNewTabShortcuts(function (e) {
            var genericModalTag = $("#genericModal");
            genericModalTag.find(".modal-body").load(this.href, function () {
                $("#genericModal").find(".render-partial").removeClass("render-partial").addClass("render-modal");
            });
            genericModalTag.modal("show");
        }));
    });

    function allowNewTabShortcuts(callback) {
        return function (e) {
            // users want to open the page in a new tab - let them do it!
            if (!e.ctrlKey && !e.metaKey) {
                e.preventDefault();
                callback.apply(this, arguments);
            }
        };
    }

    function getParameterByName(uri, name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(uri);
        if (results == null) {
            return "";
        }

        return decodeURIComponent(results[1].replace(/\+/g, " "));
    }
})(jQuery);