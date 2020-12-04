import request from '@/utils/request'
import Qs from 'qs'

/**
 * 用户登录接口
 * @param data
 */
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data: Qs.stringify(data)
  })
}

/**
 * 根据token获取用户信息
 * @param token
 */
export function getInfo(token) {
  return request({
    url: '/api/admin/info',
    method: 'get',
    params: { token }
  })
}

/**
 * 用户退出登录接口
 */
export function logout() {
  return request({
    url: '/api/logout',
    method: 'post'
  })
}

