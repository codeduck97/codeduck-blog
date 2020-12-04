import request from '@/utils/request'
import Qs from 'qs'

export function getList(pageInfo) {
  return request({
    url: '/api/role/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

export function getPermissionMenus() {
  return request({
    url: '/api/role/permission',
    method: 'get'
  })
}

export function addRole(roleInfo) {
  return request({
    url: '/api/role/add',
    method: 'post',
    data: { ...roleInfo }
  })
}

export function deleteRole(roleId) {
  return request({
    url: '/api/role/' + roleId,
    method: 'delete'
  })
}

export function updateRole(roleInfo) {
  return request({
    url: '/api/role/update',
    method: 'put',
    data: { ...roleInfo }
  })
}

export function getRolePermission(roleId) {
  return request({
    url: '/api/role/permission/' + roleId,
    method: 'get'
  })
}

