<template>
  <transition name="slide-horizon">
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
      this.$refs['time-icon'].classList.remove('active');
      this.$refs['profile-icon'].classList.remove('active');
      this.$refs['store-icon'].classList.remove('active');
      this.$refs.time.classList.remove('active');
      this.$refs.profile.classList.remove('active');
      this.$refs.store.classList.remove('active');
    },
    setBtnActive(e) {
      this.resetAside();
      this.$refs[e].classList.add('active');
      this.$refs[`${e}-icon`].classList.add('active');
    },
  },
};
</script>

<style lang="scss" scoped>
.slide-horizon-enter-active,
.slide-horizon-leave-active {
  transition-duration: 0.5s;
  transition-property: height, opacity, transform;
  transition-timing-function: cubic-bezier(0.55, 0, 0.1, 1);
  overflow: hidden;
}
.slide-horizon-leave-active,
.slide-horizon-enter {
  opacity: 0;
  @include transform(translate(-10em, 0));
}
.drawer-container {
  position: fixed;
  top: 50px;
  height: 100vh;
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
  background: $gray600;
  .navigation-icon-wrapper {
    transition: all 0.5s;
    // header 크기가 변함에 따라 top 변경해줘야함.
    font-size: 1.8rem;
    width: 50px;
    height: 100vh;
    padding: 20px 0;
    background-color: white;
    z-index: 2;
    .drawer-item {
      height: 50px;
      @include flexbox;
      @include justify-content(center);
      @include align-items(center);
      transition: all 0.3s;
      color: black;
      padding: 10px;
      cursor: pointer;
      &.active {
        color: white;
        background-color: $blue600;
      }
    }
  }
  .navigation-drawer {
    padding: 20px 0;
    height: 100vh;
    background-color: white;
    z-index: 1;
    .detail-guide {
      height: 50px;
      line-height: 50px;
      font-size: 20px;
      cursor: pointer;

      color: $navy800;
      @include flexbox;
      @include justify-content(space-between);
      .detail-guide-text {
        @include user-select(none);
        margin: {
          right: 100px;
          left: 10px;
        }
      }
      &::after {
        content: '';
        width: 5px;
        transition: all 0.3s;
      }
      &:hover {
        &::after {
          background-color: $blue800;
        }
      }
    }
    .active {
      color: white;
      background-color: $blue600;
    }
  }
}
</style>
