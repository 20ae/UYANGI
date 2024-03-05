// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Bar Chart Example
var ctx = document.getElementById("myBarChart");

var chart_data_lst = {{resultData|tojson}}

var quality_p = chart_data_lst[1].count(*);
var quality_n = chart_data_lst[2].count(*);
var color_p = chart_data_lst[3].count(*);
var color_n = chart_data_lst[4].count(*);
var price_p = chart_data_lst[5].count(*);
var price_n = chart_data_lst[6].count(*);
var delivery_p = chart_data_lst[7].count(*);
var delivery_n = chart_data_lst[8].count(*);

var myLineChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: ["배송", "가격", "품질", "색상"],
    datasets: [{
      label: "해당 키워드 긍정 빈도",
      backgroundColor: "rgba(2,117,216,1)",
      borderColor: "rgba(2,117,216,1)",
      data: [delivery_p, price_p, quality_p, color_p],
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: '카테고리'
        },
        gridLines: {
          display: true
        },
        ticks: {
          maxTicksLimit: 4
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: 1000,
          maxTicksLimit: 5
        },
        gridLines: {
          display: true
        }
      }],
    },
    legend: {
      display: false
    }
  }
});