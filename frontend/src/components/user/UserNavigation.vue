<template>
  <nav class="user-nav-container">
    <div class="nav-buttons">
      <button ref="currentStore" class="active" @click="selectComponent('currentStore')">예약 목록</button>
      <span>|</span>
      <button ref="recentReviews" @click="selectComponent('recentReviews')">최근 리뷰</button>
      <span>|</span>
      <button ref="userProfile" @click="selectComponent('userProfile')">프로필</button>
      <span>|</span>
      <button ref="resetPassword" @click="selectComponent('resetPassword')">비밀번호 변경</button>
      <span>|</span>
      <button ref="registerProductor" @click="selectComponent('registerProductor')">판매자 신청</button>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      active: 'currentStore',
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
  max-width: 800px;
  padding: 20px 40px;
  @include flexbox;

  .nav-buttons {
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
  }
}
@include mobile() {
  .user-nav-container {
    padding: 0;
    margin-bottom: 20px;
    .nav-buttons {
      font-size: 14px;
      // display: none;
    }
  }
}
@include xs-mobile() {
  .user-nav-container {
    .nav-buttons {
      font-size: 11px;
    }
  }
}
</style>
