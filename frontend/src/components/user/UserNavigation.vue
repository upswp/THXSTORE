<template>
  <nav class="user-nav-container">
    <div class="nav-buttons">
      <button ref="currentStore" @click="selectComponent('currentStore')">예약 목록</button>
      <span>|</span>
      <button ref="recentReviews" @click="selectComponent('recentReviews')">최근 리뷰</button>
      <span>|</span>
      <button ref="userProfile" class="active" @click="selectComponent('userProfile')">프로필</button>
      <span>|</span>
      <button ref="resetPassword" @click="selectComponent('resetPassword')">비밀번호 변경</button>
      <span>|</span>
      <button ref="storeEnrollmentForm" @click="selectComponent('storeEnrollmentForm')">판매자 신청</button>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      active: 'userProfile',
    };
  },
  methods: {
    selectComponent(item) {
      if (this.active === item) return;
      this.resetActive();
      this.active = item;
      this.$refs[item].classList.add('active');
      const capitalize = ([firstLetter, ...rest]) => `${firstLetter.toUpperCase()}${rest.join('')}`;
      this.$emit('clickButton', capitalize(item));
    },
    resetActive() {
      this.$refs[this.active].classList.remove('active');
    },
  },
};
</script>

<style lang="scss" scoped>
.user-nav-container {
  // margin-bottom: 20px;
  padding: 20px 40px;
  margin-bottom: 40px;
  @include flexbox;
  @include justify-content(center);
  @include mobile() {
    padding: 10px 30px;
  }
  @include xs-mobile() {
    padding: 10px 20px;
  }
}
.nav-buttons {
  max-width: 800px;
  width: 100%;
  @include flexbox;
  @include justify-content(space-between);
  font-size: 18px;
  & > span {
    color: $gray600;
    padding: 5px 0;
  }
  & > button {
    @include ellipsis;
    @include user-select(none);
    color: $gray600;
    background: $gray100;
    display: block;
    border: none;
    padding: 5px 0;
    &.active {
      color: black;
      font-weight: 600;
    }
    &:hover {
      color: black;
    }
  }
  @include mobile() {
    font-size: 14px;
  }
  @include xs-mobile() {
    font-size: 11px;
  }
}
</style>
