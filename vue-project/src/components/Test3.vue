<template>
  <div>
    <el-button @click="increase">click</el-button>
    <div id="div1"></div>
    <!-- <div id='container' style="height: 500px"></div> -->
    <!-- <div id='container' style="height:500px;width:500px"></div> -->

    <el-drawer title="上一周和这一周每一天您浏览的日记总数" :visible.sync="drawer" :size="size2" @opened="$opened" @closed="$closed">

      <!-- <span>我来啦!</span> -->


      <div class="demo-drawer__content">
        <el-row style="text-align:center">
          <el-button icon="el-icon-refresh" circle @click='refresh'></el-button>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple">
              <div id='container' style="height:500px;"></div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content bg-purple-light">
              <div class="grid-content bg-purple">
                <div id='container2' style="height:500px;"></div>
              </div>
            </div>
          </el-col>
        </el-row>

      </div>
    </el-drawer>

  </div>

</template>
<script>
  import echarts from '../assets/js/echarts.simple.min.js'
  // import echarts from '../assets/js/echarts.simple.min.js'


  export default {
    props: ['drawer', 'size2'],
    data() {
      return {
        data: 1,
        option: {},  // 上一周的信息
        option2:{},  // 这一周的信息
        myChart: '',
        myChart2: ''

        // drawer: false
      }
    },
    methods: {
      $closed() {
        this.$emit('toggleStatistics')
      },
      $opened() {
        var dom = document.getElementById('container')
        this.myChart = echarts.init(dom)
        var app = {}
        // alert(this.myChart)
        this.option = {
          title: {
            text: 'helloasdsadsdsadassadasds',
            left: 'center'
          },
          xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value',
            minInterval: 1 //只显示整数
          },
          series: [{
            data: [0, 0, 0, 0, 0, 0, 0],
            type: 'bar',
            label: {
              show: true, // 开启显示
              // rotate: 70, // 旋转70度
              position: 'top', // 在上方显示
              distance: 20, // 距离图形元素的距离。当 position 为字符描述值（如 'top'、'insideRight'）时候有效。
              verticalAlign: 'middle',
              textStyle: { // 数值样式
                color: 'black',
                fontSize: 12
              }
            }

          }]
        }
        var dom2 = document.getElementById('container2')
        this.myChart2 = echarts.init(dom2)
        // alert(this.myChart)
        this.option2 = {
          title: {
            text: 'helloasdsadsdsadassadasds',
            left: 'center'
          },
          xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value',
            minInterval: 1 //只显示整数
          },
          series: [{
            data: [0, 0, 0, 0, 0, 0, 0],
            type: 'bar',
            label: {
              show: true, // 开启显示
              // rotate: 70, // 旋转70度
              position: 'top', // 在上方显示
              distance: 20, // 距离图形元素的距离。当 position 为字符描述值（如 'top'、'insideRight'）时候有效。
              verticalAlign: 'middle',
              textStyle: { // 数值样式
                color: 'black',
                fontSize: 12
              }
            }

          }]
        }
        this.refresh()
        // this.getNoteStatisticsOfLastWeek()
        console.log(this.option)
        // this.myChart.setOption(this.option, true)

      },
      sayHello() {
        alert("Hello From Cookie~")
      },
      increase() {
        this.drawer = !this.drawer
        // this.option.series[0].data[0]++;
        // this.myChart.setOption(this.option, true)
        // this.data++;

      },
      async refresh() {
        await this.getNoteStatisticsOfLastWeek()
        await this.getNoteStatisticsOfThisWeek()
        var dom = document.getElementById('container')
        this.myChart.setOption(this.option, true)
        this.myChart2.setOption(this.option2, true)

      },
      async getNoteStatisticsOfLastWeek() {
        // 首先要获取当前时间
        // var date = new Date()
        // var year = date.getFullYear()
        // var month = date.getMonth() + 1
        // var day = date.getDate()
        var localDateTime = this.format(new Date()) //2020-03-21
        var formattedTime = `${localDateTime} 00:00:00`
        // console.log(`/note_statistics_last_week?"localDateTime=${formattedTime}"`)

        const {
          data: res
        } = await this.$http.get(`/note_statistics_last_week?localDateTime=${formattedTime}`)
        var newData = res.data
        var data = this.option.series[0].data
        // 假定options.data和长度和当前data的长度一致

        console.log(newData)
        for (var i = 0; i < data.length && i < newData.length; i++) {
          // console.log(newData[i].count)
          data[i] = newData[i]['count']
        }
        // console.log(data)
        // this.refresh()
      },
      async getNoteStatisticsOfThisWeek() {
        // 首先要获取当前时间
        // var date = new Date()
        // var year = date.getFullYear()
        // var month = date.getMonth() + 1
        // var day = date.getDate()
        var localDateTime = this.format(new Date()) //2020-03-21
        var formattedTime = `${localDateTime} 00:00:00`
        // console.log(`/note_statistics_last_week?"localDateTime=${formattedTime}"`)

        const {
          data: res
        } = await this.$http.get(`/note_statistics_this_week?localDateTime=${formattedTime}`)
        var newData = res.data
        var data = this.option2.series[0].data
        // 假定options.data和长度和当前data的长度一致

        console.log(newData)
        for (var i = 0; i < data.length && i < newData.length; i++) {
          // console.log(newData[i].count)
          data[i] = newData[i]['count']
        }
        // console.log(data)
        // this.refresh()
      }
    },
    mounted() {
      // this.refresh()
      // test
      // this.getNoteStatisticsOfLastWeek()


      // var dom = document.getElementById('container')

      // alert(document.getElementById('div1'))
      // alert(document.getElementById('div2'))
      // alert(8)
      // alert(dom)
      // this.myChart = echarts.init(dom)
      // alert(9)
      // var app = {}
      // // alert(this.myChart)
      // this.option = {

      //   xAxis: {
      //     type: 'category',
      //     data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      //   },
      //   yAxis: {
      //     type: 'value',
      //     minInterval: 1//只显示整数
      //   },
      //   series: [{
      //     data: [0, 0, 0, 0, 0, 0, 0],
      //     type: 'bar'
      //   }]
      // }
      // this.getNoteStatisticsOfLastWeek()
      // console.log(this.option)
      // this.myChart.setOption(this.option, true)
    },
    created() {

    },
    watch: {
      data(newVal, oldVal) {
        // alert(`oldVal=${oldVal};newVal=${newVal}`)
      }
    }
  }

</script>

<style lang="less" scoped>

</style>
