<template>
  <div class="user-store-container">
    <nav class="sticky-menu">
      <input id="toggle-button" v-model="toggleMenu" type="checkbox" />
      <label for="toggle-button" class="toggle-label">
        <awesome icon="bars" />
      </label>
      <div class="menu-background"></div>
      <div class="nav-aside">
        <ul>
          <li class="nav-item" @click="selectComponent('info')">
            <i class="material-icons"> info </i>
            가게 정보
          </li>
          <li class="nav-item" @click="selectComponent('menu')">
            <i class="material-icons"> list_alt </i>
            메뉴
          </li>
          <li class="nav-item" @click="selectComponent('timedeal')">
            <i class="material-icons"> alarm_on </i>
            타임 딜
          </li>
          <li class="nav-item" @click="selectComponent('live')">
            <i class="material-icons"> live_tv </i>
            라이브 스토어
          </li>
          <li class="nav-item" @click="selectComponent('review')">
            <i class="material-icons"> drive_file_rename_outline </i>
            리뷰
          </li>
        </ul>
      </div>
    </nav>
    <section v-if="storeInfoLoaded" class="main-content-wrapper">
      <div class="main-content">
        <header class="header-container">
          <figure class="store-thumbnail"><img :src="sideInfo.thumbImg" /></figure>
          <div class="logo-and-title">
            <img class="store-logo" :src="sideInfo.logo" />
            <div class="store-title">{{ baseInfo.name }}</div>
          </div>
        </header>
        <br />
        <router-view
          :base-info="baseInfo"
          :side-info="sideInfo"
          :menu-group-list-loaded="menuGroupListLoaded"
          :menu-group-list="menuGroupList"
          :time-deal="timeDeal"
          :time-deal-loaded="timeDealLoaded"
          :review-list="reviewList"
          :review-list-loaded="reviewListLoaded"
        ></router-view>
      </div>
    </section>
  </div>
</template>

<script>
import { getStoreInfo, getStoreMenu, getStoreTimedeal } from '@/api/userStore';
import { getStoreReview } from '@/api/userOrder';
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      active: '',
      storeId: this.$route.params.storeId,
      toggleMenu: false,
      baseInfo: {},
      sideInfo: {},
      menuGroupList: [],
      timeDeal: [],
      reviewList: [],
      storeInfoLoaded: false,
      menuGroupListLoaded: false,
      timeDealLoaded: false,
      reviewListLoaded: false,
    };
  },
  created() {
    this.active = this.$route.name;
    this.setSpinnerState(true);
    this.loadStoreInfo();
    this.loadStoreMenu();
    this.loadStoreTimeDeal();
    this.loadStoreReview();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    selectComponent(item) {
      if (this.active === item) return;
      this.active = item;
      this.toggleMenu = false;
      this.$router.push({
        name: item,
        params: {
          storeId: this.storeId,
        },
      });
    },
    loadStoreInfo() {
      getStoreInfo(this.storeId)
        .then(({ data }) => {
          const { baseInfo, sideInfo } = data;
          if (!sideInfo.thumbImg) this.sideInfo.thumbImg = require('@/assets/image/thumbnail_example.jpg');
          if (!sideInfo.logo) this.sideInfo.logo = require('@/assets/image/logo.jpg');
          this.baseInfo = baseInfo;
          this.sideInfo = sideInfo;
          this.storeInfoLoaded = true;
        })
        .catch(error => {
          this.setSpinnerState(false);
          console.log(error);
          alert('가게 정보를 불러오는데 실패했습니다.');
        });
    },
    loadStoreMenu() {
      getStoreMenu(this.storeId)
        .then(({ data }) => {
          data.forEach(x => {
            x['isShow'] = true;
          });
          this.menuGroupList = data;
          this.menuGroupListLoaded = true;
        })
        .catch(error => {
          this.setSpinnerState(false);
          console.log(error);
          alert('메뉴 리스트를 불러오는데 실패했습니다.');
        });
    },
    loadStoreTimeDeal() {
      getStoreTimedeal(this.storeId)
        .then(({ data }) => {
          this.timeDeal = data.timeDeal.map(menu => {
            const originComputed = (menu.price * (100 - menu.rate)) / 100;
            return Object.assign(menu, {
              count: 0,
              computed: 0,
              selected: false,
              discounted: Math.floor(originComputed / 100) * 100,
              height: menu.stock > 30 ? 130 : menu.stock > 20 ? 120 : 110,
              payFor: 0,
              selected: false,
            });
          });
          this.timeDealLoaded = true;
        })
        .catch(error => {
          this.setSpinnerState(false);
          console.error(error);
          if (error.response.status !== 400) alert('타임딜 메뉴 목록을 불러오는데 실패하였습니다.');
          else this.timeDealLoaded = true;
        });
    },
    loadStoreReview() {
      getStoreReview(this.storeId)
        .then(({ data }) => {
          this.reviewList = data.map(review => {
            review['answerLoaded'] = false;
            return review;
          });
          this.reviewListLoaded = true;
        })
        .catch(error => {
          this.setSpinnerState(false);
          console.log(error);
          alert('가게 리뷰를 불러오는데 실패하였습니다.');
        });
    },
  },
};
</script>

