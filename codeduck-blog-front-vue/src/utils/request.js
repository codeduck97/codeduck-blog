import axios from 'axios'

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
    // 响应码为50001表示token已过期，需要重新登录
    return res
  },
  error => {
    return Promise.reject(error)
  }
)

export default service
