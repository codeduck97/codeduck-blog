<template>
  <div class="app-container">
    <!-- 用户管理||用户列表||查找栏 -->
    <div class="nav-bar">
      <el-input v-model="keyword" class="search-bar" placeholder="请输入用户名" @keyup.enter.native="searchByName" />
      <el-button type="primary" style="margin-left: 15px;" @click="searchByName">查找</el-button>
      <el-button v-if="hasPerm('user:add')" type="primary" @click="registerUser">用户注册</el-button>
    </div>
    <!-- 用户管理||用户列表||表单 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      stripe
      highlight-current-row
    >
      <!-- 表单||ID -->
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <!-- 表单||邮箱 -->
      <el-table-column align="center" label="昵称">
        <template slot-scope="scope">
          {{ scope.row.nickname }}
        </template>
      </el-table-column>
      <!-- 表单||用户名 -->
      <el-table-column align="center" label="用户名">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <!-- 表单||手机号 -->
      <el-table-column align="center" label="角色">
        <template slot-scope="scope">
          <el-tag type="primary">{{ scope.row.roleName }} </el-tag>
        </template>
      </el-table-column>
      <!-- 表单||登录次数 -->
      <el-table-column align="center" label="登录次数">
        <template slot-scope="scope">
          {{ scope.row.loginTimes }}
        </template>
      </el-table-column>
      <!-- 表单||登陆时间 -->
      <el-table-column align="center" prop="lastLoginTime" label="最近登录" width="180px">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.lastLoginTime }}</span>
        </template>
      </el-table-column>
      <!-- 表单||用户状态 -->
      <el-table-column class-name="status-col" label="用户状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | userStatusFilter }}</el-tag>
        </template>
      </el-table-column>
      <!-- 表单||操作 -->
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <div class="operate">
            <el-tooltip v-if="hasPerm('user:update')" class="item" effect="dark" content="编辑" placement="bottom-end">
              <el-button type="primary" icon="el-icon-edit" circle @click="editUserInfo(scope.row)" />
            </el-tooltip>
            <el-tooltip v-if="hasPerm('user:delete')" class="item" effect="dark" content="删除" placement="bottom-end">
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteUserById(scope.row.id)" />
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 用户管理||用户列表||表单||操作||用户修改 -->
    <!-- 用户管理||用户列表||查找栏||用户注册 -->
    <user-add ref="userRegisterRef" style="margin-left:15px;" @needRefresh="refresh" />
    <user-edit ref="userUpdateRef" style="margin-left:15px;" @needRefresh="refresh" />
    <user-add ref="registerDrawer" style="margin-left:15px;" @needRefresh="refresh" />
    <!-- 用户管理||用户列表||分页 -->
    <el-pagination
      :current-page="pageInfo.pageNum"
      :page-sizes="[3, 5, 8, 15]"
      :page-size="pageInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import UserAdd from '@/views/user/components/UserAdd'
import UserEdit from '@/views/user/components/UserEdit'

import * as UserApi from '@/api/user'

export default {
  components: { UserAdd, UserEdit },
  filters: {
    statusFilter(status) {
      if (Number.prototype.valueOf(status) === 0) {
        return 'success'
      } else {
        return 'danger'
      }
    },
    userStatusFilter(status) {
      if (Number.prototype.valueOf(status) === 0) {
        return '正常'
      } else {
        return '已删除'
      }
    }
  },
  data() {
    return {
      //  获取用户列表的参数对象
      pageInfo: {
        pageNum: 1,
        pageSize: 5
      },
      keyword: '',
      list: [],
      userForm: '',
      listLoading: true,
      drawer: false,
      total: 0,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { require: true, min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取用户信息
    getList() {
      // this.listLoading = true
      UserApi.getList(this.pageInfo).then(response => {
        this.list = response.data.admins
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 根据用户名查询用户信息
    searchByName() {
      if (this.keyword === '') this.getList()
      else {
        UserApi.queryByName(this.keyword).then(res => {
          if (res.code !== 1000) {
            return this.$message({
              message: '查询的用户不存在！',
              type: 'error'
            })
          }
          // res.dta为一个对象，需要转为数组对象并赋值给list
          const arr = []
          arr.push(res.data)
          this.list = arr
        })
      }
    },
    // 注册用户信息
    registerUser() {
      this.$refs.userRegisterRef.title = '用户注册页面'
      this.$refs.userRegisterRef.getRoleList()
      this.$refs.userRegisterRef.drawer = true
    },
    // 注册或更新完成后调用此方法
    refresh() {
      setTimeout(() => {
        this.getList()
      }, 100)
    },
    // 编辑用户信息
    editUserInfo(userInfo) {
      this.$refs.userUpdateRef.userForm = userInfo
      this.$refs.userUpdateRef.roleId = userInfo.roleId
      this.$refs.userUpdateRef.getRoleList()
      this.$refs.userUpdateRef.title = '用户信息修改页面'
      this.$refs.userUpdateRef.drawer = true
    },
    // 通过id删除用户信息
    deleteUserById(id) {
      this.$confirm('是否删除该用户信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        UserApi.deleteUser(id).then(res => {
          if (res.code !== 1000) {
            return this.$message({
              message: '删除失败！',
              type: 'error'
            })
          }
          setTimeout(() => {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
          }, 100)
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 用户管理||用户列表||分页||监听pagesize改变事件
    handleSizeChange(size) {
      this.pageInfo.pageSize = size
      this.getList()
    },
    // 用户管理||用户列表||分页||监听page-sizes改变
    handleCurrentChange(num) {
      this.pageInfo.pageNum = num
      this.getList()
    }
  }
}
</script>

<style scoped>
  .operate{
    font-size: 16px;
    letter-spacing: 1em;
  }
  .nav-bar{
    margin: 0px 10px 15px 0px;
  }
  .search-bar{
    width: 200px;
  }
</style>
