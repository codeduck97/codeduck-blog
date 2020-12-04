<template>
  <div>
    <div class="app-container">
      <!-- 查询和其他操作 -->
      <div class="filter-container" style="margin: 10px 0 10px 0;">
        <el-input
          v-model="keyword"
          class="filter-item"
          placeholder="请输入博客名"
          clearable
          style="width: 200px;margin-right:10px;"
          @keyup.enter.native="handleFind"
        />
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFind">查找</el-button>
        <el-button v-if="hasPerm('blog-tag:add')" class="filter-item" type="primary" icon="el-icon-edit" @click="handleAdd">添加标签</el-button>
        <el-button v-if="hasPerm('blog-tag:delete')" class="filter-item" type="danger" icon="el-icon-delete" @click="handleDeleteBatch">删除选中</el-button>
        <!-- TODO 点击数排序 -->
        <!--<el-button
          class="filter-item"
          type="info"
          icon="el-icon-document"
          @click="handleTagSortByClickCount"
        >点击数排序</el-button>-->

        <!-- TODO 重置排序 -->
        <el-button
          class="filter-item"
          type="info"
          icon="el-icon-document"
          @click="handleResetIndex"
        >重置排序</el-button>
      </div>
      <!-- 博客管理||博客分类||表单 -->
      <el-table
        ref="multipleTable"
        :data="tags"
        style="width: 100%"
        stripe
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" />
        <!-- 表单||序号 -->
        <el-table-column label="序号" width="60" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <!-- 表单||分类名 -->
        <el-table-column label="标签名" width="200" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.tagName }}</span>
          </template>
        </el-table-column>
        <!-- 表单||文章数 -->
        <el-table-column label="文章总数" width="100" align="center">
          <template slot-scope="scope">
            <el-tag type="danger">{{ scope.row.articlesNum }}</el-tag>
          </template>
        </el-table-column>
        <!-- 表单||点击数 -->
        <el-table-column label="点击数" width="100" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hits }}</span>
          </template>
        </el-table-column>
        <!-- 表单||排序 -->
        <el-table-column label="排序" width="100" align="center">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.tagIndex }}</el-tag>
          </template>
        </el-table-column>
        <!-- TODO 表单||状态 -->
        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="scope">
            <template v-if="scope.row.status === 0">
              <el-tag type="success">正常</el-tag>
            </template>
            <template v-if="scope.row.status === 1">
              <el-tag type="warning">推荐</el-tag>
            </template>
            <template v-if="scope.row.status == 2">
              <el-tag type="errot">已删除</el-tag>
            </template>
          </template>
        </el-table-column>
        <!-- 表单||创建时间 -->
        <el-table-column label="创建时间" width="250" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time" style="margin-right:5px" />
            <span>{{ scope.row.creationTime }}</span>
          </template>
        </el-table-column>
        <!-- 表单||操作 -->
        <el-table-column label="操作" fixed="right" min-width="250">
          <template slot-scope="scope">
            <!-- TODO 置顶按钮 -->
            <el-tooltip class="item" effect="dark" content="置顶" placement="bottom-end">
              <el-button type="warning" icon="el-icon-caret-top" circle @click="handleIncr(scope.row.id)" />
            </el-tooltip>
            <el-tooltip v-if="hasPerm('blog-tag:update')" class="item" effect="dark" content="编辑" placement="bottom-end">
              <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)" />
            </el-tooltip>
            <el-tooltip v-if="hasPerm('blog-tag:delete')" class="item" effect="dark" content="删除" placement="bottom-end">
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 博客管理||博客标签||分页管理 -->
      <div class="block">
        <el-pagination
          :current-page="pageInfo.pageNum"
          :page-sizes="[3, 5, 8, 15]"
          :page-size="pageInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top:25px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <!-- 标签保存对话框 -->
      <tag-save ref="tagSaveDialogRef" @needRefresh="refresh" />
      <!-- 标签修改对话框 -->
      <tag-update ref="tagUpdateDialogRef" @needRefresh="refresh" />
    </div>
  </div>
</template>
<script>
import * as TagApi from '@/api/blog/tag'
import TagSave from '@/views/blog/components/TagSave'
import TagUpdate from '@/views/blog/components/TagUpdate'

export default {
  components: { TagSave, TagUpdate },
  data() {
    return {
      tags: [], // 保存表单数据
      total: 0, // 总数据量
      keyword: '', // 查询关键字
      editDialogVisible: false, // 编辑框的显示与否
      multipleSelection: [], // 批量选中信息
      pageInfo: { // 分页信息对象
        pageNum: 1,
        pageSize: 8
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 请求数据
    getList() {
      TagApi.list(this.pageInfo).then(res => {
        this.tags = res.data.tags
        this.total = res.data.total
      })
    },
    // 监听pageSize改变的事件
    handleSizeChange(size) {
      this.pageInfo.pageSize = size
      this.getList()
    },
    // 监听页码值发生改变的事件
    handleCurrentChange(num) {
      this.pageInfo.pageNum = num
      this.getList()
    },
    // 添加或修改博客对话框完成数据修改后触发该方法
    refresh() {
      setTimeout(() => {
        this.getList()
      }, 100)
    },
    // 根据查询关键字查询标签信息
    handleFind() {
      if (this.keyword === '') {
        return this.getList()
      } else {
        TagApi.getTagInfo(this.keyword).then(res => {
          if (res.code === 1000) {
            this.tags = res.data
            return this.$message.success('查询成功')
          }
          this.getList()
          return this.$message.warning('未查询到相关信息')
        })
      }
    },
    /* 置顶该标签 */
    handleIncr(tagId) {
      TagApi.incrTagIndex(tagId).then(res => {
        if (res.code !== 1000) return this.$message.error('操作失败')
        this.getList()
        return this.$message.success('操作成功')
      })
    },
    /* 重置所有标签的排序值 */
    handleResetIndex() {
      TagApi.resetIndex().then(res => {
        if (res.code !== 1000) return this.$message.error('操作失败')
        this.getList()
        return this.$message.success('操作成功')
      })
    },
    // 添加标签信息
    handleAdd() {
      this.$refs.tagSaveDialogRef.dialogVisible = true
    },
    // 编辑标签信息
    handleEdit(tagInfo) {
      this.$refs.tagUpdateDialogRef.tagInfo = tagInfo
      this.$refs.tagUpdateDialogRef.dialogVisible = true
    },
    // 删除标签信息
    handleDelete(id) {
      this.$confirm('是否删除该标签信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        TagApi.deleteTagInfo(id).then(res => {
          if (res.code === 1001) {
            return this.$message.error('删除失败！')
          } else if (res.code === 1002) {
            const h = this.$createElement
            return this.$message({
              message: h('p', null, [
                h('span', null, '禁止操作 '),
                h('i', { style: 'color: teal' }, '该标签下存在博文')
              ])
            })
          }
          this.getList()
          return this.$message.success('删除成功！')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 批量选中
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 批量删除
    handleDeleteBatch() {
      if (this.multipleSelection.length <= 0) {
        this.$message.error('请先选中需要删除的内容')
        return
      }
      this.$confirm('此操作将会删除选中标签，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs.multipleTable.clearSelection()
        return this.$message.info('暂未实现')
      }).catch(() => {
        this.$refs.multipleTable.clearSelection()
        return this.$message.info('已取消删除')
      })
    }
  }
}
</script>
