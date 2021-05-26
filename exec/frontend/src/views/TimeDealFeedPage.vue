<template>
  <div class="feed-container">
    <div v-if="loaded" class="feed-component-wrapper">
      <time-deal-aside @newDistance="loadFeed"></time-deal-aside>
      <time-deal-body v-if="feedList.length > 0" :feed-list="feedList"></time-deal-body>
      <div v-else class="no-time-deal">현재 등록된 타임딜이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import TimeDealBody from '@/components/store/feed/TimeDealBody';
import TimeDealAside from '@/components/store/feed/TimeDealAside';
import { getTimeDealFeed } from '@/api/timeDeal';
import { dummy } from '@/utils/dummy';
import { mapMutations } from 'vuex';
export default {
  components: {
    TimeDealBody,
    TimeDealAside,
  },
  data() {
    return {
      // feedList: dummy.map(x => {
      //   x.timeDealList.forEach(item => {
      //     const specific = (item.price * (100 - item.rate)) / 100;
      //     item['discounted'] = Math.floor(specific / 100) * 100;
      //   });
      //   return x;
      // }),
      feedList: [],
      loaded: false,
    };
  },
  async created() {
    try {
      this.setSpinnerState(true);
      const { data } = await getTimeDealFeed(5);
      if (data.length > 0) {
        this.feedList = data.map(x => {
          x.timeDealList.forEach(item => {
            const specific = (item.price * (100 - item.rate)) / 100;
            item['discounted'] = Math.floor(specific / 100) * 100;
          });
          return x;
        });
      }
      this.loaded = true;
      this.setSpinnerState(false);
    } catch (error) {
      console.log(error);
      alert('타임딜 목록을 불러오는데 실패하였습니다.');
      this.setSpinnerState(false);
    }
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    async loadFeed(dist) {
      try {
        this.setSpinnerState(true);
        const { data } = await getTimeDealFeed(dist);
        if (data.length > 0) {
          this.feedList = data.map(x => {
            x.timeDealList.forEach(item => {
              const specific = (item.price * (100 - item.rate)) / 100;
              item['discounted'] = Math.floor(specific / 100) * 100;
            });
            return x;
          });
        } else {
          this.feedList = [];
        }
        this.loaded = true;
        this.setSpinnerState(false);
      } catch (error) {
        alert('타임딜 목록을 불러오는데 실패하였습니다.');
        this.setSpinnerState(false);
      }
    },
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
.no-time-deal {
  color: $gray600;
  height: 300px;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  font-size: 30px;
  width: 100%;
  @include lg-pc {
    width: clamp(100px, 50vw, 1000px);
  }
  @include pc {
    width: clamp(100px, 50vw, 1000px);
    font-size: 25px;
  }
  @include mobile {
    font-size: 20px;
  }
  @include xs-mobile {
    font-size: 15px;
  }
}
</style>
