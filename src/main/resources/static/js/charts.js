$(document).ready(function () {
    // Get the context of the canvas element we want to select
    var ctx = document.getElementById("myChart").getContext("2d");
    var data = {
        labels: ["January", "February", "March", "April", "May", "June", "July"],
        datasets: [
            {
                label: "My First dataset",
                fill: false,
                // The properties below allow an array to be specified to change the value of the item at the given index

                // String or array - Line color
                borderColor: "rgba(220,220,220,1)",

                // String - cap style of the line. See https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineCap
                borderCapStyle: 'butt',

                // Array - Length and spacing of dashes. See https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash
                borderDash: [],

                // Number - Offset for line dashes. See https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset
                borderDashOffset: 0.0,

                // String - line join style. See https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineJoin
                borderJoinStyle: 'miter',

                // String or array - Point stroke color
                pointBorderColor: "rgba(220,220,220,1)",

                // String or array - Point fill color
                pointBackgroundColor: "#fff",

                // Number or array - Stroke width of point border
                pointBorderWidth: 1,

                // Number or array - Radius of point when hovered
                pointHoverRadius: 5,

                // String or array - point background color when hovered
                pointHoverBackgroundColor: "rgba(220,220,220,1)",

                // Point border color when hovered
                pointHoverBorderColor: "rgba(220,220,220,1)",

                // Number or array - border width of point when hovered
                pointHoverBorderWidth: 2,

                // The actual data
                data: [65, 59, 80, 81, 56, 55, 40],

                // String - If specified, binds the dataset to a certain y-axis. If not specified, the first y-axis is used.
                yAxisID: "y-axis-1"
            },
            {
                label: "My Second dataset",
                fill: false,
                backgroundColor: "rgba(220,220,220,0.2)",
                borderColor: "rgba(220,220,220,1)",
                pointBorderColor: "rgba(220,220,220,1)",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "rgba(220,220,220,1)",
                pointHoverBorderColor: "rgba(220,220,220,1)",
                pointHoverBorderWidth: 2,
                data: [28, 48, 40, 19, 86, 27, 90]
            }
        ]
    };

    var myLineChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: {responsive: true}
    });
});