<template>
  <div class="app-container">
    <button @click="printValue">anjiu</button>
    <mavon-editor ref="mavonEditorRef" v-model="content" :ishljs="true" @imgAdd="imgAdd" />
  </div>
</template>

<script>
import '../../../node_modules/mavon-editor/dist/css/index.css'
import { mavonEditor } from 'mavon-editor'
export default {
  components: {
    mavonEditor
  },
  data() {
    return {
      content: ''
    }
  },
  watch: {
  },

  methods: {
    printValue() {
      console.log(this.content)
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

