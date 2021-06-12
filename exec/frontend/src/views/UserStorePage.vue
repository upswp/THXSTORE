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
            타임딜
          </li>
          <li ref="live" class="nav-item" @click="selectComponent('live')">
            <i class="material-icons"> live_tv </i>
            라이브커머스
          </li>
          <li ref="userStoreReview" class="nav-item" @click="selectComponent('userStoreReview')">
            <i class="material-icons"> drive_file_rename_outline </i>

            리뷰
          </li>
        </ul>
      </div>
    </nav>
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
      toggleMenu: false,
    };
  },
  computed: {
    path() {
      return this.$router.history.current;
    },
  },
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
      this.active = item;
      this.toggleMenu = false;
      this.$router.push({
        name: item,
        params: {
          storeId: this.storeId,
        },
      });
    },
  },
};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample';
</style>
