<template>
  <nav class="user-nav-container">
    <div class="nav-buttons">
      <button v-focus @click="clickButton('currentStore')">예약 목록</button>
      <span>|</span>
      <button @click="clickButton('recentReviews')">최근 리뷰</button>
      <span>|</span>
      <button @click="clickButton('profile')">프로필</button>
      <span>|</span>
      <button @click="clickButton('resetPassword')">비밀번호 변경</button>
      <span>|</span>
      <button @click="clickButton('registerProductor')">판매자 신청</button>
    </div>
  </nav>
</template>

<script>
export default {
  methods: {
    clickButton(item) {
      this.$emit('clickButton', item);
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
      &:focus {
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
