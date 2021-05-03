<template>
  <transition name="slide-vertical">
    <div v-show="isMainDrawerOpend" class="drawer-container">
      <nav class="navigation-icon-wrapper">
        <div ref="time-icon" class="drawer-item active" @click="setBtnActive('time')">
          <awesome icon="meteor"></awesome>
        </div>
        <div ref="profile-icon" class="drawer-item" @click="setBtnActive('profile')">
          <awesome icon="user-circle"></awesome>
        </div>
        <div ref="store-icon" class="drawer-item" @click="setBtnActive('store')">
          <awesome icon="store"></awesome>
        </div>
        <div ref="admin-icon" class="drawer-item" @click="setBtnActive('admin')">
          <awesome icon="clipboard-list"></awesome>
        </div>
      </nav>
      <aside class="navigation-drawer">
        <div ref="time" class="detail-guide active" @click="setBtnActive('time')">
          <span class="detail-guide-text">타임 딜!</span>
        </div>
        <div ref="profile" class="detail-guide" @click="setBtnActive('profile')">
          <span class="detail-guide-text">프로필</span>
        </div>
        <div ref="store" class="detail-guide" @click="setBtnActive('store')">
          <span class="detail-guide-text">내 가게</span>
        </div>
        <div ref="admin" class="detail-guide" @click="setBtnActive('admin')">
          <span class="detail-guide-text" @click="moveToPage('admin')">관리자 대시보드</span>
        </div>
      </aside>
    </div>
  </transition>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters(['isMainDrawerOpend']),
  },
  methods: {
    resetAside() {
      const icons = ['time', 'profile', 'store', 'admin'];
      for (const icon of icons) {
        this.$refs[`${icon}-icon`].classList.remove('active');
        this.$refs[icon].classList.remove('active');
      }
    },
    setBtnActive(e) {
      this.resetAside();
      this.$refs[e].classList.add('active');
      this.$refs[`${e}-icon`].classList.add('active');
    },
    moveToPage(name) {
      console.log('이동가능함');
      this.$router.push({ name });
    },
  },
};
</script>

<style lang="scss" scoped>
.slide-vertical-enter-active,
.slide-vertical-leave-active {
  transition-duration: 0.5s;
  transition-property: height, opacity, transform;
  transition-timing-function: cubic-bezier(0.55, 0, 0.1, 1);
  overflow: hidden;
}
.slide-vertical-leave-active,
.slide-vertical-enter {
  opacity: 0;
  @include transform(translate(0, -10em));
}
.drawer-container {
  position: fixed;
  z-index: 2;
  top: 50px;
  right: 0px;
  box-shadow: 0 10px 5px rgba(0, 0, 0, 0.2);
  @include flexbox;
  @include mobile() {
    top: 44px;
    .navigation-drawer {
      display: none;
    }
    .navigation-icon-wrapper {
      .drawer-item {
        font-size: 1.4rem;
        &:hover {
          color: white;
          background-color: $blue600;
        }
      }
    }
  }
  @include xs-mobile() {
    top: 44px;
    .navigation-drawer {
      display: none;
    }
    .navigation-icon-wrapper {
      .drawer-item {
        font-size: 1.4rem;
        &:hover {
          color: white;
          background-color: $blue600;
        }
      }
    }
  }
  .navigation-icon-wrapper {
    @include transition(all 0.5s);
    // header 크기가 변함에 따라 top 변경해줘야함.
    font-size: 1.8rem;
    width: 50px;
    padding: 20px 0;
    background-color: white;
    border: none;
    .drawer-item {
      height: 50px;
      @include flexbox;
      @include justify-content(center);
      @include align-items(center);
      @include transition(all 0.5s);
      color: black;
      padding: 10px;
      cursor: pointer;
      border: 1px solid white;
      &.active {
        color: white;
        background-color: $blue600;
        border: 1px solid $blue600;
      }
    }
  }
  .navigation-drawer {
    position: relative;
    left: -1px;
    padding: 20px 0;
    background-color: white;
    z-index: 11;
    .detail-guide {
      height: 50px;
      line-height: 50px;
      font-size: 20px;
      cursor: pointer;
      color: $navy800;
      border: 1px solid white;
      @include transition(all 0.5s);
      @include flexbox;
      @include justify-content(space-between);
      .detail-guide-text {
        @include user-select(none);
        font-size: 1rem;
        margin: {
          right: 50px;
          left: 10px;
        }
      }
      &::after {
        content: '';
        width: 5px;
        @include transition(all 0.1s);
      }
      &:hover {
        &::after {
          background-color: $blue800;
        }
      }
      &.active {
        color: white;
        background-color: $blue600;
        border: 1px solid $blue600;
      }
    }
  }
}
</style>
