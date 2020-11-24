import request from '@/utils/request'
// import Qs from 'qs'

/* 获取博文创建时间（以年月为单位） */
export function getDate () {
  return request({
    url: '/api/archive/date',
    method: 'get'
  })
}

/* 根据博文创建时间获取博文信息 */
export function getArticlesByDate (date) {
  return request({
    url: '/api/archive/articles',
    method: 'get',
    params: {
      date: date
    }
  })
}
