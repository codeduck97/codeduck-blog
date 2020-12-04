<template>
  <el-drawer
    title="新增角色"
    :visible.sync="drawer"
    :before-close="handleClose"
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
    <div class="operate">
      <el-button type="primary" @click="submitForm('roleForm')">提交</el-button>
      <el-button @click="cancelSubmit('roleForm')">取消</el-button>
    </div>
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
    submitForm(roleForm) {
      this.$refs[roleForm].validate((valid) => {
        const checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
        if (!valid) {
          return this.$message.error('请填写完整信息')
        } else if (checkedArr.length === 0) {
          return this.$message.error('请选择相应的权限')
        }
        this.radioKey === 1 ? this.role.roleKey = 'admin' : this.role.roleKey = 'user'
        this.role.permissionId = checkedArr
        RoleApi.addRole(this.role).then(res => {
          if (res.code !== 1000) {
            return this.$message.error('新增角色失败')
          }
          this.$refs[roleForm].resetFields()
          this.checkedKeys = []
          this.drawer = false
          this.$emit('needRefresh')
          return this.$message.success('添加成功')
        })
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.$refs.roleForm.resetFields()
          this.checkedKeys = []
          done()
        })
        .catch(_ => {})
    }
  }
}
</script>

<style scoped>
.operate {
  padding: 60px 20px 0 0;
  float: right;
}
</style>
