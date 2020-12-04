<template>
  <div class="app-container">
    <el-button v-if="hasPerm('role:add')" type="primary" @click="handleAddRole">添加角色</el-button>
    <div class="role-table">
      <el-table
        v-loading="loading"
        :data="roles"
        stripe
        style="width:100%;margin:20px 0 30px 0;"
      >
        <!-- 表单||ID -->
        <el-table-column align="center" label="ID" width="95">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="角色名" width="220">
          <template slot-scope="scope">
            {{ scope.row.roleName }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="备注信息">
          <template slot-scope="scope">
            {{ scope.row.remark }}
          </template>
        </el-table-column>

        <el-table-column align="center" label="创建时间" width="220">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="修改时间" width="220">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            <span>{{ scope.row.updateTime }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="220">
          <template slot-scope="scope">
            <el-button v-if="hasPerm('role:view')" type="primary" size="small" @click="handleView(scope.row)">查看</el-button>
            <el-button v-if="hasPerm('role:update')" type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button v-if="hasPerm('role:delete')" type="danger" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      :current-page="pageInfo.pageNum"
      :page-sizes="[3, 5, 8, 15]"
      :page-size="pageInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <role-add ref="roleAdd" @needRefresh="refresh" />
    <role-edit ref="roleEdit" @needRefresh="refresh" />
    <role-view ref="roleView" @needRefresh="refresh" />
  </div>
</template>

<script>
import * as RoleApi from '@/api/role'
import RoleAdd from '@/views/user/components/RoleAdd'
import RoleEdit from '@/views/user/components/RoleEdit'
import RoleView from '@/views/user/components/RoleView'

export default {
  components: { RoleAdd, RoleEdit, RoleView },
  data() {
    return {
      roles: [],
      loading: true,
      total: 0,
      pageInfo: {
        pageNum: 1,
        pageSize: 5
      },
      roleEdit: {
        visiable: false
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      RoleApi.getList(this.pageInfo).then(res => {
        if (res.code !== 1000) {
          this.loading = false
          return this.$message.error('服务器异常')
        }
        this.total = res.data.total
        this.roles = res.data.roles
      })
      this.loading = false
    },
    handleEdit(roleInfo) {
      RoleApi.getRolePermission(roleInfo.id).then(res => {
        const permissions = res.data
        this.$refs.roleEdit.setPermissions(permissions)
      })
      this.$refs.roleEdit.setRadioKey(roleInfo.roleKey)
      this.$refs.roleEdit.role = roleInfo
      this.$refs.roleEdit.drawer = true
    },
    handleView(roleInfo) {
      RoleApi.getRolePermission(roleInfo.id).then(res => {
        const permissions = res.data
        this.$refs.roleView.setPermissions(permissions)
      })
      this.$refs.roleView.setRadioKey(roleInfo.roleKey)
      this.$refs.roleView.role = roleInfo
      this.$refs.roleView.drawer = true
    },
    handleDelete(roleId) {
      RoleApi.deleteRole(roleId).then(res => {
        if (res.code === 1002) {
          return this.$message.error('删除失败，某些用户拥有该角色')
        } else if (res.code === 1000) {
          this.getList()
          return this.$message.success('删除成功')
        }
        return this.$message.error('删除失败')
      })
    },
    handleAddRole() {
      this.$refs.roleAdd.drawer = true
      return this.$notify.info('暂时不支持该功能')
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
    },
    refresh() {
      this.getList()
    }
  }
}
</script>

<style scoped>

</style>
