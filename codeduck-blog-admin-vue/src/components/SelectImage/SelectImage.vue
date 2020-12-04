<template>
  <div class="check-pic">
    <el-drawer
      :visible.sync="drawer"
      :before-close="handleClose"
      :with-header="false"
      size="50%"
    >
      <div style="margin:15px 0 0px 30px;padding:0px">图库</div>
      <el-divider style="margin:10px 0;padding:10px" />
      <!-- 图片显示区 -->
      <el-card>
        <div style="margin-top: 7px;">
          <el-row :gutter="10">
            <el-col v-for="(item,i) in pics" :key="i" :offset="-5" :xs="12" :sm="12" :md="6" :lg="6" :xl="4">
              <el-card class="image-card" shadow="hover">
                <el-image :src="item.localUrl" class="image" @click="getPicInfo(item)" />
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-card>
      <!-- 分页管理 -->
      <div class="block">
        <el-pagination
          :current-page="pageInfo.pageNum"
          :page-sizes="[16,24,48,72,96,120]"
          :page-size="pageInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top:25px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <!-- 图片详情对话框 -->
      <div>
        <el-drawer
          :visible.sync="innerDrawer"
          :with-header="false"
          :append-to-body="true"
          size="40%"
        >
          <div style="padding:10px 0 0 20px;">图片信息</div>
          <el-divider />
          <div class="edit-form">
            <el-form label-width="100px" :model="data">
              <el-form-item class="form-item" label="图片名称:">
                <el-input v-model="data.alias" :disabled="true" />
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
    </el-drawer>
  </div>
</template>

<script>
import * as PicApi from '@/api/picture/picture'
export default {
  data() {
    return {
      drawer: false,
      innerDrawer: false,
      pageInfo: { // 分页信息对象
        pageNum: 1,
        pageSize: 16
      },
      pics: [],
      data: '',
      total: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      PicApi.list(this.pageInfo).then(res => {
        this.pics = res.data.pictures
        this.total = res.data.total
      })
    },
    // 获取图片详情
    getPicInfo(pic) {
      this.data = pic
      this.innerDrawer = true
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
    // 复制图片地址
    copyUrl(url) {
      this.$commonUtil.copyText(url)
      this.innerDrawer = false
      this.drawer = false
      this.$commonUtil.message.success('复制成功')
    },
    // 复制图片markdown地址
    copyMdUrl(url) {
      this.$commonUtil.copyText(url)
      this.innerDrawer = false
      this.drawer = false
      this.$commonUtil.message.success('复制成功')
    },
    handleClose(done) {
      done()
    }
  }
}
</script>

<style>
.image-card{
  padding: 0px;
}
.image{
    width: 223px;
    height: 119px;
}
.block{
  margin-bottom: 20px;
}
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
  border-right-color: #FFFFFF;
  border-radius: 16px;
  background-color:#000000;
  color:#FFFFFF;
}
</style>
