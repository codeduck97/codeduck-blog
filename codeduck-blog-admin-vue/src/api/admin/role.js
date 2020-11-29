import request from '@/utils/request'
// import Qs from 'qs'

export function getRoleList() {
  return request({
    url: '/api/role/list',
    method: 'get'
  })
}

export function getAllPermissions() {
  return request({
    url: '/api/role/permission',
    method: 'get'
  })
}
