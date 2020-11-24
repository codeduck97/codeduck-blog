<template>
  <div class="classify-container">
    <el-row :gutter="20" type="flex" justify="center" style="height:200px">
      <el-col :span="24">
        <div class="sort-header">
          <el-image fit="contain"
          src="http://localhost:8600/upload/images/2020/11/2020117-0ecc80cf-c1d3-4397-b02d-86d78e3e44e7.jpg" />
        </div>
      </el-col>
    </el-row>
    <el-divider>
      <i class="el-icon-collection"></i>
    </el-divider>
    <el-row type="flex" justify="center">
      <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="2">
        <div class="sidebar">
          <div class="radio" style="margin-bottom:20px;">
              <el-switch
                v-model="reverse"
                active-text="倒序"
                inactive-text="正序"
                active-color="#000000"
                inactive-color="#13ce66"
              ></el-switch>
            </div>
          <el-timeline :reverse="reverse">
            <el-timeline-item v-for="(item, index) in dateList" hide-timestamp :key="index">
              <span
              @click="getArticles(item)"
              :class="[item === selectedId ? 'sortBoxSpan sortBoxSpanSelect' : 'sortBoxSpan']">
                {{item}}
              </span>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
      <el-col :xs="20" :sm="18" :md="18" :lg="16" :xl="16">
        <div class="main">
          <el-timeline>
            <el-timeline-item v-for="(item,index) in articleList" :key="index" :timestamp="item.creationTime" placement="top">
              <el-card>
                <div style="padding:10px;">
                  <div class="article-title">
                    <a href="javascript:void(0);" @click="getArticleInfo(item.id)">{{item.title}}</a>
                  </div>
                  <div class="article-info">
                    <span style="margin-right:10px;"><el-tag >{{item.author}}</el-tag></span>
                    <span style="margin-right:10px;"><el-tag type="success">{{tagDic.get(item.tagId)}}</el-tag></span>
                  </div>
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-row>
    <el-backtop><i class="el-icon-upload2"></i></el-backtop>
  </div>
</template>

<script>
import * as ArchiveApi from '@/api/archive'
import * as ArticleApi from '@/api/article'
export default {
  data () {
    return {
      selectedId: '', // 选中的时间段
      reverse: false,
      tagDic: new Map(), // 标签字典
      articleList: [],
      dateList: []
    }
  },
  created () {
    this.getDateList()
    this.getTagDic()
  },
  methods: {
    getDateList () {
      ArchiveApi.getDate().then(res => {
        if (res.code !== 1000) {
          return this.$message.error('服务器开小差了……')
        }
        this.dateList = Array.from(res.data)
        this.getArticles(this.dateList[0])
      })
    },
    /* 根据年月时间获取博文列表 */
    getArticles (date) {
      this.selectedId = date
      ArchiveApi.getArticlesByDate(date).then(res => {
        if (res.code !== 1000) {
          return this.$message.error('服务器开小差了……')
        }
        this.articleList = res.data
        // console.log(this.articleList)
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

<style scoped>
.article-info {
  padding: 5px 0 10px 0;
}
.article-title {
  padding: 10px 0 25px 0;
  font-weight: 600;
}
.sort-header {
  width:100%;
  height:100%;
  overflow: hidden;
}
.article-box {
  padding: 10px;
}
.main {
  padding: 10px;
}
.sidebar {
  margin: 5px 0;
  padding: 10px;
}
.sortBox {
  color: #555;
}

.sortBoxSpan {
  cursor: pointer;
}
.sortBoxSpan:hover {
  color: #409eff;
}
.sortBoxSpanSelect {
  color: #409eff;
}

.itemTitle {
  cursor: pointer;
}
.itemTitle:hover {
  color: #409eff;
}
.elTag {
  cursor: pointer;
}
.main-card {
  padding: 10px;
}
</style>
