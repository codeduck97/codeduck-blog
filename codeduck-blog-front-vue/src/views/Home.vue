<template>
    <div>
      <el-row :gutter="20" type="flex" justify="center" style="height:200px">
        <!-- 图片轮播区域 -->
        <el-col :xs="24" :sm="16" :md="16" :lg="14" :xl="12">
          <div  class="pic-box" style="width:100%;height:100%;overflow: hidden;">
            <el-image src="http://localhost:8600/upload/images/2020/11/2020117-960b1921-a6e9-43bd-b694-abd949d5cf68.jpg" fit="contain"/>
          </div>
        </el-col>
        <!-- END -->

        <!-- 图片轮播右侧推荐区域 -->
        <el-col :xs="0" :sm="8" :md="8" :lg="6" :xl="5">
          <div class="pic-blog-box-sides" style="width:100%;height:100%;overflow:hidden;margin:0px;">
            <el-image src="http://localhost:8600/upload/images/2020/11/2020117-960b1921-a6e9-43bd-b694-abd949d5cf68.jpg" fit="contain"/>
          </div>
        </el-col>
        <!-- END -->
      </el-row>
      <div class="blank"></div>
      <el-row :gutter="20" type="flex" justify="center">
        <!-- 博客列表部分 -->
        <el-col :xs="24" :sm="16" :md="16" :lg="14" :xl="12">
          <div class="blog-box" v-for="(blog,i) in blogs" :key="i">
            <el-card>
              <div class="blog-card">
                <h3 class="blog-title">
                  <a href="javascript:void(0);" @click="getArticleInfo(blog.id)">{{blog.title}}</a>
                </h3>
                <span class="blog-cover">
                  <el-image @click="getArticleInfo(blog.id)" :src="blog.cover"></el-image>
                </span>
                <div style="padding:15px">{{blog.title}}</div>
                <div class="blog-info">
                  <span style="margin-right:20px">
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
                    <i class="el-icon-time" style="margin-right:5px" />{{blog.creationTime}}
                  </span>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
        <!-- END -->

        <!-- 侧边栏区域 -->
        <el-col :xs="0" :sm="8" :md="8" :lg="6" :xl="5">
          <!-- 标签侧边框 -->
          <div  class="blog-box-side">
            <el-card>
              <div class="cloud" v-if="tags.length > 0">
                <h2 class="hometitle">标签云</h2>
                  <ul>
                    <a v-for="item in tags" :key="item.id" @click="goToList(item.id)">{{item.tagName}}</a>
                  </ul>
              </div>
            </el-card>
          </div>
          <!-- END -->

          <!-- 分类侧边框 -->
          <div  class="blog-box-side">
            <el-card>
              <div class="cloud" v-if="sorts.length > 0">
                <h2 class="hometitle">分类云</h2>
                  <ul>
                    <a v-for="item in sorts" :key="item.id" @click="goToList(item.id)">{{item.sortName}}</a>
                  </ul>
              </div>
            </el-card>
          </div>
          <!-- END -->

          <!-- 热门文章侧边框 -->
          <div class="blog-box-side">
            <el-card>
              <div class="box-hot">
               <h2 class="hometitle">热门文章</h2>
                <div class="hot-artile-first" v-if="hotBlogs[0]" style="margin-bottom:-5rem">
                  <el-image :src="hotBlogs[0].cover" />
                  <p><a>{{hotBlogs[0].title}}</a></p>
                </div>
                <div class="hot-article" v-for="(item,i) in hotBlogs" :key="i">
                  <div v-if="i!==0">
                    <el-row >
                      <el-col :span="13">
                        <span class="hot-article-cover"><el-image :src="item.cover" /></span>
                      </el-col>
                      <el-col :span="11" style="padding:12px;">
                        <h5>{{item.title}}</h5>
                        <div style="font-size:1px">{{item.creationTime}}</div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
          <!-- END -->

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
      pageInfo: {
        pageNum: 1,
        pageSize: 10
      },
      blogs: [], // 博文列表
      tags: [], // 标签列表
      sorts: [], // 分类列表
      hotBlogs: [] // 热门博文
    }
  },
  created () {
    this.getBlogs()
    this.getTags()
    this.getSorts()
    this.getHotBlogs()
  },
  methods: {
    // 获取所有博文信息
    getBlogs () {
      HomeApi.getBlogs(this.pageInfo).then(res => {
        if (res.code === 1000) {
          this.blogs = res.data.blogs
          // console.log(this.blogs)
          return
        }
        return this.$notify.error('博文信息获取失败')
      })
    },
    // 获取所有标签信息
    getTags () {
      HomeApi.getTags().then(res => {
        if (res.code === 1000) {
          this.tags = res.data
          return
        }
        return this.$notify.error('标签信息获取失败')
      })
    },
    // 获取所有分类列表信息
    getSorts () {
      HomeApi.getSorts().then(res => {
        if (res.code === 1000) {
          this.sorts = res.data
          return
        }
        return this.$notify.error('分类信息获取失败')
      })
    },
    // 获取热门文章信息
    getHotBlogs () {
      HomeApi.getHots().then(res => {
        if (res.code === 1000) {
          this.hotBlogs = res.data
          // console.log(this.hotBlogs)
          return
        }
        return this.$notify.error('热门文章信息获取失败')
      })
    },
    // 跳转到博客详细页
    getArticleInfo (blogId) {
      const routeData = this.$router.resolve({
        path: '/article',
        query: { id: blogId }
      })
      window.open(routeData.href, '_blank')
    }
  }
}
</script>

<style>
.hot-article{
  width:100%;
  margin-right: 20px;
  overflow: hidden;
}
.hot-article-cover{
  width: 30%;
}
.hot-artile-first p {
  position: relative;
  bottom: 65px;
  padding: 10px 20px;
  font-size: 20px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  background: rgba(0,0,0,0.7);
  color: #fff;
}
.hot-artile-first p a {
  color: #fff;
  }
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
.cloud{
  padding:10px;
}
.box-hot {
  padding: 10px;
}
</style>
