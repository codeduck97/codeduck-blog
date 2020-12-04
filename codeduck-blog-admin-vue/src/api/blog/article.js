import request from '@/utils/request'
import Qs from 'qs'

/**
 * 分页获取博客信息
 * Qs.stringify()将对象序列化成URL的形式，以&进行拼接。
 *
 * @param pageInfo
 */
export function list(pageInfo) {
  return request({
    url: '/api/blog/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

/**
 * 更新博客信息
 *
 * @param  blog
 */
export function updateBlog(blog) {
  return request({
    url: '/api/blog/update',
    method: 'put',
    data: { ...blog }
  })
}

/**
 * 删除博客信息
 * @param  blog
 */
export function deleteBlog(id) {
  return request({
    url: '/api/blog/delete',
    method: 'delete',
    params: {
      id: id
    }
  })
}

/**
 * 添加博客
 * @param  blog
 */
export function addBlog(blog) {
  return request({
    url: '/api/blog/add',
    method: 'post',
    data: { ...blog }
  })
}

export function getBlogByTitle(title) {
  return request({
    url: '/api/blog/get',
    method: 'get',
    params: {
      title: title
    }
  })
}

