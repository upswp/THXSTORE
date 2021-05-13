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
import { saveStoreIdToLocalStorage } from '@/utils/webStorage';
import { getStoreId } from '@/api/store';
import { mapMutations } from 'vuex';
export default {
  components: { StoreNavigation },
  data() {
    return {
      tap: 'info',
    };
  },
  async created() {
    try {
      const { data } = await getStoreId();
      this.setStoreId(data);
      saveStoreIdToLocalStorage(data);
    } catch (error) {
      console.log(error);
      alert('스토어 아이디를 불러오는데 실패했습니다.');
    }
  },
  methods: {
    ...mapMutations(['setStoreId']),
    changeTap(selection) {
      switch (selection) {
        case 'info':
          this.$router.push({ name: 'storeInfo' });
          break;
        case 'product':
          this.$router.push({ name: 'storeProduct' });
          break;
        case 'deal':
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
