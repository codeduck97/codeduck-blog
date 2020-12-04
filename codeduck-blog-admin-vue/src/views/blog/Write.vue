<template>
  <div class="blog-container">
    <div class="input-title">
      <el-input v-model="blog.title" placeholder="请输入文章标题" />
    </div>
    <div class="mavonEditor">
      <mavon-editor ref="mavonEditorRef" v-model="blog.content" class="editor" :ishljs="true" @imgAdd="imgAdd" />
    </div>
    <div class="operate">
      <el-button v-if="hasPerm('blog:add')" type="danger">保存草稿</el-button>
      <el-button v-if="hasPerm('blog:add')" type="primary" @click="openPublishDrawer">发布</el-button>
      <el-button type="warning" @click="checkImage">图库</el-button>
    </div>
    <!-- 博客信息设置对话框 -->
    <el-drawer
      :visible.sync="drawer"
      :with-header="false"
      size="40%"
    >
      <div class="publishDrawer">
        <!-- 封面选择器 -->
        <el-form ref="blogFormRef" :model="blog" :rules="rules">
          <el-form-item label="封面" :label-width="formLabelWidth">
            <div v-if="blog.cover" class="imgBody">
              <i
                v-show="icon"
                class="el-icon-error inputClass"
                @mouseover="icon = true"
                @click="deleteCover()"
              />
              <img
                class="imgBody-image"
                :src="blog.cover"
                @mouseover="icon = true"
                @mouseout="icon = false"
              >
            </div>
            <div v-else class="uploadImgBody" @click="checkPicture">
              <i class="el-icon-plus avatar-uploader-icon" />
            </div>
          </el-form-item>

          <el-form-item label="标题" :label-width="formLabelWidth" prop="title">
            <el-input v-model="blog.title" auto-complete="off" />
          </el-form-item>

          <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
            <el-input v-model="blog.author" auto-complete="off" />
          </el-form-item>

          <!-- 分类下拉框 -->
          <el-form-item label="分类" :label-width="formLabelWidth" prop="sortId">
            <el-select
              v-model="blog.sortId"
              style="width: 140px;margin-right:15px;"
              filterable
              clearable
              reserve-keyword
              placeholder="请选择分类名"
              @keyup.enter.native="handleFind"
            >
              <el-option
                v-for="item in sortList"
                :key="item.id"
                :label="item.sortName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <!-- 标签下拉框 -->
          <el-form-item label="标签" :label-width="formLabelWidth" prop="tagId">
            <el-select
              v-model="blog.tagId"
              style="width: 140px;margin-right:15px;"
              filterable
              clearable
              reserve-keyword
              placeholder="请选择标签名"
              @keyup.enter.native="handleFind"
            >
              <el-option
                v-for="item in tagList"
                :key="item.id"
                :label="item.tagName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <!-- 发布选择器 -->
          <el-form-item label="发布" :label-width="formLabelWidth" prop="published">
            <el-radio-group v-model="blog.published" size="small">
              <el-radio v-for="item in publishList" :key="item.id" :label="item.id" border>{{ item.status }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div class="operate">
          <el-button @click="cansulSubmit('blogFormRef')">取 消</el-button>
          <el-button type="primary" @click="saveBlog('blogFormRef')">保 存</el-button>
        </div>
        <!-- 图片选择对话框 -->
        <check-picture ref="checkPictureRef" @getUrl="getPicUrl" />
      </div>
    </el-drawer>
    <!-- 图片信息获取对话框 -->
    <select-image ref="selectIamgeRef" />
  </div>
</template>

<script>
import * as BlogApi from '@/api/blog/article'
import * as SortApi from '@/api/blog/sort'
import * as TagApi from '@/api/blog/tag'
import '../../../node_modules/mavon-editor/dist/css/index.css'

import { mavonEditor } from 'mavon-editor'
import CheckPicture from '@/components/CheckPicture'
import SelectImage from '@/components/SelectImage/SelectImage'
export default {
  components: {
    mavonEditor,
    CheckPicture,
    SelectImage
  },
  data() {
    return {
      drawer: false,
      blog: {
        id: '',
        adminId: '',
        cover: '',
        creationTime: '',
        updateTime: '',
        hit: '',
        deleted: '',
        title: '',
        author: '',
        sortId: '',
        tagId: '',
        published: '0',
        content: ''
      },
      icon: false, // 控制删除图标的显示
      sortList: [], // 分类选择列表
      tagList: [], // 标签选择列表
      publishList: [], // 发布选择列表
      isChange: false, // 表单内容是否改变
      changeCount: 0, // 改变计数器
      interval: null, // 定义触发器
      formLabelWidth: '60px',
      lineLabelWidth: '120px', // 一行的间隔数
      rules: {
        title: [
          { required: true, message: '标题不能为空', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '标题不能为空', trigger: 'blur' }
        ],
        sortId: [
          { required: true, message: '分类不能为空', trigger: 'blur' }
        ],
        tagId: [
          { required: true, message: '分类不能为空', trigger: 'blur' }
        ],
        published: [
          { required: true, message: '发布字段不能为空', trigger: 'blur' },
          { pattern: /^[0-9]\d*$/, message: '发布字段只能为自然数' }
        ],
        content: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 加载分类列表
    this.getSortList()
    // 加载标签列表
    this.getTagList()
    // 加载发布列表
    this.initPublish()
  },
  methods: {
    openPublishDrawer() {
      this.drawer = true
    },
    // 获取返回图片的连接
    getPicUrl(event) {
      this.blog.cover = event
    },
    // 博客封面选择器
    checkPicture() {
      this.$refs.checkPictureRef.drawer = true
    },
    // 删除选中的封面
    deleteCover() {
      this.blog.cover = ''
      return
    },
    // 打开图片选择器
    checkImage() {
      this.$refs.selectIamgeRef.drawer = true
    },
    // 获取所有分类列表信息
    getSortList() {
      SortApi.getAllSorts().then(res => {
        if (res.code === 1000) {
          this.sortList = res.data
        }
      })
    },
    // 获取所有标签列表信息
    getTagList() {
      TagApi.getAllTags().then(res => {
        if (res.code === 1000) {
          this.tagList = res.data
        }
      })
    },
    // 创建发布列表信息
    initPublish() {
      const status = [
        { 'id': '0', 'status': '是' },
        { 'id': '1', 'status': '否' }
      ]
      this.publishList = status
    },
    // 添加博客
    saveBlog(blogFormRef) {
      this.$refs[blogFormRef].validate(valid => {
        if (!valid) return
        BlogApi.addBlog(this.blog).then(res => {
          if (res.code === 1000) {
            this.blog.content = ''
            this.blog.cover = ''
            this.$refs[blogFormRef].resetFields()
            this.drawer = false
            return this.$notify({ title: '成功', message: '博客添加成功', type: 'success' })
          }
          this.$refs[blogFormRef].resetFields()
          this.drawer = false
          return this.$notify({ title: '失败', message: '博客添加失败', type: 'error' })
        })
        // 博客添加完成后
      })
    },
    // 取消保存
    cansulSubmit(blogFormRef) {
      this.blog.content = ''
      this.blog.cover = ''
      this.$refs[blogFormRef].resetFields()
      this.drawer = false
    },
    imgAdd(pos, $file) {
    // 第一步.将图片上传到服务器.
      var formdata = new FormData()
      formdata.append('image', $file)
      this.img_file[pos] = $file
      this.$http({
        url: '/api/ck//upload/pic',
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then((res) => {
        const _res = res.data
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        this.$refs.md.$img2Url(pos, _res.url)
      })
    }
  }
}
</script>
<style scoped>
.publishDrawer{
  padding: 10px;
  margin: 30px 10px 0 0;
}
.input-title{
  padding: 20px 30px 20px 30px;
  text-align: center;
}
.mavonEditor{
  width: 100%;
  height: 100%;
  padding: 0px 30px 0px 30px;
}
.editor{
  min-height: 500px;
}
.operate {
  padding: 30px;
  text-align: right;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  margin: 0, 0, 0, 10px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width:  100%;
  height: 100%;
  line-height: 200px;
  text-align: center;
}
.imgBody {
  width:  100%;
  height: 100%;
  border: solid 2px #ffffff;
  border-radius: 10px;
  float: left;
  position: relative;
}
.imgBody-image {
  display:inline;
  width: 100%;
  height: 100%;
  border-radius: 10px;
}
.uploadImgBody {
  margin-left: 5px;
  width:  100%;
  height: 200px;
  border: dashed 1px #c0c0c0;
  float: left;
  position: relative;
  border-radius: 10px;
}
.uploadImgBody :hover {
  border: dashed 1px #00ccff;
  border-radius: 10px;
}
.inputClass {
  padding: 13px;
  position: absolute;
}
</style>
