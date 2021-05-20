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
