<template>
  <div class="user-container">
    <div class="user-container-center">
      <transition name="slide-right" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@include slide-left-transition(0.4s);
@include slide-right-transition(0.4s);
.user-container {
  width: 100%;
  min-height: 100vh;

  .user-container-center {
    @include flexbox;
    @include justify-content(center);
    padding: 0px 40px;
    @include mobile {
      padding: 0 20px;
    }
    @include xs-mobile {
      padding: 0 10px;
    }
  }
}
</style>
