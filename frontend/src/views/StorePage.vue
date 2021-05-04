<template>
  <div class="store-container">
    <store-navigation class="store-aside" @changeTap="changeTap"></store-navigation>
    <router-view class="store-main"></router-view>
  </div>
</template>

<script>
import StoreNavigation from '@/components/store/StoreNavigation';

export default {
  components: { StoreNavigation },
  data() {
    return {
      tap: 'info',
    };
  },
  methods: {
    changeTap(selection) {
      switch (selection) {
        case 'info':
          this.$router.push({ name: 'storeInfo' });
          break;
        case 'product':
          this.$router.push({ name: 'storeProduct' });
          break;
        case 'timeDeal':
          this.$router.push({ name: 'storeTimeDeal' });
          break;
        case 'live':
          this.$router.push({ name: 'storeLive' });
          break;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.store-container {
  min-height: 100vh;
  @include lg-pc {
    @include flexbox;
  }
  @include pc {
    @include flexbox;
  }
}
.store-aside {
  @include lg-pc {
    padding: 10px 0;
    margin: 0;
    width: 250px;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
  }
  @include pc {
    padding: 10px 0;
    width: 250px;
    margin: 0;
    min-height: 100vh;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
  }
}
.store-main {
  width: 100%;
  @include lg-pc {
    width: calc(100% - 250px);
  }
  @include pc {
    width: calc(100% - 250px);
  }

  padding: 10px;
}
</style>
