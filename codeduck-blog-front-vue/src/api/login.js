import request from '@/utils/request'

export function login (params) {
  return request({
    url: '/api/login',
    method: 'post',
    data: { ...params }
  })
}

export function register (params) {
  return request({
    url: '/api/login/register',
    method: 'post',
    data: { ...params }
  })
}

export function logout (token) {
  return request({
    url: 'api/login/logout/' + token,
    method: 'get'
  })
}
