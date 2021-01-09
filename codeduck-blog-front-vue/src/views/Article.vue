<template>
  <article>
    <div class="blog-container">
      <el-row type="flex" justify="center">
        <el-col :xs="24" :sm="24" :md="24" :lg="18" :xl="15">
          <div :model="blog" style="padding:20px">
            <el-card>
              <div class="card-body">
                <div class="blog-main">
                  <a class="blog-title" v-if="blog.title">{{blog.title}}</a>
                </div>
                <div class="blog-info">
                  <span style="margin-right:20px">
                    <i class="el-icon-s-custom" style="margin-right:5px" />{{blog.author}}
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-menu" style="margin-right:5px" />
                    <a>{{sortDic.get(blog.sortId)}}</a>
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-view" style="margin-right:5px" />{{blog.hits}}
                  </span>
                  <span style="margin-right:20px">
                    <i class="el-icon-time" style="margin-right:5px" />{{blog.creationTime}}
                  </span>
                </div>
                <div class="blog-tag">
                  <a
                    href="javascript:void(0);"
                    target="_blank"
                  >{{tagDic.get(blog.tagId)}}</a>
                </div>
                <el-divider><i class="el-icon-reading"></i></el-divider>

                <!-- 博文内容显示区域 -->
                <div v-html="blogContent" style="with:80%;margin-top:20px"></div>

                <!-- 评论区域 -->
                <div class="comment-container">
                  <a>文章评论</a>
                  <el-divider><i class="el-icon-edit"></i></el-divider>
                  <el-input
                    type="textarea"
                    :rows="5"
                    placeholder="留下评论吧……"
                    v-model="blogComment">
                  </el-input>
                  <div class="comment-button">
                    <el-row type="flex" justify="end">
                      <el-col :span="2"><el-button type="primary" @click="submitComment">提交</el-button></el-col>
                      <el-col :span="1.5"><el-button @click="clearComment">清空</el-button></el-col>
                    </el-row>
                    <span></span>
                    <span></span>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-backtop><i class="el-icon-upload2"></i></el-backtop>
  </article>
</template>

<script>
import * as ArticleApi from '@/api/article'
import marked from 'marked'
import hljs from 'highlight.js'
import '../../public/static/highlight/styles/monokai-sublime.css'
export default {
  data () {
    return {
      blogContent: '', // 博文内容
      blogId: '',
      blog: '',
      sortDic: new Map(), // 分类字典
      tagDic: new Map(), // 标签字典
      blogComment: '' // 评论内容
    }
  },
  created () {
    this.blogId = this.$route.query.id
    this.getArticleInfo()
    this.getSortDic()
    this.getTagDic()
    hljs.initHighlightingOnLoad()
  },
  mounted () {
  },
  methods: {
    /* 获取根据id获取博文信息 */
    getArticleInfo () {
      ArticleApi.getArticleInfo(this.blogId).then(res => {
        if (res.code !== 1000) {
          return this.$router.push({ path: '/404' })
        }
        this.blog = res.data
        marked.setOptions({
          renderer: new marked.Renderer(),
          gfm: true,
          pedantic: false,
          sanitize: false,
          tables: true,
          breaks: false,
          smartLists: true,
          smartypants: false,
          highlight: function (code) {
            return hljs.highlightAuto(code).value
          }
        })
        this.blogContent = marked(this.blog.content).replace(/<pre>/g, "<pre class='hljs'>")
      })
    },
    /* 加载分类字典信息 */
    getSortDic () {
      ArticleApi.getSortDic().then(res => {
        // 将 后端的map对象转为前端的map对象
        const mapData = new Map(Object.entries(res.data))
        this.sortDic = mapData
      })
    },
    /* 加载标签字典信息 */
    getTagDic () {
      ArticleApi.getTagDic().then(res => {
        // 将 后端的map对象转为前端的map对象
        const mapData = new Map(Object.entries(res.data))
        this.tagDic = mapData
      })
    },
    /* 提交评论 */
    submitComment () {
      return this.$notify.info('暂未实现该功能……')
    },
    /* 清空评论内容 */
    clearComment () {
      this.blogComment = ''
    }
  }
}
</script>

<style lang="less" scoped>

.blog-title{
  font-family:-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  font-weight: normal;
  font-size: 24px;
  color: #333;
}
.blog-tag {
  margin:30px 0;
}
.blog-tag a{
  background-color:#FF6666;
  color:#fff;
  padding:5px;
  margin-top: 15px;
}
.blog-info{
  padding: 20px 0 0 0;
}
.card-body{
  padding:20px;
}
.comment-container{
  margin-top: 30px;
}
.comment-container a{
  font-size: 18px;
  font-weight: 600;
}
.comment-container .el-divider--horizontal {
  margin: 11px 0;
}
.comment-button{
  margin: 10px 0;
}
</style>
