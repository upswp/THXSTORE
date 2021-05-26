<template>
  <div id="app">
    <loading-spinner></loading-spinner>
    <router-view />
  </div>
</template>
<script>
import LoadingSpinner from '@/components/common/LoadingSpinner';
export default {
  components: {
    LoadingSpinner,
  },
};
</script>
<style lang="scss">
#app {
  min-height: 100vh;
}
</style>
