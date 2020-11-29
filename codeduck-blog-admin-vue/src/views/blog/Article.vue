<template>
  <div class="app-container">
    <!--筛选博客导航栏-->
    <div class="nav-container" style="margin: 10px 0 10px 0;">
      <!--搜索框-->
      <el-input
        v-model="titleKeyword"
        clearable
        placeholder="请输入博客标题"
        style="width: 150px;margin-right:15px;"
        @keyup.enter.native="searchByTitle"
      />
      <!-- 分类下拉框 -->
      <el-select
        v-model="sortKeyword"
        style="width: 140px;margin-right:15px;"
        filterable
        clearable
        reserve-keyword
        placeholder="请输入分类名"
        @keyup.enter.native="queryByTitle"
      >
        <el-option
          v-for="item in sortList"
          :key="item.id"
          :label="item.sortName"
          :value="item.id"
        />
      </el-select>
      <!-- 标签下拉框 -->
      <el-select
        v-model="tagKeyword"
        style="width: 140px;margin-right:15px;"
        filterable
        clearable
        reserve-keyword
        placeholder="请输入分类名"
      >
        <el-option
          v-for="item in tagList"
          :key="item.id"
          :label="item.tagName"
          :value="item.id"
        />
      </el-select>
      <!-- 发布状态下拉框 -->
      <el-select
        v-model="publishStatus"
        clearable
        placeholder="是否发布"
        style="width:110px;margin-right:15px;"
      >
        <el-option
          v-for="item in publishedList"
          :key="item.id"
          :label="item.status"
          :value="item.id"
        />
      </el-select>
      <!-- 查询按钮 -->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="searchByTitle">查找</el-button>
      <!-- 添加博客按钮 -->
      <!-- <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="AddBlog">添加博客</el-button> -->
    </div>

    <!-- 博文列表 -->
    <div v-loading="listLoading" class="blog_table" element-loading-text="Loading">
      <el-table :data="blogs" stripe style="width: 100%">
        <el-table-column type="selection" />
        <el-table-column label="序号" fixed="left" width="50px">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="封面" align="center">
          <template slot-scope="scope">
            <el-image v-if="scope.row.cover" :src="scope.row.cover" />
          </template>
        </el-table-column>
        <el-table-column label="标题" align="center">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        <el-table-column label="作者" align="center">
          <template slot-scope="scope">
            {{ scope.row.author }}
          </template>
        </el-table-column>
        <el-table-column label="分类" align="center">
          <template slot-scope="scope">
            {{ scope.row.sort.sortName }}
          </template>
        </el-table-column>
        <el-table-column label="标签" align="center">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.tag.tagName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="点击数" align="center" width="70px">
          <template slot-scope="scope">
            {{ scope.row.hits }}
          </template>
        </el-table-column>
        <el-table-column label="发布状态" align="center">
          <template slot-scope="scope">
            <template v-if="scope.row.published === 1">
              <el-tag type="success">已发布</el-tag>
            </template>
            <template v-if="scope.row.published === 0">
              <el-tag type="danger">未发布</el-tag>
            </template>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" width="200px" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            {{ scope.row.creationTime }}
          </template>
        </el-table-column>

        <el-table-column v-if="hasPerm('blog:add') & hasPerm('blog:delete')" fixed="right" width="150px" label="操作">
          <template slot-scope="scope">
            <div class="operate">
              <el-button type="primary" size="small" @click="editBlog(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteBlog(scope.row.id)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div class="block">
      <el-pagination
        :current-page="pageInfo.pageNum"
        :page-sizes="[3, 5, 7, 10]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="margin-top:25px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <!-- 新增博客对话框 -->
    <article-save ref="articleSaveDialogRef" :sort-list="sortList" :tag-list="tagList" :title="title" @needRefresh="refresh" />
    <!-- 更新博客对话框 -->
    <article-update ref="articleUpdateDialogRef" :sort-list="sortList" :tag-list="tagList" :title="title" @needRefresh="refresh" />
  </div>
