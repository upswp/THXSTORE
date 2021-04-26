<template>
  <div class="user-container">
    <user-navigation @clickButton="clickButton"></user-navigation>
    <div class="user-container-center">
      <user-info-body :nav="nav"></user-info-body>
    </div>
  </div>
</template>

<script>
import UserNavigation from '@/components/user/UserNavigation';
import UserInfoBody from '@/components/user/UserInfoBody';
export default {
  components: {
    UserNavigation,
    UserInfoBody,
  },
  data() {
    return {
      nav: '',
    };
  },
  methods: {
    clickButton(e) {
      this.nav = e;
    },
  },
};
</script>

<style lang="scss" scoped>
.user-container {
  width: 100%;
  min-height: 100vh;

  .user-container-center {
    @include flexbox;
    @include justify-content(center);
  }
}
</style>
