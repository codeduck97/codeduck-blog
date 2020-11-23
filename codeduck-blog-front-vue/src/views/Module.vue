<template>
    <article>
      <el-row :gutter="20" type="flex" justify="center">
        <!-- 图片轮播区域 -->
        <div  class="picsBox">
          <el-col :xs="3" :sm="12" :span="12">
              <el-image style="width:60%;height:60%" src="https://images.cnblogs.com/cnblogs_com/code-duck/1797754/o_2007021259491001062.jpg" fit="contain"/>
          </el-col>
        </div>
        <!-- END -->

        <!-- 图片轮播右侧推荐区域 -->
        <div class="toppic">
          <el-col :xs="0" :sm="4" :span="4">
              <el-image style="width:100%;height:100%" src="https://images.cnblogs.com/cnblogs_com/code-duck/1797754/o_2007021259491001062.jpg" fit="contain"/>
          </el-col>
        </div>
        <!-- END -->
      </el-row>
      <div class="blank"></div>
      <el-row :gutter="20" type="flex" justify="center">

        <!-- 博客列表部分 -->
        <div class="blogsBox">
          <el-col :xs="3" :sm="12" :span="12">
            <h1>home</h1>
          </el-col>
        </div>

        <!-- END -->

        <!-- 侧边栏区域 -->
        <div  class="blogsBoxSide">
          <el-col :xs="0" :sm="4" :span="4">
              <h1>Side</h1>
          </el-col>
        </div>
        <!-- END -->
      </el-row>

      <!-- 首页页脚区域 -->
      <el-row :gutter="20" type="flex" justify="center">
        <div class="isEnd">
          <el-col  :xs="3" :sm="24" :span="24">
              <h1>foot</h1>
          </el-col>
        </div>
      </el-row>
      <!-- END -->

    </article>
</template>
<script>
export default {
}
</script>

<style>

</style>
