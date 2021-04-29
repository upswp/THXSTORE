<template>
  <div class="user-container">
    <user-navigation @clickButton="clickButton"></user-navigation>
    <div class="user-container-center">
      <transition name="slide-left">
        <keep-alive>
          <component :is="nav"></component>
        </keep-alive>
      </transition>
    </div>
  </div>
</template>

<script>
import UserNavigation from '@/components/user/UserNavigation';
import UserProfile from '@/components/user/category/UserProfile';
import StoreEnrollmentForm from '@/components/user/category/StoreEnrollmentForm';

export default {
  components: {
    UserNavigation,
    UserProfile,
    StoreEnrollmentForm,
  },
  data() {
    return {
      nav: 'UserProfile',
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
@include slide-transition;
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
