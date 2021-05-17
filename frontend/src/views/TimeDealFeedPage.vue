<template>
  <div class="feed-container">
    <div class="feed-component-wrapper">
      <time-deal-aside></time-deal-aside>
      <time-deal-body :feed-list="feedList"></time-deal-body>
    </div>
  </div>
</template>

<script>
import TimeDealBody from '@/components/store/feed/TimeDealBody';
import TimeDealAside from '@/components/store/feed/TimeDealAside';
import { dummy } from '@/utils/dummy';

export default {
  components: {
    TimeDealBody,
    TimeDealAside,
  },
  data() {
    return {
      feedList: dummy.map(x => {
        x.timeDealList.forEach(item => {
          const specific = (item.price * (100 - item.rate)) / 100;
          item['discounted'] = Math.floor(specific / 100) * 100;
        });
        return x;
      }),
    };
  },
};
</script>

<style lang="scss" scoped>
.feed-container {
  width: 100%;
  @include flexbox;
  @include justify-content(center);
}
.feed-component-wrapper {
  width: 100%;
  @include lg-pc {
    @include flexbox;
    @include justify-content(center);
  }
  @include pc {
    @include flexbox;
    @include justify-content(center);
  }
}
</style>
