import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
// https://vuex.vuejs.org/zh/guide/ 关于vuex的简单介绍
export default new Vuex.Store({
  state: {
    user: {
      // 本地存储，在项目打开的时候会判断本地存储中是否有 user 这个对象存在，如果存在就取出来并获得 username 的值，否则则把 username 设置为空。这样我们只要不清除缓存，登录的状态就会一直保存
      username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
    }
  },
  mutations: {
    login (state, user) {
      state.user = user
      window.localStorage.setItem('user', JSON.stringify(user))
    },
    logout (state) {
      state.user = []
      window.localStorage.removeItem('user')
    }
  }
})
