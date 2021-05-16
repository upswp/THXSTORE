<template>
  <div class="feed-container">
    <div v-for="(feed, index) in feedList" :key="index" class="feed-wrapper">
      <div class="thumbnail-wrapper">
        <img :src="feed.thumbImg" />
      </div>
    </div>
  </div>
</template>

<script>
import { dummy } from '@/utils/dummy';
export default {
  data() {
    return {
      feedList: dummy,
    };
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
