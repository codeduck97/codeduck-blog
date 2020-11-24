import request from '@/utils/request'
// import Qs from 'qs'

/* 获取分类列表 */
export function getSortList () {
  return request({
    url: '/api/sort/list',
    method: 'get'
  })
}

/* 根据分类id获取博文列表 */
export function getArticlesBySortId (sortId) {
  return request({
    url: '/api/sort/articles',
    method: 'get',
    params: {
      id: sortId
    }
  })
}
