<template>
  <div class="userstore-container">
    <div class="nav-aside">
      <div ref="info" class="active nav-item" @click="selectComponent('info')">가게 정보</div>
      <div ref="menu" class="nav-item" @click="selectComponent('menu')">메뉴</div>
      <div ref="timedeal" class="nav-item" @click="selectComponent('timedeal')">
        타임딜 <span class="time-deal-ani">Now!</span>
      </div>
      <div ref="live" class="nav-item" @click="selectComponent('live')">라이브커머스</div>
      <div ref="userStoreReview" class="nav-item" @click="selectComponent('userStoreReview')">리뷰</div>
    </div>
    <div v-if="loaded" class="main-content-wrapper">
      <div class="main-content">
        <header class="header-container">
          <div class="store-thumbnail"><img :src="sideInfo.thumbImg" /></div>
          <div class="logo-and-title">
            <img class="store-logo" :src="sideInfo.logo" />
            <div class="store-title">{{ baseInfo.name }}</div>
          </div>
          <!-- <div class="time-deal-ani" style="text-align: end">Timedeal Now!</div> -->
        </header>
        <br />
        <router-view :base-info="baseInfo" :side-info="sideInfo"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { getStoreInfo } from '@/api/userStore';
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      active: 'info',
      loaded: false,
      window: '',
      baseInfo: {},
      sideInfo: {},
      storeId: this.$route.params.storeId,
    };
  },
  computed: {
    path() {
      return this.$router.history.current;
    },
  },
  // 늦게 나오는 거
  async created() {
    try {
      this.setSpinnerState(true);
      const { data } = await getStoreInfo(this.storeId);
      this.baseInfo = data.baseInfo;
      this.sideInfo = data.sideInfo;
      if (!this.sideInfo.thumbImg) this.sideInfo.thumbImg = require('@/assets/image/thumbnail_example.jpg');
      if (!this.sideInfo.logo) this.sideInfo.logo = require('@/assets/image/logo.jpg');
      this.loaded = true;
    } catch (error) {
      console.log(error);
      alert('가게 정보를 불러오는데 실패하였습니다.');
    } finally {
      this.setSpinnerState(false);
    }
  },
  mounted() {
    this.syncTab(this.path.fullPath);
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    syncTab(path) {
      for (const name of ['info', 'menu', 'timedeal', 'live']) {
        if (path.includes(name)) {
          this.resetActive();
          this.active = name;
          this.$refs[name].classList.add('active');
          return;
        }
      }
    },
    pageY() {
      this.window = window.pageYOffset;
    },
    selectComponent(item) {
      if (this.active === item) return;
      this.resetActive();
      this.active = item;
      this.$refs[item].classList.add('active');
      this.$router.push({
        name: item,
        params: {
          storeId: this.storeId,
        },
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
  width: 100%;
}
.main-content-wrapper {
  @include flexbox;
  @include justify-content(center);
  @include lg-pc {
    width: calc(100% - 200px);
    margin-left: 200px;
  }
  @include pc {
    width: calc(100% - 200px);
    margin-left: 200px;
  }
  @include mobile {
    margin-top: 40px;
  }
  @include xs-mobile {
    margin-top: 35px;
  }
}
.main-content {
  width: 100%;
  padding: 5px 10px 5px;
  max-width: 1200px;
  min-height: 90vh;
}
.store-title {
  font-size: 30px;
  font-weight: 800;
  @include pc {
    font-size: 26px;
  }
  @include mobile {
    font-size: 22px;
  }
  @include xs-mobile {
    font-size: 20px;
  }
}
.header-container {
  position: relative;
}
.nav-aside {
  z-index: 9;
  position: fixed;
  left: 3px;
  // text-align: center;
  font-size: 1rem;
  padding: 10px 0;
  margin: 0;
  width: 200px;
  min-height: 100vh;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  cursor: pointer;

  @include mobile {
    @include flexbox;
    @include justify-content(space-evenly);
    padding: 5px;
    width: 100%;
    background-color: white;
    min-height: 5px;
    flex-grow: 0;
  }
  @include xs-mobile {
    @include flexbox;
    @include justify-content(space-evenly);
    padding: 3px;
    width: 100%;
    background-color: white;
    min-height: 5px;
    flex-grow: 0;
  }
}
.nav-item {
  @include flexbox;
  @include justify-content(space-between);
  @include align-items(center);
  padding: 10px 10px 10px;
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
    font-size: 13px;
    @include justify-content(center);
  }
  @include xs-mobile {
    padding: 2px;
    flex-basis: 25%;
    font-size: 11px;
    @include justify-content(center);
  }
}
.store-thumbnail {
  img {
    height: 10vw;
    object-fit: cover;
    object-position: center 50%;
    width: 100%;
  }
}
.logo-and-title {
  height: 10vw;
  position: absolute;
  top: 0px;
  color: white;
  width: 100%;
  padding: 10px 20px;
  border-top: 5px $purple200 solid;
  @include flexbox;
  @include align-items(center);
  background-color: rgba(0, 0, 0, 0.4);
}
.store-logo {
  $length: clamp(20px, 8vw, 130px);
  width: $length;
  height: $length;
  box-shadow: 0 0 3px rgba(255, 255, 255, 0.6);
  object-fit: cover;
  object-position: center 50%;
  margin-right: 10px;
  border-radius: 10px;
}
.time-deal-ani {
  font-family: neon;
  font-weight: bold;
  color: #fb4264;
  // text-shadow: 0 0 10px #f40a35;
  animation: neon 1s ease infinite;
  -moz-animation: neon 1s ease infinite;
  -webkit-animation: neon 1s ease infinite;
}

.header-thumbnail {
  padding: 1%;
  border: 2px $gray200 solid;
}
@keyframes neon {
  0%,
  70% {
    //   text-shadow: 0 0 1vw #FA1C16, 0 0 3vw #FA1C16, 0 0 10vw #FA1C16, 0 0 10vw #FA1C16, 0 0 .4vw #FED128, ;
    color: #f40a35;
  }
  50% {
    text-shadow: 0 0 2px #d3cac9, 0 0 4px #d8918f, 0 0 6px #f08c64, 0 0 8px #800e0b;
    //   color: #806914;
  }
}
</style>
