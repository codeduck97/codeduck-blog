<template>
  <el-drawer
    title="编辑角色信息"
    :visible.sync="drawer"
    :with-header="true"
  >
    <el-form ref="roleForm" label-width="120px" :model="role" :rules="rules" style="padding:0 60px 0 0">
      <el-form-item label="角色名称：" prop="roleName">
        <el-input v-model="role.roleName" />
      </el-form-item>
      <el-form-item label="角色描述：">
        <el-input v-model="role.remark" type="textarea" />
      </el-form-item>
      <el-form-item label="用户类型：">
        <el-radio-group v-model="radioKey">
          <el-radio :label="1">admin</el-radio>
          <el-radio :label="2">user</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="权限选择">
        <el-cascader
          v-model="checkedKeys"
          :options="menuTreeData"
          :props="props"
          clearable
        />
      </el-form-item>
      <span style="color:red; margin-left:40px;">PS：user类型无法拥有用户管理的所有权限</span>
    </el-form>
  </el-drawer>
</template>

<script>
import * as RoleApi from '@/api/role'
export default {
  data() {
    return {
      drawer: false,
      props: { multiple: true, emitPath: false },
      role: {
        roleName: '',
        remark: '',
        roleKey: '',
        permissionId: ''
      },
      radioKey: 2,
      checkedKeys: [],
      menuTreeData: [],
      getMenus: false,
      rules: {
        roleName: [
          { required: true, message: '角色名不能空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getPermissionMenus()
  },
  methods: {
    getPermissionMenus() {
      RoleApi.getPermissionMenus().then(res => {
        if (res.code !== 1000) {
          this.loading = false
          return this.$message.error('服务器异常')
        }
        this.menuTreeData = res.data.rows.children
      })
    },
    setRadioKey(roleKey) {
      if (roleKey === 'admin') {
        this.radioKey = 1
      } else if (roleKey === 'user') {
        this.radioKey = 2
      }
    },
    setPermissions(permissions) {
      this.checkedKeys = permissions
    }
  }
}
</script>

<style scoped>
</style>
