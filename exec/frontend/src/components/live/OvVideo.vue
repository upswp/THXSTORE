<template>
  <video autoplay />
</template>

<script>
export default {
  name: 'OvVideo',

  props: {
    streamManager: {
      type: Object,
      default: () => {},
    },
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style lang="scss" scoped>
video {
  width: 100%;
}
</style>
