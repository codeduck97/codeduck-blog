<template>
  <div class="list-container">
    <el-row :gutter="20" type="flex" justify="center" style="height:200px">
      <el-col :span="24">
          <el-image fit="fill"
          src="http://localhost:8600/upload/images/2020/11/2020117-0ecc80cf-c1d3-4397-b02d-86d78e3e44e7.jpg" />
      </el-col>
    </el-row>
      <div class="blank"></div>
      <el-row :gutter="20" type="flex" justify="center">
        <!-- 博客列表部分 -->
        <el-col :xs="24" :sm="16" :md="16" :lg="14" :xl="12">
          <div class="blog-box" v-for="(blog,i) in blogs" :key="i">
            <el-card>
              <div class="blog-card">
                <h3 class="blog-title">
                  <a href="javascript:void(0);" v-html="blog.title" @click="getArticleInfo(blog.blogId)" >{{blog.title}}</a>
                </h3>
                <span class="blog-cover">
                  <el-image @click="getArticleInfo(blog.blogId)" :src="blog.coverImage"></el-image>
                </span>
                <div v-html="blog.content" style="padding:15px">{{blog.content}}</div>
                <div class="blog-info">
                  <span style="margin-right:20px" v-html="blog.author">
                    <i class="el-icon-s-custom" style="margin-right:5px" />{{blog.author}}
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-menu" style="margin-right:5px" />
                    <a>{{blog.sort}}</a>
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-view" style="margin-right:5px" />{{blog.hits}}
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-time" style="margin-right:5px" />{{blog.createTime}}
                  </span>
                </div>
              </div>
            </el-card>
          </div>
          <div class="pageination">
            <!-- 用户管理||用户列表||分页 -->
            <el-pagination
              :current-page="pageInfo.pageNum"
              :page-sizes="[2, 3, 4, 5]"
              :page-size="pageInfo.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
        <!-- END -->
      </el-row>
      <el-backtop><i class="el-icon-upload2"></i></el-backtop>
  </div>
</template>

<script>

import * as HomeApi from '../api/home'

export default {
  data () {
    return {
      blogs: [],
      keyword: '',
      pageInfo: {
        pageNum: 1,
        pageSize: 4
      },
      total: 0
    }
  },
  created () {
    this.keyword = this.$route.query.keyword
    this.getSearchList()
  },
  methods: {
    getSearchList () {
      const searchParm = {
        keyword: this.keyword,
        pageNum: this.pageInfo.pageNum,
        pageSize: this.pageInfo.pageSize
      }
      HomeApi.searchByKeyword(searchParm).then(res => {
        const data = res.data.blogIndexList
        console.log(data)
        if (data === null) {
          this.pageInfo.pageSize = 0
          return this.$notify({
            title: '未查询到相关信息',
            type: 'warning'
          })
        } else {
          this.blogs = data
          this.total = res.data.total
        }
        console.log(res)
      })
    },
    // 跳转到博客详细页
    getArticleInfo (blogId) {
      const routeData = this.$router.resolve({
        path: '/article',
        query: { id: blogId }
      })
      window.open(routeData.href, '_blank')
    },
    // 用户管理||用户列表||分页||监听pagesize改变事件
    handleSizeChange (size) {
      this.pageInfo.pageSize = size
      this.getSearchList()
    },
    // 用户管理||用户列表||分页||监听page-sizes改变
    handleCurrentChange (num) {
      this.pageInfo.pageNum = num
      this.getSearchList()
    }
  }
}
</script>
<style scoped>
  .blog-info{
    margin: 17px 0 0 0;
  }
  .blog-card{
    padding: 20px;
    overflow: hidden;
  }
  .blog-title{
    margin: 0 0 10px;
    font-size: 20px;
    overflow: hidden;
  }
  .blog-title a:hover{
    color: #337ab7;
  }
  .blog-cover{
    float: left;
    width: 30%;
    max-width: 170px;
    margin-right: 20px;
    overflow: hidden;
    cursor:pointer;
  }
  .blog-box{
    margin-bottom: 10px;
  }
  .blog-box-side{
    margin-bottom: 15px;
  }
  .blank{
    padding: 10px;
  }
  .pageination {
    margin-top: 30px;
  }
</style>
