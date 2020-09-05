// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)',
    },
    graphic: {
      type: 'text',
      left: 'center',
      top: 'center',
      style: {
        text: '师资来源',
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 'bold',
        fill: 'rgb(128, 128, 128)',
      }
    },
    series: [
        {
          name: name,
          type: 'pie',
          selectedMode: 'single',
          avoidLabelOverlap: false,
          startAngle: 180,
          radius: ['32%', '42%'],
          silent:true,
          label: {
              show: false,
              position: 'center'
          },
          labelLine: {
            normal: {
              show: false 
            }
          },
          data: [{
              value: 300,
              name: '大型国企',
              itemStyle : {normal : {color :'#fc7675'}}
            },
            {
              value: 100,
              name: '金融租赁',
              itemStyle : {normal : {color :'#feca4a'}}
            },
            {
              value: 100,
              name: '四大投行',
              itemStyle : {normal : {color :'#26d9e0'}}
            },
            {
              value: 500,
              name: '协会专家',
              itemStyle : {normal : {color :'#2779fe'}}
            }
          ]
        },
        {
          name: '师资来源',
          type: 'pie',
          color: ['#fb5859', '#ffaf20', '#00cbd6', '#0060fe'],
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          startAngle: 180,
          label: {
            fontSize: '20',
            align: 'center',
            formatter: '{d}% \n {b}',
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '30',
              fontWeight: 'bold',
              align: 'center',
            },
          },
          labelLine: {
            show: false,
            length: 30,
            length2: 80
          },
          data: [{
              value: 300,
              name: '大型国企'
            },
            {
              value: 100,
              name: '金融租赁'
            },
            {
              value: 100,
              name: '四大投行'
            },
            {
              value: 500,
              name: '协会专家'
            }
          ]
        }
      ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);