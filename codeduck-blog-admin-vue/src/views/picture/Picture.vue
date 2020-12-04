<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="nav-container">
      <el-input
        v-model="keyword"
        class="filter-item"
        placeholder="请输入图片名称"
        clearable
        style="width: 200px;margin-right:10px;"
        @keyup.enter.native="handleFind"
      />
      <!-- 分类下拉框 -->
      <el-select
        v-model="sortKeyword"
        style="width: 140px;margin-right:15px;"
        filterable
        clearable
        reserve-keyword
        placeholder="请选择分类"
        @keyup.enter.native="handleFind"
      >
        <el-option
          v-for="item in sortList"
          :key="item.id"
          :label="item.sortName"
          :value="item.id"
        />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFind">查找</el-button>
      <el-button class="filter-item" icon="el-icon-refresh" @click="handleReset">重置</el-button>
      <el-button v-if="hasPerm('pic:add')" class="filter-item" style="margin-left:100px" type="primary" icon="el-icon-upload" @click="handleUploadPic">上传图片</el-button>
      <el-button v-if="hasPerm('pic:add')" class="filter-item" type="primary" icon="el-icon-upload" @click="handleQiniuUpload">七牛上传</el-button>
      <el-button class="button" style="margin-left:30px" type="primary" icon="el-icon-refresh-right" @click="checkAll">{{ chooseTitle }}</el-button>
      <el-button v-if="hasPerm('pic:delete')" class="filter-item" type="danger" icon="el-icon-delete" @click="handleDeleteBatch">删除选中</el-button>
    </div>
    <!-- 图片显示区 -->
    <el-card>
      <div style="margin-top: 7px;">
        <el-row :gutter="10">
          <el-col v-for="(item,i) in pics" :key="i" :offset="-5" :xs="12" :sm="8" :md="6" :lg="4" :xl="3">
            <el-card class="image-card" shadow="hover">
              <input :id="item.id" class="check-box" type="checkbox" :checked="pictureIds.indexOf(item.id)>=0" @click="checked(item)">
              <el-image :src="item.localUrl" class="image" @click="handleEdit(item)" />
              <div class="card-pic-name">
                <span>{{ item.alias }}</span>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <!-- 分页管理 -->
    <div class="block">
      <el-pagination
        :current-page="pageInfo.pageNum"
        :page-sizes="[12,24,48,72,96,120]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="margin-top:25px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <!-- 本地上传图片对话框 -->
    <picture-upload ref="pictureUploadRef" @needRefresh="refresh" />
    <!-- 七牛云上传对话框 -->
    <qiniu-upload ref="qiniuUploadRef" @needRefresh="refresh" />
    <!-- 图片信息编辑对话框 -->
    <picture-edit ref="pictureEditRef" @needRefresh="refresh" />
  </div>
</template>

<script>
import PictureUpload from '@/views/picture/components/PictureUpload'
import QiniuUpload from '@/views/picture/components/QiniuUpload'
import PictureEdit from '@/views/picture/components/PictureEdit'
import * as PicApi from '@/api/picture/picture'

export default {
  components: {
    PictureUpload,
    QiniuUpload,
    PictureEdit
  },
  filters: {
    picNameManage(name) {
      return name.substring(0, 12)
    }
  },
  data() {
    return {
      title: '增加图片',
      pageInfo: { // 分页信息对象
        pageNum: 1,
        pageSize: 24
      },
      pics: [],
      total: 0,
      keyword: '', // 查找关键字
      pictureIds: [], // 图片id集合
      chooseTitle: '全部选中', // 全选的提示信息
      isCheckedAll: false, // 是否全选
      dialogUploadVisible: false,
      sortKeyword: '',
      sortList: []
    }
  },
  watch: {
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
    handleFind() {
      console.log(this.keyword)
    },
    // 编辑图片信息
    handleEdit(picInfo) {
      this.$refs.pictureEditRef.drawer = true
      this.$refs.pictureEditRef.data = picInfo
    },
    // 添加或修改博客对话框完成数据修改后触发该方法
    refresh() {
      this.getList()
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
    // 本地服务器上传
    handleUploadPic() {
      this.$refs.pictureUploadRef.drawer = true
    },
    // 七牛云存储上传
    handleQiniuUpload() {
      this.$refs.qiniuUploadRef.dialogVisible = true
    },
    // 选中所有图片
    checkAll() {
      // 如果是全选
      if (this.isCheckedAll) {
        this.pictureIds = []
        this.isCheckedAll = false
        this.chooseTitle = '全部选中'
      } else {
        this.pictureUids = []
        this.pics.forEach(picture => {
          this.pictureIds.push(picture.id)
        }, this)
        this.isCheckedAll = true
        this.chooseTitle = '取消全选'
      }
      // console.log('选择列表', this.pictureIds)
    },
    // 删除选中图片
    handleDeleteBatch() {
      this.$confirm('是否删除选中的图片?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        PicApi.deletePic(this.pictureIds).then(res => {
          if (res.code !== 1000) return this.$message.error('删除失败')
          this.getList()
          this.$message.success('删除成功')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    checked(data) {
      // 返回某个指定的字符串值在 pictureIds 出现的索引
      const idIndex = this.pictureIds.indexOf(data.id)
      if (idIndex >= 0) {
        // 删除数组第idIndex索引处的1个元素（直接改变数组本身）
        this.pictureIds.splice(idIndex, 1)
      } else {
        this.pictureIds.push(data.id)
      }
      // console.log('选择列表', this.pictureIds)
    },
    handleReset() {
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
.nav-container{
  margin: -10px 0 10px 0;
  padding: 25px;
  width: 100%;
  height: 100px;
  background: #ffffffdc;
  border: 1px solid #dce0e9;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.12), 0 0 6px 0 rgba(0,0,0,.04);
  box-sizing: inherit;
  display: block;
  z-index: 101;
}
.image-card{
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
  padding: 2px;
  height: 200px;
}
.image{
  width: 280px;
  margin-left: 0px;
  height: 160px;
  margin-top: 0px;
}
.card-pic-name{
  padding: 5px;
  margin-left: 25%;
}
.check-box{
    position: absolute;
    z-index: 100;
    margin-left: 4px;
    margin-top: 4px;
}
</style>
