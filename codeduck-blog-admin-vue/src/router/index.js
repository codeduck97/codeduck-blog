import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRouterMap
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '仪表盘', icon: 'dashboard' }
    }]
  },
  {
    path: '/blog',
    redirect: '/blog/write',
    component: Layout,
    meta: { title: '博客管理', icon: 'el-icon-s-management' },
    children: [
      {
        path: 'write',
        name: '写文章',
        component: () => import('@/views/blog/Write'),
        meta: { title: '写文章', icon: 'form' }
      },
      {
        path: 'management',
        name: '博文管理',
        component: () => import('@/views/blog/Article'),
        meta: { title: '博文管理', icon: 'el-icon-s-management' }
      },
      {
        path: 'sort',
        name: '分类管理',
        component: () => import('@/views/blog/Sort'),
        meta: { title: '分类管理', icon: 'el-icon-s-grid' }
      },
      {
        path: 'tag',
        name: '标签管理',
        component: () => import('@/views/blog/Tag'),
        meta: { title: '标签管理', icon: 'el-icon-collection-tag' }
      }
    ]
  },
  {
    path: '/picture',
    component: Layout,
    redirect: '/picture/list',
    name: '图片管理',
    meta: {
      title: '图片管理',
      icon: 'el-icon-picture',
      role: ['pic']
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/picture/Picture'),
        meta: { title: '图片管理', icon: 'el-icon-picture' },
        menu: 'pic'
      },
      {
        path: 'sort',
        component: () => import('@/views/picture/Sort'),
        meta: { title: '分类管理', icon: 'el-icon-picture' },
        menu: 'pic-sort'
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://localhost:8201/doc.html',
        meta: { title: 'Api 文档', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

export const asyncRouterMap = [
  {
    path: '/user',
    component: Layout,
    redirect: '/user',
    name: 'User',
    meta: { title: '用户管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'info',
        name: 'UserInfo',
        component: () => import('@/views/user/User'),
        meta: { title: '用户列表', icon: 'user' },
        menu: 'user'
      },
      {
        path: 'role',
        name: '权限管理',
        component: () => import('@/views/user/Role'),
        meta: { title: '权限管理', icon: 'tree' },
        menu: 'role'
      }
    ]
  }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
