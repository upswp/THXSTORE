<template>
  <div class="main-container">
    <main-header></main-header>
    <main-aside></main-aside>
    <div class="main-contents">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import MainHeader from '@/components/common/MainHeader';
import MainAside from '@/components/common/MainAside';
export default {
  components: {
    MainHeader,
    MainAside,
  },
};
</script>

<style lang="scss" scoped>
.main-container {
  z-index: 0;
  .main-contents {
    padding: 60px 20px 30px 20px;
  }
}
</style>
