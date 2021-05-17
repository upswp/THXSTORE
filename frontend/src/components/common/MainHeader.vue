<template>
  <header class="fixed-header">
    <div class="fixed-header-logo">
      <div class="block-for-logo"></div>
      <div class="logo-title" @click="moveToPage('main')">
        <ul class="logo-wrap">
          <li><awesome icon="store" class="store"></awesome></li>
          <li><span class="title">Thx!Store</span></li>
        </ul>
      </div>
      <div></div>
    </div>
    <div class="navigation-drawer-button" @click="toggleDrawer">
      <awesome icon="bars"></awesome>
    </div>
  </header>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
  methods: {
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
.block-for-logo {
  @include lg-pc {
    display: none;
  }
  @include pc {
    display: none;
  }
}
.logo-title {
  @include lg-pc {
    padding-left: 10px;
  }
  @include pc {
    padding-left: 10px;
  }
}
.fixed-header {
  z-index: 10;
  position: fixed;
  top: 0;
  width: 100%;
  background-color: white;
  @include flexbox;
  @include justify-content(flex-end);
  @include align-items(center);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  font-size: 1.8rem;
  @include mobile() {
    font-size: 1.4rem;
  }
  @include xs-mobile() {
    font-size: 1.4rem;
  }
  .navigation-drawer-button {
    width: 50px;
    padding: 10px;
    cursor: pointer;
    z-index: 11;
    @include flexbox;
    @include justify-content(center);
  }
  .fixed-header-logo {
    width: 100%;
    position: fixed;
    @include flexbox;
    @include justify-content(space-between);
    @include align-items(center);
    .logo-wrap {
      @include user-select(none);
      font-family: 'Pacifico', cursive;
      color: $blue800;
      font-size: 2rem;
      @include mobile() {
        font-size: 1.4rem;
      }
      @include xs-mobile() {
        font-size: 1.4rem;
      }
      @include flexbox;
      position: relative;
      cursor: pointer;
      .store {
        padding-right: 0.3rem;
      }
    }
  }
}
</style>
