<template>
  <div class="userstore-container">
    <div class="nav-aside">
      <div ref="info" class="active" @click="selectComponent('info')">가게 정보</div>
      <div ref="menu-list" @click="selectComponent('menu-list')">메뉴</div>
      <div ref="timedeal-list" @click="selectComponent('timedeal-list')">타임딜</div>
      <div ref="live-commerce" @click="selectComponent('live-commerce')">라이브커머스</div>
    </div>
    <div v-if="loaded" style="margin: auto">
      <div class="main-content">
        <header class="header-container">
          <h2>{{ storeName }}</h2>
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
      loaded: false,
    };
  },
  // 늦게 나오는 거
  async created() {
    try {
      const paramsId = this.$route.params.storeId;
      const { data } = await getStoreInfo(paramsId);
      this.setWatchedStore(data);
      this.storeName = this.$store.state.watchedStore.baseInfo.name;
      this.loaded = true;
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    // 어디서든
    ...mapMutations(['setWatchedStore']),
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
// @import '@/assets/scss/sample2';
// @font-face {
//   font-family: neon;
//   src: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/707108/neon.ttf);
// }
.userstore-container {
  @include flexbox;
  flex-wrap: wrap;
  width: 100%;
  // align-items: flex-start;
  h2 {
    @include xs-mobile {
      font-size: 20px;
    }
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
    padding: 20px;
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
