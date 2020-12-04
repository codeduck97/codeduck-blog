<template>
  <div class="sort-save">
    <el-dialog
      title="分类保存页面"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <el-form ref="sortFormRef" :model="sortInfo" label-width="100px" class="demo-ruleForm" :rules="rules">
        <el-form-item label="排序索引" prop="sortIndex">
          <el-input v-model.number="sortInfo.sortIndex" type="number" autocomplete="off" />
        </el-form-item>
        <el-form-item label="分类名称" prop="sortName">
          <el-input v-model="sortInfo.sortName" type="text" autocomplete="off" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('sortFormRef')">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as SortApi from '@/api/blog/sort'
export default {
  data() {
    return {
      dialogVisible: false,
      sortInfo: {
        sortIndex: 0,
        sortName: ''
      },
      rules: {
        sortIndex: [
          { required: true, message: '排序索引不能为空', trigger: 'blur' },
          { type: 'number', message: '排序索引必须为数字值' }
        ],
        sortName: [
          { required: true, message: '分类名不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          SortApi.addSort(this.sortInfo).then(res => {
            if (res.code === 1002) {
              return this.$message.warning('该分类已存在，请重新输入！')
            } else {
              this.dialogVisible = false
              this.resetForm()
              if (res.code === 1000) {
                this.$emit('needRefresh')
                this.$router.push('/blog/sort')
                return this.$message.success('保存成功！')
              } else {
                return this.$message.error('保存失败！')
              }
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm() {
      return this.$refs.sortFormRef.resetFields()
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
