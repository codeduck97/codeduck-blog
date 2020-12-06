<template>
  <div class="upload-container">
    <el-dialog
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="40%"
      center
    >
      <div style="margin:-20px 0 5px 0px;top:0px">七牛云上传</div>
      <el-divider />
      <div class="upload-pic">
        <el-upload
          class="upload-demo"
          drag
          action="/dev-api/api/pic/upload/qiniu"
          multiple
          :headers="headers"
          :on-success="handleUploadSuccess"
          :file-list="pictures"
        >
          <i class="el-icon-upload" />
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
        </el-upload>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  data() {
    return {
      dialogVisible: false,
      uploadApi: null,
      // 允许的文件类型
      fileType: ['doc', 'docx'],
      fileSize: 10,
      pictures: []
    }
  },
  computed: {
    headers() {
      return {
        'Authorization': getToken()
      }
    }
  },
  created() {
  },
  methods: {
    handleClose(done) {
      this.$emit('needRefresh')
      this.dialogVisible = false
      done()
      console.log('已关闭')
    },
    handleUploadSuccess() {
      return this.$message.success('上传成功')
    }
  }
}
</script>
<style scoped>
.upload-pic{
  padding: 0px;
}
.el-upload__tip {
    font-size: 12px;
    color: #606266;
    margin-top: 15px;
}
</style>
