<template>
  <div class="userstore-container">
    <div class="nav-aside">
      <div ref="info" class="active" @click="selectComponent('info')">가게 정보</div>
      <div ref="menu-list" @click="selectComponent('menu-list')">메뉴</div>
      <div ref="timedeal-list" @click="selectComponent('timedeal-list')">타임딜</div>
      <div ref="live-commerce" @click="selectComponent('live-commerce')">라이브커머스</div>
    </div>
    <div v-if="loaded" class="main-content-wrapper">
      <div class="main-content">
        <header class="header-container">
          <div class="store-thumbnail"><img :src="storeThumbImg" alt="" /></div>
          <div class="logo-and-title">
            <label for=""><img class="store-logo" :src="storeLogo" /></label>
            <div class="store-title">{{ storeName }}</div>
          </div>
          <div class="time-deal-ani" style="text-align: end">Timedeal</div>
        </header>
        <br />
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import { getStoreInfo } from '@/api/userStore';
export default {
  data() {
    return {
      active: 'info',
      storeName: '',
      storeThumbImg: require('@/assets/image/thumbnail_example.jpg'),
      storeLogo: require('@/assets/image/logo.jpg'),
      loaded: false,
      window: '',
    };
  },

  // 늦게 나오는 거
  async created() {
    try {
      const paramsId = this.$route.params.storeId;
      const { data } = await getStoreInfo(paramsId);
      await this.setWatchedStore(data);
      this.insertStoreHeaderInfo();
      this.loaded = true;
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    pageY() {
      this.window = window.pageYOffset;
    },
    // 어디서든
    ...mapMutations(['setWatchedStore']),
    insertStoreHeaderInfo() {
      this.storeName = this.$store.state.watchedStore.baseInfo.name;
      if (this.$store.state.watchedStore.sideInfo.thumbImg) {
        this.storeThumbImg = this.$store.state.watchedStore.sideInfo.thumbImg;
      }
      if (this.$store.state.watchedStore.sideInfo.logo) {
        this.storelogo = this.$store.state.watchedStore.sideInfo.logo;
      }
    },
    selectComponent(item) {
      if (this.active === item) return;
      this.resetActive();
      this.active = item;
      // console.log('아이템', this.active);
      this.$refs[item].classList.add('active');
      console.log(this.$route);
      // params.
      console.log('item', item);
      this.$router.push({
        name: item,
        // params: {
        //   storeId: this.$routes.params.storeId,
        // },
      });
    },
    resetActive() {
      this.$refs[this.active].classList.remove('active');
    },
  },
};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample';
</style>
