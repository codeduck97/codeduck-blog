<template>
  <div>
    <!-- 添加或修改博客对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="100%"
      :before-close="handleClose"
      fullscreen
    >
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
              style="display:inline; width: 195px;height: 105px;"
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

        <el-row>

          <!-- 分类下拉框 -->
          <el-col :span="4.5">
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
          </el-col>

          <!-- 标签下拉框 -->
          <el-col :span="4.5">
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
          </el-col>

          <!-- 发布选择框 -->
          <el-col :span="4.5">
            <el-form-item label="是否发布" :label-width="lineLabelWidth" prop="published">
              <el-radio-group v-model="blog.published" size="small">
                <el-radio v-for="item in publishList" :key="item.id" :label="item.id" border>{{ item.value }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 文章内容：CKEditor编辑器 -->
        <el-form-item label="内容" :label-width="formLabelWidth" prop="content">
          <mavon-editor ref="mavonEditorRef" v-model="blog.content" :ishljs="true" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cansulSubmit('blogFormRef')">取 消</el-button>
        <el-button type="primary" @click="submitForm('blogFormRef')">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 图片选择对话框 -->
    <check-picture ref="checkPictureRef" @getUrl="getPicUrl" />
  </div>
</template>
<script>
// import CkEditor from '@/components/CKEditor'
import CheckPicture from '@/components/CheckPicture'

import { getToken } from '@/utils/auth'
import * as BlogApi from '@/api/blog/article'

export default {
  components: { CheckPicture },
  // eslint-disable-next-line
  props: ['title','sort-list','tag-list'],
  data() {
    return ({
      dialogVisible: false,
      importHeaders: {
        Authorization: getToken()
      },
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
      formLabelWidth: '120px',
      lineLabelWidth: '120px', // 一行的间隔数
      rules: {
        title: [
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
    })
  },
  created() {
    // 初始化分类选择列表
    this.sortList = this['sort-list']
    // 初始化标签选择列表
    this.tagList = this['tag-list']
    // 初始化发布选择列表
    this.initPublished()
  },
  methods: {
    // 创建发布字典信息
    initPublished() {
      const status = [
        { 'id': '0', 'value': '已发布' },
        { 'id': '1', 'value': '未发布' }
      ]
      this.publishList = status
    },
    // 获取返回图片的连接
    getPicUrl(event) {
      this.blog.cover = event
    },
    checkPicture() {
      this.$refs.checkPictureRef.drawer = true
    },
    // 删除选中的封面
    deleteCover() {
      this.blog.cover = ''
      return
    },
    handleClose() {
    },
    // 添加博客
    submitForm(blogFormRef) {
      this.$refs[blogFormRef].validate(valid => {
        if (!valid) return
        BlogApi.addBlog(this.blog).then(res => {
          if (res.code === 1000) {
            this.$emit('needRefresh')
            this.$refs[blogFormRef].resetFields()
            this.dialogVisible = false
            return this.$notify({ title: '成功', message: '博客添加成功', type: 'success' })
          }
          this.$refs[blogFormRef].resetFields()
          this.dialogVisible = false
          return this.$notify({ title: '失败', message: '博客添加失败', type: 'error' })
        })
        // 博客添加完成后
      })
    }
  }
}
</script>
<style>
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
  width:  195px;
  height: 105px;
  line-height: 105px;
  text-align: center;
}
.imgBody {
  width:  195px;
  height: 105px;
  border: solid 2px #ffffff;
  float: left;
  position: relative;
}
.uploadImgBody {
  margin-left: 5px;
  width:  195px;
  height: 105px;
  border: dashed 1px #c0c0c0;
  float: left;
  position: relative;
}
.uploadImgBody :hover {
  border: dashed 1px #00ccff;
}
.inputClass {
  position: absolute;
}
</style>
