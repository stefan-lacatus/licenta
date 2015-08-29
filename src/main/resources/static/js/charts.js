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
        document.getElementById("myChart"), chartData,
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
        $('#endDate').data("DateTimePicker").date(), false);

    function update(channel, start, end, append) {
        var uri;
        if (start || end) {
            uri = "/api/fetch/" + channel + "?";
            var from = start ? start.toISOString() : new Date().toISOString();
            var to = end ? end.toISOString() : new Date(). toISOString();
            uri += $.param([{name: "from", value: from}, {name: "to", value: to}]);
        } else {
            uri = "/api/fetch/last/" + channel + "?maxItems=100";
        }
        $.getJSON(uri, function (data) {
            window.chartData = [];
            $.each(data, function (key) {
                window.chartData.push([new Date(this.timeStamp + 1000 * key), parseFloat(this.value)]);
            });
            window.lineChart.updateOptions({'file': window.chartData});
        });


    }
}