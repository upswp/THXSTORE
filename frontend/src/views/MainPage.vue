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
  z-index: 10;
  background-color: $gray100;
}
.main-contents {
  background-color: $gray100;
  position: absolute;
  padding: 50px 10px;
  min-height: 100vh;
  width: 100%;
  @include flexbox;
  @include justify-content(center);
  @include mobile() {
    padding-top: 40px;
  }
}
.aside-padding {
  @include mobile() {
    display: none;
  }
}
</style>
