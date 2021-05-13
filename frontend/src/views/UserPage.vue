<template>
  <div class="user-container">
    <user-navigation :to="to" @clickButton="clickButton"></user-navigation>
    <div class="user-container-center">
      <transition name="slide-right" mode="out-in">
        <keep-alive>
          <component :is="nav" @changeTab="clickButton"></component>
        </keep-alive>
      </transition>
    </div>
  </div>
</template>

<script>
import UserNavigation from '@/components/user/UserNavigation';
import UserProfile from '@/components/user/category/UserProfile';
import StoreEnrollmentForm from '@/components/user/category/StoreEnrollmentForm';
import PasswordReset from '@/components/user/category/PasswordReset';
export default {
  components: {
    UserNavigation,
    UserProfile,
    StoreEnrollmentForm,
    PasswordReset,
  },
  data() {
    return {
      nav: 'UserProfile',
      to: 'UserProfile',
    };
  },
  created() {
    if (this.$store.state.rewriteBaseInfo) {
      this.nav = 'StoreEnrollmentForm';
      this.to = 'StoreEnrollmentForm';
      this.$store.commit('setRewriteBaseInfo', false);
    }
  },
  methods: {
    clickButton(e) {
      this.nav = e;
      this.to = e;
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
