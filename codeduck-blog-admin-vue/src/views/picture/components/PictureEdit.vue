<template>
  <div>
    <el-drawer
      :visible.sync="drawer"
      :with-header="false"
      size="40%"
    >
      <div style="padding:10px 0 0 20px;">图片信息</div>
      <el-divider />
      <div class="edit-form">
        <el-form label-width="100px" :model="data">
          <el-form-item class="form-item" label="图片名称:">
            <el-input v-model="data.alias" />
          </el-form-item>
          <el-form-item class="form-item" label="图片大小:">
            {{ Math.trunc(data.picSize/1024) + ' KB' }}
          </el-form-item>
          <el-form-item class="form-item" label="图片尺寸:">
            {{ data.resolution }}
          </el-form-item>
          <el-form-item class="form-item" label="图片类型:">
            {{ 'image/' + data.suffixName }}
          </el-form-item>
          <el-form-item class="form-item" label="上传时间:">
            {{ data.creationTime }}
          </el-form-item>
          <el-form-item class="form-item" label="普通链接:" style="font-size:5px">
            {{ data.localUrl }}
            <el-button
              size="mini"
              class="duplicate-url"
              icon="el-icon-copy-document"
              @click="copyUrl(data.localUrl)"
            />
          </el-form-item>
          <el-form-item class="form-item" label="MD格式链接:" style="font-size:5px">
            {{ data.mdUrl }}
            <el-button
              class="duplicate-url"
              size="mini"
              icon="el-icon-copy-document"
              @click="copyMdUrl(data.mdUrl)"
            />
          </el-form-item>
        </el-form>
        <div class="block">
          <el-image :src="data.localUrl" />
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
export default {
  data() {
    return {
      drawer: false,
      data: ''
    }
  },
  methods: {
    handleSave() {
      console.log(this.data)
    },
    copyUrl(url) {
      this.$commonUtil.copyText(url)
      this.$commonUtil.message.success('复制成功')
    },
    copyMdUrl(url) {
      this.$commonUtil.copyText(url)
      this.$commonUtil.message.success('复制成功')
    }
  }
}
</script>
<style scoped>
.edit-form{
  margin-top: 20px;
  margin-left: 10px;
}
.block{
  margin: 0 10px 0 0;
}
.form-item{
  margin-bottom:10px;
}
.duplicate-url{
  border-right-color: #FFF;
  border-radius: 16px;
  background-color:#000;
  color:#FFF
}
</style>
