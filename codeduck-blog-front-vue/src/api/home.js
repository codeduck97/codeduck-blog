import request from '@/utils/request'
// import Qs from 'qs'

/* 获取所有博客信息 */
export function getBlogs (pageInfo) {
  return request({
    url: '/api/home/list',
    method: 'get',
    params: {
      pageNum: pageInfo.pageNum,
      pageSize: pageInfo.pageSize
    }
  })
}

/* 获取所有标签信息 */
export function getTags () {
  return request({
    url: '/api/home/tags',
    method: 'get'
  })
}

/* 获取所有分类信息 */
export function getSorts () {
  return request({
    url: '/api/home/sorts',
    method: 'get'
  })
}

/* 获取热门文章 */
export function getHots () {
  return request({
    url: '/api/home/hot',
    method: 'get'
  })
}
