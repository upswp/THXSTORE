<template>
  <transition name="slide-horizon">
    <aside v-if="isMainDrawerOpend" class="navigation-drawer"></aside>
  </transition>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters(['isMainDrawerOpend']),
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
  @include transform(translate(-2em, 0));
}
.navigation-drawer {
  position: fixed;
  top: 56px;
  @include mobile() {
    top: 45px;
  }
  @include xs-mobile() {
    width: 100%;
  }
  width: 300px;
  height: 100vh;
  background-color: $gray600;
  z-index: 1;
}
</style>
