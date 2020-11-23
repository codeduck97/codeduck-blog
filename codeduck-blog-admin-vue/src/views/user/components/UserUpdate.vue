<template>
  <span class="register-info">
    <!-- 用户管理||用户列表||查找栏||用户注册 -->
    <el-drawer
      :title="title"
      :visible.sync="drawer"
      :with-header="true"
      size="50%"
      :before-close="handleClose"
    >
      <!-- 用户注册||表单 -->
      <el-form ref="userFormRef" :model="userForm" status-icon :rules="rules" label-width="100px" class="demo-userForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" type="text" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model="userForm.checkPass" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="userForm.mobile" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="backgroud-color:#00000" @click="submitForm('userFormRef')">保存</el-button>
          <el-button @click="resetForm('userFormRef')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </span>
</template>

<script>
import * as UserApi from '@/api/admin/user'
export default {
  data() {
    // 密码校验函数
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.userForm.checkPass !== '') {
          this.$refs.userFormRef.validateField('checkPass')
        }
        callback()
      }
    }
    // 确认密码校验函数
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.userForm.password) {
        console.log(value)
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      drawer: false,
      title: '',
      userForm: {
        username: '',
        password: '',
        checkPass: '',
        email: '',
        mobile: ''
      },
      checkPass: '',
      // 表单校验
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { require: true, min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
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
  methods: {
    submitForm(userFormRef) {
      // 1. 对表单进行验证，验证通过则进行Api调用
      this.$refs[userFormRef].validate((valid) => {
        if (valid) {
          // 3. 调用注册Api注册用户信息
          UserApi.updateUser(this.userForm).then(res => {
            if (res.code === 1002) {
              return this.$message.warning('该用户名已存在,请重新填写！')
            } else if (res.code === 1001) {
              this.$refs[userFormRef].resetFields()
              this.drawer = false
              return this.$message.error('修改失败！')
            } else {
              this.$refs[userFormRef].resetFields()
              this.$emit('needRefresh')
              this.$message.success('修改成功！')
              this.drawer = false
            }
          })
        } else {
          this.$message.error('请填写完整信息')
          return false
        }
      })
    },
    // 重置表单
    resetForm(userForm) {
      this.$refs[userForm].resetFields()
    },
    // 关闭对话框前的处理函数
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.$refs.userFormRef.resetFields()
          done()
        })
        .catch(_ => {})
    }
  }
}
</script>

<style scoped>
</style>
