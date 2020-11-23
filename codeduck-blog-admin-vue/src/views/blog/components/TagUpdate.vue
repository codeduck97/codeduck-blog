<template>
  <div class="tag-save">
    <el-dialog
      title="标签修改页面"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form ref="tagFormRef" :model="tagInfo" label-width="100px" class="demo-ruleForm" :rules="rules">
        <el-form-item label="排序索引" prop="tagIndex">
          <el-input v-model.number="tagInfo.tagIndex" type="number" autocomplete="off" />
        </el-form-item>
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="tagInfo.tagName" type="text" autocomplete="off" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('tagFormRef')">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as TagApi from '@/api/blog/tag'
export default {
  data() {
    return {
      dialogVisible: false,
      tagInfo: {},
      rules: {
        tagIndex: [
          { required: true, message: '排序索引不能为空', trigger: 'blur' },
          { type: 'number', message: '排序索引必须为数字值' }
        ],
        tagName: [
          { required: true, message: '标签名不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          TagApi.updateTagInfo(this.tagInfo).then(res => {
            if (res.code === 1002) {
              return this.$message.warning('该标签已存在，请重新输入！')
            } else {
              this.dialogVisible = false
              this.resetForm()
              if (res.code === 1000) {
                this.$emit('needRefresh')
                return this.$message.success('更新成功！')
              } else {
                return this.$message.error('更新失败！')
              }
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm() {
      return this.$refs.tagFormRef.resetFields()
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.resetForm()
          done()
        })
        .catch(_ => {})
    }
  }
}
</script>
