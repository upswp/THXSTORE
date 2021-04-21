<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
export default {
  data() {
    return {
      key: 0,
    };
  },
  watch: {
    key(newValue, oldValue) {
      return newValue;
    },
  },

  created() {},
};
</script>
<style lang="scss">
#app {
  min-height: 100vh;
}
</style>
