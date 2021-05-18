<template>
  <nav class="user-nav-container">
    <div class="nav-buttons">
      <button ref="UserProfile" class="active" @click="selectComponent('UserProfile')">프로필</button>
      <span>|</span>
      <button ref="PasswordReset" @click="selectComponent('PasswordReset')">비밀번호 변경</button>
      <span>|</span>
      <button ref="OrderList" @click="selectComponent('OrderList')">예약 목록</button>
      <span>|</span>
      <button ref="RecentReviews" @click="selectComponent('RecentReviews')">최근 리뷰</button>
      <span>|</span>
      <button ref="StoreEnrollmentForm" @click="selectComponent('StoreEnrollmentForm')">판매자 신청/수정</button>
    </div>
  </nav>
</template>

<script>
export default {
  props: {
    to: {
      type: String,
      default: '',
      require: true,
    },
  },
  data() {
    return {
      active: 'UserProfile',
    };
  },
  watch: {
    to(newValue) {
      this.resetActive();
      this.active = newValue;
      this.$refs[newValue].classList.add('active');
    },
  },
  methods: {
    selectComponent(item) {
      if (this.active === item) return;
      this.resetActive();
      this.active = item;
      this.$refs[item].classList.add('active');
      this.$emit('clickButton', item);
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
    background: white;
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
