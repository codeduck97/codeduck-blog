<template>
  <div>
    <el-drawer
      title="个人中心"
      :visible.sync="drawer"
      :before-close="handleClose">
      <h1>个人中心待开发……</h1>
    </el-drawer>
  </div>
</template>
<script>
export default {
  data () {
    return {
      drawer: false
    }
  },
  methods: {
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    }
  }
}
</script>

<style>

</style>
