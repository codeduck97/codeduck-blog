import request from '@/utils/request'
// import Qs from 'qs'

/* 获取分类列表 */
export function getTagList () {
  return request({
    url: '/api/tag/list',
    method: 'get'
  })
}

/* 根据分类id获取博文列表 */
export function getArticlesByTagId (tagId) {
  return request({
    url: '/api/tag/articles',
    method: 'get',
    params: {
      id: tagId
    }
  })
}
