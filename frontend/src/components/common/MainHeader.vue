<template>
  <header class="fixed-header">
    <div class="navigation-drawer-button" @click="toggleDrawer">
      <awesome icon="bars"></awesome>
    </div>
    <div class="logo-title" @click="moveToPage('main')">
      <ul class="logo-wrap">
        <li><awesome icon="store" class="store"></awesome></li>
        <li><span class="title">Thx!Store</span></li>
      </ul>
    </div>
    <div class="account-button">
      <awesome icon="user-circle" @click="toggleUserDrawer"></awesome>
      <transition name="slide-vertical">
        <div v-show="!userDrawer" class="user-nav"></div>
      </transition>
    </div>
  </header>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      userDrawer: false,
    };
  },
  methods: {
    toggleUserDrawer() {
      this.userDrawer = !this.userDrawer;
    },
    moveToPage(name) {
      if (!this.$router.history.current.fullPath.includes('main')) this.$router.push({ name: 'main' });
    },
    ...mapMutations({
      toggleDrawer: 'toggleMainDrawerOpen',
    }),
  },
};
</script>

<style lang="scss" scoped>
.fixed-header {
  z-index: 0;
  position: fixed;
  top: 0;
  width: 100%;
  padding: 10px 20px;
  @include flexbox;
  @include justify-content(space-between);
  @include align-items(center);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  @include transition(font-size 0.5s);
  font-size: 1.8rem;
  @include mobile() {
    font-size: 1.2rem;
  }
  .navigation-drawer-button {
    cursor: pointer;
    z-index: 1;
  }
  .logo-wrap {
    @include user-select(none);
    font-family: 'Pacifico', cursive;
    color: $blue800;
    font-size: 2rem;
    transition: font-size 0.5s;
    @include mobile() {
      font-size: 1.4rem;
    }
    @include flexbox;
    position: relative;
    cursor: pointer;
    .store {
      padding-right: 0.3rem;
    }
  }
  .account-button {
    z-index: 1;
    position: relative;
    cursor: pointer;
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
      @include transform(translate(0, -0.5rem));
    }
    .user-nav {
      position: absolute;
      padding: 10px;
      width: 200px;
      height: 200px;
      top: 46px;
      right: -20px;
      @include mobile() {
        top: 35px;
      }
      @include xs-mobile() {
        width: 100vw;
      }

      border-radius: 5px;
      background: $gray100;
      box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.1);
      z-index: 1;
    }
  }
}
</style>
