import request from '@/utils/request'
import Qs from 'qs'

/**
 * 分页获取博客分类列表
 * Qs.stringify()将对象序列化成URL的形式，以&进行拼接。
 *
 * @param pageInfo
 */
export function list(pageInfo) {
  return request({
    url: '/api/blog/sort/list',
    method: 'post',
    data: Qs.stringify(pageInfo)
  })
}

/**
 * 更新博客分类信息
 *
 * @param  sort
 */
export function updateSort(sort) {
  return request({
    url: '/api/blog/sort/update',
    method: 'put',
    data: { ...sort }
  })
}

/**
 * 删除博客分类信息
 * @param  id
 */
export function deleteSort(id) {
  return request({
    url: '/api/blog/sort/delete',
    method: 'delete',
    params: {
      id: id
    }
  })
}

/**
 * 添加博客分类
 * @param  sortInfo
 */
export function addSort(sortInfo) {
  return request({
    url: '/api/blog/sort/add',
    method: 'post',
    params: {
      sortIndex: sortInfo.sortIndex,
      sortName: sortInfo.sortName
    }
  })
}

/**
 * 根据分类名查询分类
 * @param  sortName
 */
export function getSortInfo(sortName) {
  return request({
    url: '/api/blog/sort/get',
    method: 'get',
    params: {
      sortName: sortName
    }
  })
}

/**
 * 获取所有的分类信息
 */
export function getAllSorts() {
  return request({
    url: '/api/blog/sort/get/all',
    method: 'get'
  })
}

