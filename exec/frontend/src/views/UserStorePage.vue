<template>
  <div class="userstore-container">
    <nav class="sticky-menu">
      <input id="toggle-button" v-model="toggleMenu" type="checkbox" />
      <label for="toggle-button" class="toggle-label">
        <awesome icon="bars"></awesome>
      </label>
      <div class="menu-background"></div>
      <div class="nav-aside">
        <ul>
          <li ref="info" class="active nav-item" @click="selectComponent('info')">
            <i class="material-icons"> info </i>
            가게 정보
          </li>
          <li ref="menu" class="nav-item" @click="selectComponent('menu')">
            <i class="material-icons"> list_alt </i>
            메뉴
          </li>
          <li ref="timedeal" class="nav-item" @click="selectComponent('timedeal')">
            <i class="material-icons"> alarm_on </i>
            타임 딜
          </li>
          <li ref="live" class="nav-item" @click="selectComponent('live')">
            <i class="material-icons"> live_tv </i>
            라이브 스토어
          </li>
          <li ref="userStoreReview" class="nav-item" @click="selectComponent('review')">
            <i class="material-icons"> drive_file_rename_outline </i>
            리뷰
          </li>
        </ul>
      </div>
    </nav>
    <div v-if="storeInfoLoaded" class="main-content-wrapper">
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
    </div>
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
      window: '',
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
  computed: {},
  created() {
    this.active = this.$route.name;
    this.setSpinnerState(true);
    this.loadStoreInfo();
    this.loadStoreMenu();
    this.loadStoreTimeDeal();
    this.loadStoreReview();
  },
  mounted() {},
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
          this.baseInfo = baseInfo;
          this.sideInfo = sideInfo;
          if (!this.sideInfo.thumbImg) this.sideInfo.thumbImg = require('@/assets/image/thumbnail_example.jpg');
          if (!this.sideInfo.logo) this.sideInfo.logo = require('@/assets/image/logo.jpg');
          this.storeInfoLoaded = true;
        })
        .catch(error => {
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
          console.log(error);
          alert('타임딜 메뉴 목록을 불러오는데 실패하였습니다.');
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
          console.log(error);
          alert('가게 리뷰를 불러오는데 실패하였습니다.');
        });
    },
  },
};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample';
</style>
