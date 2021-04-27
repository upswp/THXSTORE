<template>
  <div class="user-container">
    <user-navigation @clickButton="clickButton"></user-navigation>
    <div class="user-container-center">
      <keep-alive>
        <component :is="nav"></component>
      </keep-alive>
    </div>
  </div>
</template>

<script>
import UserNavigation from '@/components/user/UserNavigation';
import UserProfile from '@/components/user/category/UserProfile';
import StoreEnrollmentForm from '@/components/store/StoreEnrollmentForm';

export default {
  components: {
    UserNavigation,
    UserProfile,
    StoreEnrollmentForm,
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
