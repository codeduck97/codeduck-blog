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
    <el-divider><i class="el-icon-collection"></i></el-divider>
    <el-row type="flex" justify="center">
      <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="2">
        <div class="sidebar">
          <el-timeline :reverse="reverse">
            <el-timeline-item v-for="(item, index) in tagList" hide-timestamp :key="index">
              <span
              @click="getArticles(item.id)"
              :class="[item.id === selectedId ? 'sortBoxSpan sortBoxSpanSelect' : 'sortBoxSpan']">
                {{item.tagName}}
              </span>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
      <el-col :xs="20" :sm="18" :md="18" :lg="16" :xl="16">
        <div class="main">
          <el-card>
            <div class="main-card">
              <el-row>
                <el-col :xs="10" :sm="8" :md="6" :lg="6" :xl="4" v-for="(item,index) in articleList" :key="index">
                  <div class="article-box">
                    <el-card>
                      <div class="article-card"  style="cursor:pointer;">
                          <el-image  @click="getArticleInfo(item.id)" class="cover-image" v-if="item.cover" :src="item.cover" />
                          <el-image  @click="getArticleInfo(item.id)" v-else src="http://localhost:8600/upload/images/2020/11/20201124-c9368084-9af8-4bb5-879e-70d28c1ebe92.jpg" />
                      </div>
                      <div style="padding:5px;text-align: center;">
                        <a href="javascript:void(0);" @click="getArticleInfo(item.id)">{{item.title}}</a>
                      </div>
                    </el-card>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as TagApi from '@/api/tag'
export default {
  data () {
    return {
      selectedId: '', // 选中的分类id
      reverse: false,
      tagList: [],
      articleList: []
    }
  },
  created () {
    this.getTagList()
  },
  methods: {
    /* 获取分类列表信息 */
    getTagList () {
      TagApi.getTagList().then(res => {
        if (res.code !== 1000) {
          return this.$message.error('系统开小差了……')
        }
        this.tagList = res.data
        this.getArticles(this.tagList[0].id)
      })
    },
    /* 根据分类Id获取博文列表 */
    getArticles (tagId) {
      this.selectedId = tagId
      TagApi.getArticlesByTagId(tagId).then(res => {
        if (res.code !== 1000) {
          return this.$message.error('系统开小差了……')
        }
        this.articleList = res.data
        console.log(this.articleList)
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
