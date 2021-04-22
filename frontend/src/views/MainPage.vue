<template>
  <div class="main-container">
    <main-header></main-header>
    <main-aside></main-aside>
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
}
</style>
