import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout,
    children: [
      { path: '/', component: () => import('../views/Home') },
      { path: '/sort', component: () => import('../views/Sort') },
      { path: '/tag', component: () => import('../views/Tag') },
      { path: '/archive', component: () => import('../views/Archive') },
      { path: '/about', component: () => import('../views/About') },
      { path: '/article', component: () => import('../views/Article') },
      { path: '/500', component: () => import('../views/500') },
      { path: '/404', component: () => import('../views/404') },
      { path: '/502', component: () => import('../views/502') }

    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
