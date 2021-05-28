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
        <div v-if="drawers.userDrawer" v-click-outside="resetDrawer" class="drawer-container">
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
              <td><div class="drawer-item" @click="moveToPage('userReviews')">내가 쓴 리뷰</div></td>
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
.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 3;
  width: 100%;
  background-color: rgba(215, 219, 221, 0.698);
  @include flexbox;
  @include align-items(center);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  font-size: 1.4rem;
  @include transition(all 0.5s);
  &.top {
    background-color: white;
    & .title {
      color: $blue600;
    }
    & .drawer-icon {
      color: $gray600;
      &:hover {
        color: $blue400;
      }
    }
  }
}
summary:first-of-type {
  list-style-type: none;
}
.fixed-logo-wrapper {
  flex-grow: 1;
  @include flexbox;
  @include align-items(center);
}
.logo-wrap {
  font-family: 'Pacifico', cursive;
  padding-left: 10px;
  color: $blue800;
  @include flexbox;
  position: relative;
  .store {
    padding-right: 0.3rem;
  }
}
.navigation-drawer-button {
  position: relative;
  z-index: 4;
  width: 50px;
  padding: 10px;
  @include flexbox;
  @include justify-content(center);
}
.drawer-container {
  position: absolute;
  width: max-content;
  top: 48px;
  right: 0px;
  @include md-font;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  background-color: white;
}
.drawer-icon {
  cursor: pointer;
  color: $gray800;
  @include transition(color 0.1s);
  &:hover {
    color: $blue800;
  }
  svg {
    width: 25px;
    height: 25px;
  }
}

.drawer-item {
  @include flexbox;
  @include align-items(center);
  padding: 10px 20px;
  cursor: pointer;
  @include transition(all 0.2s);
  &:hover {
    background-color: $blue600;
    color: white;
  }
  &::after {
    display: inline-block;
    content: '\2713';
    padding-left: 10px;
    font-weight: bolder;
    color: white;
  }
}
table {
  border-collapse: collapse;
}
td {
  border: 1px solid $gray200;
}
.block-for-logo {
  @include lg-pc {
    display: none;
  }
  @include pc {
    display: none;
  }
}

@include slide-down-transition(0.2s);
</style>
