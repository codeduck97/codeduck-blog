import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './assets/css/global.css'
import './plugins/element.js' // 导入自定义element组件
import store from './store' // 导入vuex

import http from '@/utils/request'

// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios

Vue.config.productionTip = http

new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app')
