$(document).ready(function () {
    window.chartData = [];
    var t = new Date();
    for (var i = 10; i >= 0; i--) {
        var x = new Date(t.getTime() - i * 1000);
        window.chartData.push([x, Math.random()]);
    }
    // Get the context of the canvas element we want to select
    window.lineChart = new Dygraph(
        // containing div
        document.getElementById("channelChart"), chartData,
        {
            drawPoints: true,
            showRoller: true,
            rollPeriod: 2,
            labels: ['Time', 'Value']
        }
    );
});

function updateChart() {
    update($('#channelId').val(), $('#startDate').data("DateTimePicker").date(),
        $('#endDate').data("DateTimePicker").date(), $("#maxItems").val(), false);

    function update(channel, start, end, maxItems, append) {
        if (!maxItems) maxItems = 100;
        var uri;
        if (start || end) {
            uri = "/api/fetch/" + channel + "?";
            var from = start ? start.toISOString() : new Date().toISOString();
            var to = end ? end.toISOString() : new Date(). toISOString();
            uri += $.param([{name: "from", value: from}, {name: "to", value: to}, {name: "maxItems", value: maxItems}]);
        } else {
            uri = "/api/fetch/last/" + channel + "?maxItems=" + maxItems;
        }
        $.getJSON(uri, function (data) {
            window.chartData = [];
            var displayTable = false;
            $.each(data, function (key) {
                window.chartData.push([new Date(this.timeStamp + 1000 * key),
                    isNaN(this.value) ? this.value : parseFloat(this.value)]);
                displayTable = isNaN(this.value);
            });
            if (displayTable) {
                document.getElementById('channelTable').style.display = "block";
                document.getElementById('channelChart').style.display = "none";
                var $channelTable = $("#channelTable");
                var tbody = $channelTable.find("tbody");
                $.each(window.chartData, function (i, data) {
                    var tr = $('<tr>');
                    $('<td>').html(data[0]).appendTo(tr);
                    $('<td>').html(data[1]).appendTo(tr);
                    tbody.append(tr);
                });
                $channelTable.trigger("update");
            } else {
                document.getElementById('channelTable').style.display = "none";
                document.getElementById('channelChart').style.display = "block";
                if (window.chartData.length > 0) {
                    window.lineChart.updateOptions({'file': window.chartData});
                }
            }
        });


    }
}