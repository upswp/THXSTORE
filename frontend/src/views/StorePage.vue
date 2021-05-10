<template>
  <div class="store-container">
    <store-navigation class="store-aside" @changeTap="changeTap"></store-navigation>
    <div class="store-main">
      <router-view></router-view>
    </div>
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
  width: 100%;
  @include lg-pc {
    @include flexbox;
  }
  @include pc {
    @include flexbox;
  }
}
.store-aside {
  @include lg-pc {
    position: fixed;
    padding: 10px 0;
    margin: 0;
    width: 250px;
    min-height: 100vh;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
  }
  @include pc {
    position: fixed;
    padding: 10px 0;
    width: 250px;
    margin: 0;
    min-height: 100vh;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
  }
}
.store-main {
  width: 100%;

  min-height: calc(100vh - 50px);
  @include flexbox;
  @include justify-content(center);
  @include lg-pc {
    width: calc(100% - 250px);
    margin-left: 250px;
  }
  @include pc {
    width: calc(100% - 250px);
    margin-left: 250px;
  }

  padding: 10px;
}
</style>
