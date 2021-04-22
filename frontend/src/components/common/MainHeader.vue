<template>
  <header class="fixed-header">
    <div class="navigation-drawer-button" @click="toggleDrawer">
      <awesome icon="bars"></awesome>
    </div>
    <div class="fixed-header-logo">
      <div></div>
      <div class="logo-title" @click="moveToPage('main')">
        <ul class="logo-wrap">
          <li><awesome icon="store" class="store"></awesome></li>
          <li><span class="title">Thx!Store</span></li>
        </ul>
      </div>
      <div></div>
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
.fixed-header {
  z-index: 0;
  position: fixed;
  top: 0;
  width: 100%;
  @include flexbox;
  @include justify-content(space-between);
  @include align-items(center);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  @include transition(font-size 0.5s);
  font-size: 1.8rem;
  @include mobile() {
    font-size: 1.4rem;
  }
  .navigation-drawer-button {
    width: 50px;
    padding: 10px;
    cursor: pointer;
    z-index: 1;
    @include flexbox;
    @include justify-content(center);
  }
  .fixed-header-logo {
    width: 100%;
    @include flexbox;
    @include justify-content(space-between);
    @include align-items(center);
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
  }
}
</style>
