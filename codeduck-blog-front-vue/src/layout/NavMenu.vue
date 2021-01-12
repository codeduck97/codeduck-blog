<template>
  <div :class="navBarFixed == true ? 'navBarWrap':''">
    <el-row type="flex" justify="center">
      <el-col :span="24">
        <el-menu
          class="el-menu-demo"
          router
          mode="horizontal"
          @select="handleSelect"
          background-color="#181818"
          text-color="#FFFFFF"
          active-text-color="#00A7EB">
          <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.index">
            {{item.navItem}}
          </el-menu-item>
          <el-col :span="8">
            <router-link to="/">
              <a class="brand">
                Code Duck
              </a>
            </router-link>
          </el-col>
          <el-col :span="10">
            <div class="search-box">
              <!--绑定回车事件@keyup.enter.native-->
              <el-input class="search-bar"
                @keyup.enter.native="searchClick"
                style="width: 300px;margin:10px 0 10px"
                placeholder="请输入需要检索的关键词..."
                v-model="keyword">
                <el-button slot="append" icon="el-icon-search" @click="searchClick"></el-button>
              </el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="login_avatar">
              <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link" >
                  <i class="el-icon-user"></i>
                </span>

                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="login" v-show="!isLogin">登录</el-dropdown-item>
                  <el-dropdown-item command="goUserInfo" v-show="isLogin">个人中心</el-dropdown-item>
                  <el-dropdown-item command="logout" v-show="isLogin">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </span>
          </el-col>
        </el-menu>
      </el-col>
    </el-row>
    <login-box v-if="showLogin" @closeLoginBox="closeLoginBox"></login-box>
    <person-center ref="personCenter"></person-center>
  </div>
</template>

<script>
import loginBox from '@/components/LoginBox'
import personCenter from '@/components/PersonCenter'
import * as LoginApi from '@/api/login'
import * as TokenUtil from '../utils/auth'

export default {
  components: { loginBox, personCenter },
  name: 'NavMenu',
  data () {
    return {
      drawer: false,
      navBarFixed: false,
      keyword: '',
      isLogin: false,
      showLogin: false, // 显示登录框
      navList: [
        { index: '/', navItem: '首页' },
        { index: '/sort', navItem: '分类' },
        { index: '/tag', navItem: '标签' },
        { index: '/archive', navItem: '归档' },
        { index: '/about', navItem: '关于我' }
      ]
    }
  },
  created () {
    this.initUserState()
  },
  mounted () {
    window.addEventListener('scroll', this.watchScroll)
  },
  methods: {
    initUserState () {
      console.log(TokenUtil.getToken())

      if (TokenUtil.getToken() === undefined) {
        this.isLogin = false
      } else {
        this.isLogin = true
      }
    },
    handleSelect (key, keyPath) {
      // console.log(key, keyPath)
    },
    handleCommand (command) {
      switch (command) {
        case 'logout' : {
          this.logout()
          break
        }
        case 'login' : {
          this.login()
          break
        }
        case 'goUserInfo' : {
          // 打开抽屉
          this.$refs.personCenter.drawer = true
          // 获取评论列表
          // this.getCommentList()

          // 获取点赞列表
          // this.getPraiseList()

          // 获取反馈列表
          // this.getFeedback()
          break
        }
      }
    },
    logout () {
      const token = TokenUtil.getToken()
      if (token === 'undefined') {
        return this.$message.success('退出成功')
      } else {
        LoginApi.logout(token).then(res => {
          if (res.code === 1000) {
            console.log('cookie已被移除')
            TokenUtil.removeToken()
            this.$message.success('退出成功')
            return window.location.reload()
          } else {
            this.$message.error('系统错误')
          }
        })
      }
    },
    login () {
      this.showLogin = true
    },
    closeLoginBox: function () {
      this.showLogin = false
    },
    searchClick () {
      if (this.keyword === '') {
        return this.$message.warning('请输入需要检索的关键词')
      } else {
        const routeData = this.$router.resolve({
          path: '/search',
          query: { keyword: this.keyword }
        })
        window.open(routeData.href, '_blank')
      }
    },
    watchScroll () {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop // 当滚动超过 50 时，实现吸顶效果
      if (scrollTop > 0) {
        this.navBarFixed = true
      } else {
        this.navBarFixed = false
      }
    }
  }
}
</script>

<style scoped>
  .brand{
    position: absolute;
    top: 24%;
    left: 50%;
    font-size: 20px;
    font-weight: bold;
    font-family: "Comic Sans MS";
    color: #fff;
  }
  .login_avatar {
    margin-top: 15px;
    position: absolute;
    left: 96%;
  }
  .el-icon-user {
    color: #fff;
    font-size: 25px;
    cursor: pointer;
    outline:0;
  }
  .navBarWrap{
    width:100%;
    position:fixed;
    top:0;
    z-index:999;
  }
  .search-bar {
    position: absolute;
    left: 70%;
  }
</style>
