import * as LoginApi from '@/api/admin/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import router from '../../router'
import store from '../../store'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '', // 用户名
    nickname: '', // 用户昵称
    userId: '', // 用户id
    role: '', // 用户角色
    menus: [], // 可访问的菜单列表
    permissions: [], // 具体权限信息列表
    avatar: '', // 用户头像信息
    roles: [] // 基于vue-admin-template的权限控制角色列表
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_USER: (state, userInfo) => {
    state.nickname = userInfo.nickname
    state.userId = userInfo.userId
    state.role = userInfo.roleName
    state.menus = userInfo.menuList
    state.permissions = userInfo.permissionList
  },
  RESET_USER: (state) => {
    state.nickname = ''
    state.userId = ''
    state.role = ''
    state.menus = []
    state.permissions = []
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      LoginApi.login(userInfo).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_NAME', data.username)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取登录用户信息并记录用户权限
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      LoginApi.getInfo(state.token).then(response => {
        const { data } = response
        // console.log(data)
        if (!data) {
          return reject('用户信息获取失败，请重新登录！')
        }
        commit('SET_NAME', data.username)
        commit('SET_AVATAR', data.avatar)

        // 将获取到的用户权限信息存储到state中
        const userPermission = data
        commit('SET_USER', userPermission)
        commit('SET_ROLES', userPermission.menus) // 保存用户角色信息
        // 生成路由
        store.dispatch('generateRoutes', userPermission).then(() => {
          // 生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
          router.addRoutes(store.getters.addRouters)
        })
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 登出
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      LoginApi.logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        commit('RESET_USER')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

