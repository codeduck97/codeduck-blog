import axios from 'axios'
import { MessageBox } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 此处可以在发送请求前做一些配置

    if (store.getters.token) {
      // 如果本地存储中存在token，则为每一次请求设置请求头 Authorization : token
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // 请求异常处理
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  /**
   * 如果要获取http信息，例如 headers or status
   * 请返回  response => response
  */

  /**
   * 通过自定义代码确定请求状态
   * 您还可以通过HTTP状态代码来判断状态
   */
  response => {
    const res = response.data
    // 如果响应头中有新的token值，则对本地token进行刷新
    if (response.headers['refresh-token']) {
      setToken(response.headers['refresh-token'])
    }
    // 响应码为50001表示token已过期，需要重新登录
    if (res.code === 5001 || res.code === 5000) {
      MessageBox.confirm(res.msg, {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    }
    return res
  },
  error => {
    console.log('err' + error) // for debug
    // Message({
    //   message: error.message,
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    return Promise.reject(error)
  }
)

export default service
