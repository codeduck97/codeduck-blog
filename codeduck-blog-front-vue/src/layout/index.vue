<template>
  <div class="home-container">
    <nav-menu></nav-menu>
    <router-view/>
  </div>
</template>

<script>
import NavMenu from './NavMenu'
export default {
  // eslint-disable-next-line vue/no-unused-components
  components: { NavMenu }
}
</script>

<style scoped>
</style>
