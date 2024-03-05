// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Area Chart Example
var ctx = document.getElementById("myAreaChart");

var chart_data_lst = {{resultData|tojson}}

var quality_p = chart_data_lst[1].count(*);
var quality_n = chart_data_lst[2].count(*);
var color_p = chart_data_lst[3].count(*);
var color_n = chart_data_lst[4].count(*);
var price_p = chart_data_lst[5].count(*);
var price_n = chart_data_lst[6].count(*);
var delivery_p = chart_data_lst[7].count(*);
var delivery_n = chart_data_lst[8].count(*);

var quality = quality_p + quality_n;
var color = color_p + color_n;
var price = price_p + price_n;
var delivery = delivery_p + delivery_n;

var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ["배송", "가격", "품질", "색상"],
    datasets: [{
      label: "총 언급 수",
      lineTension: 0.3,
      backgroundColor: "rgba(2,117,216,0.2)",
      borderColor: "rgba(2,117,216,1)",
      pointRadius: 5,
      pointBackgroundColor: "rgba(2,117,216,1)",
      pointBorderColor: "rgba(255,255,255,0.8)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "rgba(2,117,216,1)",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: [delivery, price, quality, color],
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: 2500,
          maxTicksLimit: 5
        },
        gridLines: {
          color: "rgba(0, 0, 0, .125)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});