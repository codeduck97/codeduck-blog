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
        <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleAdd">添加分类</el-button>
        <el-button class="filter-item" type="danger" icon="el-icon-delete" @click="handleDeleteBatch">删除选中</el-button>
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
        :data="sorts"
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
        <el-table-column label="分类名" width="200" align="center" prop="sortName">
          <template slot-scope="scope">
            {{ scope.row.sortName }}
          </template>
        </el-table-column>
        <!-- 表单||文章数 -->
        <el-table-column label="文章总数" width="100" align="center" prop="articlesNum">
          <template slot-scope="scope">
            <el-tag type="danger">{{ scope.row.articlesNum }}</el-tag>
          </template>
        </el-table-column>
        <!-- 表单||点击数 -->
        <el-table-column label="点击数" width="100" align="center" prop="hits">
          <template slot-scope="scope">
            {{ scope.row.hits }}
          </template>
        </el-table-column>
        <!-- 表单||排序 -->
        <el-table-column label="排序" width="100" align="center" prop="sortIndex">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.sortIndex }}</el-tag>
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
            <div class="operate" style="margin-right: 0.5rem">
              <el-tooltip class="item" effect="dark" content="置顶" placement="bottom-end">
                <el-button type="warning" icon="el-icon-caret-top" circle @click="handleIncr(scope.row.id)" />
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="编辑" placement="bottom-end">
                <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.row)" />
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="删除" placement="bottom-end">
                <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row.id)" />
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 博客管理||博客分类||分页管理 -->
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
      <!-- 分类保存对话框 -->
      <sort-save ref="SortSaveDialogRef" @needRefresh="refresh" />
      <!-- 分类修改对话框 -->
      <sort-update ref="SortUpdateDialogRef" @needRefresh="refresh" />
    </div>
  </div>
</template>
<script>
import * as SortApi from '@/api/blog/sort'

import SortSave from '@/views/blog/components/SortSave'
import SortUpdate from '@/views/blog/components/SortUpdate'

export default {
  components: { SortSave, SortUpdate },
  data() {
    return {
      sorts: [], // 保存表单数据
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
      SortApi.list(this.pageInfo).then(res => {
        this.sorts = res.data.sorts
        this.total = res.data.total
        // console.log(this.sorts)
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
    // 根据查询关键字查询分类信息
    handleFind() {
      if (this.keyword === '') {
        return this.getList()
      } else {
        SortApi.getSortInfo(this.keyword).then(res => {
          if (res.code === 1000) {
            this.sorts = res.data
            return this.$message.success('查询成功')
          }
          this.getList()
          return this.$message.warning('未查询到相关信息')
        })
      }
    },
    // 重置排序
    handleResetIndex() {
      SortApi.resetIndex().then(res => {
        if (res.code !== 1000) return this.$message.error('操作失败')
        this.getList()
        return this.$message.success('操作成功')
      })
    },
    // 置顶操作
    handleIncr(sortId) {
      SortApi.incrSortIndex(sortId).then(res => {
        if (res.code !== 1000) return this.$message.error('操作失败')
        this.getList()
        return this.$message.success('操作成功')
      })
    },
    // 添加分类信息
    handleAdd() {
      this.$refs.SortSaveDialogRef.dialogVisible = true
    },
    // 编辑分类信息
    handleEdit(sortInfo) {
      this.$refs.SortUpdateDialogRef.sortInfo = sortInfo
      this.$refs.SortUpdateDialogRef.dialogVisible = true
    },
    // 删除分类信息
    handleDelete(id) {
      this.$confirm('是否删除该分类信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        SortApi.deleteSort(id).then(res => {
          if (res.code === 1001) {
            return this.$message.error('删除失败！')
          } else if (res.code === 1002) {
            const h = this.$createElement
            return this.$message({
              message: h('p', null, [
                h('span', null, '禁止操作 '),
                h('i', { style: 'color: teal' }, '该分类下存在博文')
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
      this.$confirm('此操作将会删除选中分类，是否继续?', '提示', {
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
