<template>
  <main class="store-product-detail">
    <div>Group1</div>
    <div class="menu-container">
      <div v-for="(menu, index) in 14" :key="index" class="menu-wrapper">
        <div class="item-image">
          <img src="https://mp-seoul-image-production-s3.mangoplate.com/806040_1615606519483261.jpg" class="image" />
        </div>
        <div class="item-info">
          <div class="item-name">메뉴이름</div>
          <div class="item-intro">메뉴설명</div>
          <div class="item-price">메뉴가격</div>
          <div class="item-unit">판매단위</div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
