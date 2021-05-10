<template>
  <transition name="slide-vertical">
    <div v-show="isMainDrawerOpend" class="drawer-container">
      <nav class="navigation-icon-wrapper">
        <div ref="time-icon" class="drawer-item active" @click="moveToPage('time')">
          <span class="drawer-icon-wrapper">
            <awesome icon="meteor"></awesome>
          </span>
          <span class="detail-guide-text">타임 딜!</span>
        </div>
        <div ref="profile-icon" class="drawer-item" @click="moveToPage('user')">
          <span class="drawer-icon-wrapper">
            <awesome icon="user-circle"></awesome>
          </span>
          <span class="detail-guide-text">프로필</span>
        </div>
        <div ref="store-icon" class="drawer-item" @click="moveToPage('store')">
          <span class="drawer-icon-wrapper">
            <awesome icon="store"></awesome>
          </span>
          <span class="detail-guide-text">내 가게</span>
        </div>
        <div ref="admin-icon" class="drawer-item" @click="moveToPage('admin')">
          <span class="drawer-icon-wrapper">
            <awesome icon="clipboard-list"></awesome>
          </span>
          <span class="detail-guide-text">관리자 대시보드</span>
        </div>
      </nav>
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
    moveToPage(name) {
      if (this.$router.history.current.fullPath.includes(name)) return;
      this.$router.push({ name });
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
