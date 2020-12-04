import request from '@/utils/request'
import Qs from 'qs'

/**
 * 获取所有管理员信息
 * @param pageInfo
 */
export function getList(pageInfo) {
  return request({
    url: '/api/user/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

/**
 * 根据用户名查询用户信息
 * @param username
 */
export function queryByName(username) {
  return request({
    url: '/api/user/get',
    method: 'get',
    params: {
      username: username
    }
  })
}

/**
 * 注册用户信息
 * @param user
 */
export function registerUser(user) {
  return request({
    url: '/api/user/add',
    method: 'post',
    data: { ...user }
  })
}
/**
 *根据用户id删除用户信息
 * @param  id
 */
export function deleteUser(id) {
  return request({
    url: '/api/user/delete',
    method: 'delete',
    params: {
      id: id
    }
  })
}

export function updateUser(user) {
  return request({
    url: '/api/user/update',
    method: 'put',
    data: { ...user }
  })
}
