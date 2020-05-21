import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import axios from 'axios'
import EVueContextmenu from 'e-vue-contextmenu'
import './utils/time_script.js'
import './assets/js/echarts.min.js'
// import './assets/js/echarts.simple.min.js'
// import 'https://cdn.bootcss.com/echarts/3.2.2/echarts.simple.min.js'
format(new Date())
Vue.use(EVueContextmenu)
function format (date) {
  var year = date.getFullYear() + ''
  var month = date.getMonth() + 1 + ''
  if (month.length < 2) { // 比如3月 要补成03
    month = '0' + month
  }
  var day = date.getDate() + ''
  if (day.length < 2) {
    day = '0' + day
  }
  return `${year}-${month}-${day}`
}

axios.defaults.baseURL = 'http://backend:8082/'
axios.interceptors.request.use(config => {
  console.log(config)
  if (window.sessionStorage.getItem('jwt') !== null) {
    config.headers.Authorization = window.sessionStorage.getItem('jwt')
  }
  return config
})

Vue.prototype.$http = axios
Vue.config.productionTip = false
Vue.prototype.format = format

var vue = new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
window.vue = vue
