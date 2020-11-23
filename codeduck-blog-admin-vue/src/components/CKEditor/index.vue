<template>
  <div class="app-container">
    <textarea id="editor" rows="10" cols="80" />
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
export default {
  name: 'CkEditor',
  // eslint-disable-next-line
  props: ['content'],
  data() {
    return {
      editor: null, // 编辑器对象
      textData: this.content // 初始化内容
    }
  },
  watch: {
    // 监听content的变化，更新editor中的值
    content: function() {
      if (this.content !== this.editor.getData()) {
        this.editor.setData(this.content)
      }
    }
  },
  mounted: function() {
    var that = this
    const CKEDITOR = window.CKEDITOR

    // 渲染编辑器
    CKEDITOR.replace('editor', {
      height: '250px',
      width: '100%',
      toolbar: 'toolbar_Full',
      codeSnippet_theme: 'zenburn',
      filebrowserImageUploadUrl: 'http://localhost:7100/api/ck/upload/pic?token=' + getToken(),
      filebrowserUploadUrl: 'http://localhost:7100/api/ck/upload/pic?token=' + getToken(),
      pasteUploadFileApi: 'http://localhost:7100/api/ck/upload/copy?token=' + getToken()
    })

    this.editor = CKEDITOR.instances.editor
    this.editor.setData(this.content) // 初始化内容

    // 一秒钟通知子组件，ckeditor中内容改变
    that.editor.on('change', function(event) {
      that.timeout = setTimeout(function() {
        that.fun()
      }, 1000)
    })
    that.editor.on('instanceReady', function() {
      that.editor.widgets.registered.uploadimage.onUploaded = function(upload) {
        console.log(upload)
        this.replaceWith('<img src="' + upload.url + '">')
      }
    })
  },

  // 销毁组件前，销毁编辑器
  beforeDestroy: function() {
    this.editor.destroy()
  },
  created() {
    this.textData = this.content
  },
  methods: {
    // 获取data
    getData() {
      return this.editor.getData()
    },
    setData() {
      return this.editor.setData()
    },
    initData() {
      try {
        this.editor.setData('')
      } catch (error) {
        console.log('CKEditor还未加载！')
      }
    },
    fun() {
      this.$emit('contentChange', '')
    }
  }
}
</script>
