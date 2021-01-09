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
            <!--绑定回车事件@keyup.enter.native-->
            <el-input class="search-bar"
              @keyup.enter.native="searchClick"
              style="width: 300px;margin:10px 0 10px"
              placeholder="请输入需要检索的关键词..."
              v-model="keyword">
              <el-button slot="append" icon="el-icon-search" @click="searchClick"></el-button>
            </el-input>
          </el-col>
          <el-col :span="6"><i class="el-icon-user" v-on:click="login"></i></el-col>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    return {
      navBarFixed: false,
      keyword: '',
      navList: [
        { index: '/', navItem: '首页' },
        { index: '/sort', navItem: '分类' },
        { index: '/tag', navItem: '标签' },
        { index: '/archive', navItem: '归档' },
        { index: '/about', navItem: '关于我' }
      ]
    }
  },
  mounted () {
    window.addEventListener('scroll', this.watchScroll)
  },
  methods: {
    handleSelect (key, keyPath) {
      // console.log(key, keyPath)
    },
    login () {
      this.$notify({
        title: '暂未实现',
        type: 'warning'
      })
      // this.$router.push('/login')
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
  .el-icon-user {
    position: absolute;
    float: right;
    top: 16px;
    right: 30px;
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
