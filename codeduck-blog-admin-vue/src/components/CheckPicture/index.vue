<template>
  <div class="check-pic">
    <el-drawer
      :visible.sync="drawer"
      :before-close="handleClose"
      :with-header="false"
      :append-to-body="true"
      size="50%"
    >
      <div style="margin:15px 0 0px 30px;padding:0px">请选择图片</div>
      <el-divider style="margin:10px 0;padding:10px" />
      <!-- 图片显示区 -->
      <el-card>
        <div style="margin-top: 7px;">
          <el-row :gutter="10">
            <el-col v-for="(item,i) in pics" :key="i" :offset="-5" :xs="12" :sm="12" :md="6" :lg="6" :xl="4">
              <el-card class="image-card" shadow="hover">
                <el-image :src="item.localUrl" class="image" @click="checked(item)" />
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
    </el-drawer>
  </div>
</template>

<script>
import * as PicApi from '@/api/picture/picture'
export default {
  data() {
    return {
      drawer: false,
      pageInfo: { // 分页信息对象
        pageNum: 1,
        pageSize: 16
      },
      pics: [],
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
    // 被选中的图片抛出给父组件
    checked(pic) {
      this.$emit('getUrl', pic.localUrl)
      this.drawer = false
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
    handleClose(done) {
      done()
    }
  }
}
</script>

<style>
.image{
    width: 223px;
    height: 119px;
}
.block{
  margin-bottom: 20px;
}
</style>
