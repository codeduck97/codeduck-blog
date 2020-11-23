import request from '@/utils/request'
// import Qs from 'qs'

/* 根据id获取博文信息 */
export function getArticleInfo (id) {
  return request({
    url: '/api/article/info',
    method: 'get',
    params: {
      id: id
    }
  })
}

/* 获取分类字典信息 */
export function getSortDic () {
  return request({
    url: '/api/article/sort',
    method: 'get'
  })
}

/* 获取标签字典信息 */
export function getTagDic () {
  return request({
    url: '/api/article/tag',
    method: 'get'
  })
}