</template>

<script>
import * as ArticleApi from '@/api/blog/article'
import * as SortApi from '@/api/blog/sort'
import * as TagApi from '@/api/blog/tag'

import ArticleSave from '@/views/blog/components/ArticleSave'
import ArticleUpdate from '@/views/blog/components/ArticleUpdate'

export default {
  components: { ArticleSave, ArticleUpdate },
  data() {
    return {
      title: '',
      blogs: [],
      pageInfo: {
        pageNum: 1,
        pageSize: 5
      },
      total: 0, // 博客总数
      titleKeyword: '', // 查询关键字
      loading: true,
      listLoading: true,
      sortList: [], // 分类下拉框
      sortKeyword: '', // 分类下拉框绑定字段
      tagList: [], // 标签下拉框
      tagKeyword: '', // 标签下拉框绑定字段
      publishedList: [], // 发布下拉框
      publishStatus: '' // 发布状态
    }
  },
  created() {
    // 加载博文列表
    this.getList()
    // 加载分类列表
    this.getSortList()
    // 加载标签列表
    this.getTagList()
    // 加载发布列表
    this.initPublish()
  },
  methods: {
    // 分页获取博文列表
    getList() {
      ArticleApi.list(this.pageInfo).then(res => {
        this.blogs = res.data.blogs
        this.total = res.data.total
        this.listLoading = false
      })
    },
    // 获取所有分类列表信息
    getSortList() {
      SortApi.getAllSorts().then(res => {
        if (res.code === 1000) {
          this.sortList = res.data
        }
      })
    },
    // 获取所有标签列表信息
    getTagList() {
      TagApi.getAllTags().then(res => {
        if (res.code === 1000) {
          this.tagList = res.data
        }
      })
    },
    // 创建发布列表信息
    initPublish() {
      const status = [
        { 'id': '0', 'status': '已发布' },
        { 'id': '1', 'status': '未发布' }
      ]
      this.publishedList = status
    },
    // 监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.pageInfo.pageSize = newSize
      this.getList()
    },
    // 监听页码值发生改变的事件
    handleCurrentChange(newPage) {
      this.pageInfo.pageNum = newPage
      this.getList()
    },
    // 添加博客
    // AddBlog() {
    //   this.$refs.articleSaveDialogRef.dialogVisible = true
    //   this.title = '添加博客'
    // },
    // 添加或修改博客对话框完成数据修改后触发该方法
    refresh() {
      setTimeout(() => {
        this.getList()
      }, 100)
    },
    editBlog(blog) {
      const article = {}
      // 将博文显示列表对象转为博文对象
      for (const k in blog) {
        if (k === 'sort') {
          article['sortId'] = blog[k].id
        } else if (k === 'tag') {
          article['tagId'] = blog[k].id
        } else {
          article[k] = blog[k]
        }
      }
      // 将published转为字符
      const published = article.published
      if (published === 0) {
        article.published = '0'
      } else {
        article.published = '1'
      }
      this.title = '更新博客'
      this.$refs.articleUpdateDialogRef.blog = article
      this.$refs.articleUpdateDialogRef.dialogVisible = true
    },
    // 删除博文信息
    deleteBlog(id) {
      this.$confirm('是否删除该用户信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ArticleApi.deleteBlog(id).then(res => {
          if (res.code !== 1000) {
            return this.$message.error('删除失败！')
          }
          this.getList()
          return this.$message.success('删除成功！')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 通过博文标题查询
    searchByTitle() {
      if (this.titleKeyword === '') {
        return this.getList()
      }
      ArticleApi.getBlogByTitle(this.titleKeyword).then(res => {
        if (res.code !== 1000) {
          return this.$message.warning('查询信息不存在！')
        }
        // console.log(res.data)
        this.blogs = res.data
        return this.$message.success('查询成功！')
      })
    }
  }
}
</script>

<style scoped>
.operate{
  font-size: 16px;
  letter-spacing: 1em;
}
.line{
  text-align: center;
}
</style>

