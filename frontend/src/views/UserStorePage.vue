<template>
  <div class="userstore-container">
    <div class="nav-aside">
      <div ref="info" class="active" @click="selectComponent('info')">가게 정보</div>
      <div ref="menu-list" @click="selectComponent('menu-list')">메뉴</div>
      <div ref="timedeal-list" @click="selectComponent('timedeal-list')">타임딜</div>
      <div ref="live-commerce" @click="selectComponent('live-commerce')">라이브커머스</div>
    </div>
    <div id="window" style="position: fixed; background: red" @click="pageY">{{ window }}zzzzzzzzzzz</div>
    <div v-if="loaded" style="margin: auto">
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
.userstore-container {
  @include flexbox;
  flex-wrap: wrap;
  width: 100%;
  // align-items: flex-start;
  .store-title {
    width: calc(90% - clamp(80px, 12vw, 150px));
    font-size: 30px;
    @include pc {
      font-size: 26px;
    }
    @include mobile {
      font-size: 22px;
    }
    @include xs-mobile {
      font-size: 20px;
    }
    font-weight: 800;
  }
  .nav-aside {
    align-self: flex-start;
    position: fixed;
    text-align: center;
    font-size: 1rem;
    padding: 10px 0;
    margin: 0;
    width: 200px;
    min-height: 100%;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
    div {
      padding: 10px 0;
      &:hover {
        background: $blue400;
        color: white;
      }
      &.active {
        background: $blue400;
        color: white;
      }
      @include mobile {
        padding: 4px;
        flex-basis: 25%;
      }
      @include xs-mobile {
        padding: 2px;
        flex-basis: 25%;
        font-size: 11px;
      }
    }
    &:hover {
      cursor: pointer;
    }
    @include mobile {
      // display: none;
      @include flexbox;
      @include justify-content(space-evenly);
      padding: 5px;
      width: 100%;
      position: static;
      min-height: 5px;
      flex-grow: 0;
    }
    @include xs-mobile {
      @include flexbox;
      @include justify-content(space-evenly);
      padding: 3px;
      width: 100%;
      position: static;
      min-height: 5px;
      flex-grow: 0;
    }
  }
  .main-content {
    width: calc(100vw - 200px);
    padding: 5px 20px 20px;
    max-width: 1000px;
    min-height: 90vh;
    margin-left: 200px;
    @include mobile {
      width: 100%;
      margin: 0px;
    }
    @include xs-mobile {
      margin: 0px;
      width: 100%;
    }
  }
  //   border: 2px blue solid;
  .header-container {
    .store-thumbnail {
      img {
        height: 10vw;
        object-fit: cover;
        object-position: center 50%;
        width: 100%;
      }
    }
    .logo-and-title {
      padding-top: 2%;
      border-top: 5px $blue400 solid;
      @include flexbox;
      @include align-items(center);
      margin-bottom: 10px;
    }
    .store-logo {
      $length: clamp(80px, 12vw, 150px);
      width: $length;
      height: $length;
      object-fit: cover;
      object-position: center 50%;
      margin-right: 10px;
      cursor: pointer;
    }
    .time-deal-ani {
      font-family: neon;
      color: #fb4264;
      font-size: 25px;
      line-height: 3vw;
      text-shadow: 0 0 2vw #f40a35;
      animation: neon 1s ease infinite;
      -moz-animation: neon 1s ease infinite;
      -webkit-animation: neon 1s ease infinite;
      @include mobile {
        font-size: 18px;
      }
      @include xs-mobile {
        font-size: 13px;
      }
    }
    @keyframes neon {
      0%,
      70% {
        //   text-shadow: 0 0 1vw #FA1C16, 0 0 3vw #FA1C16, 0 0 10vw #FA1C16, 0 0 10vw #FA1C16, 0 0 .4vw #FED128, ;
        color: #f40a35;
      }
      50% {
        text-shadow: 0 0 0.5vw #800e0b, 0 0 1.5vw #800e0b, 0 0 5vw #800e0b, 0 0 5vw #800e0b;
        //   color: #806914;
      }
    }
    @include pc {
    }
    .header-thumbnail {
      padding: 1%;
      border: 2px $gray200 solid;
    }
  }
  h2 {
    text-align: center;
  }
}
</style>
