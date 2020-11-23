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
    url: '/api/blog/tag/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

/**
 * 更新标签信息
 *
 * @param  blog
 */
export function updateTagInfo(tag) {
  return request({
    url: '/api/blog/tag/update',
    method: 'put',
    data: { ...tag }
  })
}

/**
 * 删除标签信息
 * @param  id
 */
export function deleteTagInfo(id) {
  return request({
    url: '/api/blog/tag/delete',
    method: 'delete',
    params: {
      id: id
    }
  })
}

/**
 * 添加博客标签信息
 * @param  tagName
 */
export function addTagInfo(tagInfo) {
  return request({
    url: '/api/blog/tag/add',
    method: 'post',
    params: {
      tagIndex: tagInfo.tagIndex,
      tagName: tagInfo.tagName
    }
  })
}

/**
 * 根据博客标签名查询标签信息
 * @param  tagName
 */
export function getTagInfo(tagName) {
  return request({
    url: '/api/blog/tag/get',
    method: 'get',
    params: {
      tagName: tagName
    }
  })
}

/**
 * 获取所有的标签信息
 */
export function getAllTags() {
  return request({
    url: '/api/blog/tag/get/all',
    method: 'get'
  })
}

