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
    padding: 70px 20px 50px 250px;
    height: 100vh;
    @include mobile() {
      padding: {
        top: 60px;
        left: 60px;
        right: 10px;
      }
    }
    @include xs-mobile() {
      padding: {
        left: 20px;
        right: 20px;
      }
    }
  }
}
</style>
