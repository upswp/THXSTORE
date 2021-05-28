<template>
  <header ref="header" class="fixed-header top">
    <div class="fixed-logo-wrapper">
      <ul class="logo-wrap" @click="moveToPage('main')">
        <li><span class="title">Thx!Store</span></li>
      </ul>
    </div>
    <div class="navigation-drawer-button">
      <div class="drawer-icon" @click="toggleDrawer('timeDealDrawer')"><awesome icon="meteor"></awesome></div>
      <transition name="slide-down">
        <div v-if="drawers.timeDealDrawer" class="drawer-container">
          <table>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('feed')">내 주변 타임딜</div>
              </td>
            </tr>
          </table>
        </div>
      </transition>
    </div>
    <div class="navigation-drawer-button">
      <div class="drawer-icon" @click="toggleDrawer('storeDrawer')"><awesome icon="store-alt"></awesome></div>
      <transition name="slide-down">
        <div v-if="drawers.storeDrawer" class="drawer-container">
          <table>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeInfo')">내 가게</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeProduct')">메뉴 등록/수정</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeTimeDeal')">타임딜 등록/확인</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeLive')">라이브 챗</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeReservation')">주문 내역</div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="drawer-item" @click="moveToPage('storeReview')">리뷰 관리</div>
              </td>
            </tr>
          </table>
        </div>
      </transition>
    </div>
    <div class="navigation-drawer-button">
      <div class="drawer-icon" @click="toggleDrawer('userDrawer')">
        <awesome icon="user-circle"></awesome>
      </div>
      <transition name="slide-down">
        <div v-if="drawers.userDrawer" class="drawer-container">
          <table>
            <tr>
              <td><div class="drawer-item" @click="moveToPage('userProfile')">프로필</div></td>
            </tr>
            <tr>
              <td><div class="drawer-item" @click="moveToPage('userPasswordReset')">비밀번호변경</div></td>
            </tr>
            <tr>
              <td><div class="drawer-item" @click="moveToPage('userOrderList')">예약목록</div></td>
            </tr>
            <tr>
              <td><div class="drawer-item" @click="moveToPage('userRecentReviews')">내가 쓴 리뷰</div></td>
            </tr>
            <tr>
              <td><div class="drawer-item" @click="moveToPage('userStoreApplication')">판매자 신청/수정</div></td>
            </tr>
            <tr>
              <td><div class="drawer-item" @click="logout">로그아웃</div></td>
            </tr>
          </table>
        </div>
      </transition>
    </div>
  </header>
</template>

<script>
import { mapMutations } from 'vuex';
import { clearLocalStorageItem, USER_INFO, TOKEN } from '@/utils/webStorage';
export default {
  data() {
    return {
      drawers: {
        timeDealDrawer: false,
        userDrawer: false,
        storeDrawer: false,
      },
    };
  },

  computed: {
    path() {
      return this.$route.fullPath;
    },
  },
  watch: {
    path() {
      this.resetDrawer();
    },
  },
  created() {
    this.resetDrawer();
    this.addScrollEvent();
  },
  beforeDestroy() {
    this.removeScrolEvent();
  },
  methods: {
    ...mapMutations(['setToken, clearUserInfo']),
    addScrollEvent() {
      window.addEventListener('scroll', this.handleScroll);
    },

    removeScrollEvent() {
      window.removeEventListener('scroll', this.handleScroll);
    },
    handleScroll() {
      if (window.scrollY === 0) {
        this.$refs.header.classList.add('top');
      } else if (this.$refs.header.classList.contains('top')) this.$refs.header.classList.remove('top');
    },

    moveToPage(name) {
      if (!this.$router.history.current.fullPath.includes('main')) this.$router.push({ name });
    },

    logout() {
      clearLocalStorageItem(USER_INFO);
      clearLocalStorageItem(TOKEN);

      // vuex도 비운다.
      this.setToken('');
      this.clearUserInfo();

      // drawer를 닫는다.
      this.moveToPage('login');
    },

    toggleDrawer(label) {
      if (this.drawers[label]) this.drawers[label] = false;
      else {
        this.resetDrawer();
        this.drawers[label] = true;
      }
    },
    resetDrawer() {
      for (const drawer in this.drawers) {
        this.drawers[drawer] = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
