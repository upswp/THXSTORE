<template>
  <div class="store-nav-container">
    <div class="store-nav-items">
      <label ref="info" class="nav-item active" for="info-radio">기본정보</label>
      <input id="info-radio" v-model="tap" type="radio" value="info" style="display: none" />

      <label ref="product" class="nav-item" for="product-radio">상품관리</label>
      <input id="product-radio" v-model="tap" type="radio" value="product" style="display: none" />

      <label ref="deal" class="nav-item" for="time-deal-radio">타임딜 관리</label>
      <input id="time-deal-radio" v-model="tap" type="radio" value="deal" style="display: none" />

      <label ref="live" class="nav-item" for="live-radio">라이브 커머스</label>
      <input id="live-radio" v-model="tap" type="radio" value="live" style="display: none" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tap: 'info',
    };
  },
  watch: {
    tap(newValue, oldValue) {
      if (newValue !== oldValue) {
        this.$refs[oldValue].classList.remove('active');
        this.$refs[newValue].classList.add('active');
        this.$emit('changeTap', newValue);
      }
    },
  },
  created() {
    for (const name of ['info', 'product', 'deal', 'live']) {
      if (this.$router.history.current.fullPath.includes(name)) {
        this.tap = name;
      }
    }
  },
};
</script>

<style lang="scss" scoped>
// @import '@/assets/scss/sample';
.store-nav-items {
  @include lg-pc() {
  }
  @include pc() {
  }
  @include mobile() {
    @include flexbox;
    width: 100%;
  }
  @include xs-mobile() {
    @include flexbox;
    width: 100%;
  }
}
.nav-item {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  cursor: pointer;
  text-align: center;
  padding: 10px 0;
  &:hover {
    background-color: $blue400;
    color: white;
  }
  &.active {
    background-color: $blue400;
    color: white;
  }
  @include lg-pc() {
    width: calc(100% - 5px);
    display: block;
    &:hover {
      box-sizing: content-box;
      border-right: 5px solid $blue400;
      &.active {
        border: none;
      }
    }
    &.active {
      width: 100%;
    }
  }
  @include pc() {
    width: calc(100% - 5px);
    display: block;
    &:hover {
      box-sizing: content-box;
      border-right: 5px solid $blue400;
      &.active {
        border: none;
      }
    }
    &.active {
      width: 100%;
    }
  }
  @include mobile() {
    font-size: 14px;
    width: 25%;
  }
  @include xs-mobile() {
    font-size: 12px;
    width: 25%;
  }
}
</style>
