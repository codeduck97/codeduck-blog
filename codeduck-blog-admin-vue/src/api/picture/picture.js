import request from '@/utils/request'
import Qs from 'qs'

/**
 * 分页获取博客标签列表
 * Qs.stringify()将对象序列化成URL的形式，以&进行拼接。
 *
 * @param pageInfo
 */
export function list(pageInfo) {
  return request({
    url: '/api/pic/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

export function deletePic(param) {
  return request({
    url: '/api/pic/delete',
    method: 'post',
    data: param
  })
}