<style scoped lang="scss">
.user-store-container {
  width: 100%;
}
.toggle-label,
.menu-background {
  position: fixed;
  top: 65px;
  right: 15px;
  @include cross-middle;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: $blue400;
  @include transition(all 0.6s);
  @include mobile {
    top: auto;
    bottom: 15px;
  }
  @include xs-mobile {
    top: auto;
    bottom: 10px;
    right: 10px;
    width: 40px;
    height: 40px;
  }
}

.toggle-label {
  z-index: 5;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.4);
  color: white;
  cursor: pointer;
  @include lg-font;
  &:hover {
    background-color: $blue600;
  }
}
.menu-background {
  z-index: 4;
  opacity: 0.2;
}
.nav-aside {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  @include cross-middle;
  opacity: 0;
  visibility: hidden;
  z-index: 4;
  @include transition(0.5s);
}
.nav-item {
  @include flexbox;
  @include align-items(center);
  color: white;
  font-weight: bold;
  font-size: 32px;
  @include mobile {
    font-size: 24px;
  }
  @include xs-mobile {
    font-size: 24px;
  }
  cursor: pointer;
  padding: 10px;
  @include transition(0.2s);
  transform-origin: left;
  &:hover {
    transform: scale(1.5);
    color: rgb(243, 240, 34);
  }
  i {
    margin-right: 5px;
  }
}
.main-content-wrapper {
  @include flexbox;
  @include justify-content(center);
}
.main-content {
  width: 100%;
  padding: 5px 10px 5px;
  max-width: 1200px;
  padding-bottom: 70px;
}
.header-container {
  position: relative;
}
$banner-height: clamp(100px, 13vw, 160px);
.store-thumbnail {
  margin: 0;
  img {
    width: 100%;
    height: $banner-height;
    object-fit: cover;
    object-position: center 50%;
  }
}
.logo-and-title {
  position: absolute;
  top: 0px;
  width: 100%;
  height: $banner-height;
  color: white;
  padding: 10px 20px;
  border-top: 5px $purple200 solid;
  @include flexbox;
  @include align-items(center);
  background-color: rgba(0, 0, 0, 0.4);
  @include mobile {
    padding: 10px;
  }
  @include xs-mobile {
    padding: 10px;
  }
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.store-logo {
  $length: clamp(20px, 8vw, 130px);
  width: $length;
  height: $length;
  box-shadow: 0 0 3px rgba(255, 255, 255, 0.6);
  object-fit: cover;
  object-position: center center;
  margin-right: 10px;
  border-radius: 10px;
}

#toggle-button {
  display: none;
  &:checked + .toggle-label {
    transform: rotate(360deg) scale(1.2);
    background-color: $blue600;
  }
  &:checked ~ .menu-background {
    opacity: 0.9;
    transform: scale(100);
  }
  &:checked ~ .nav-aside {
    visibility: visible;
    opacity: 1;
  }
}
</style>
