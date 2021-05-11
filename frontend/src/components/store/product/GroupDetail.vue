<template>
  <main class="store-product-detail">
    <div>Group1</div>
    <div class="menu-container"></div>
    <div v-for="(menu, index) in menus" :key="index" class="menu-wrapper">
      <div>메뉴이름</div>
      <div>메뉴사진</div>
      <div>메뉴설명</div>
      <div>메뉴가격</div>
      <div>판매단위</div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      menus: [1, 2, 3, 4, 5, 6, 7, 8],
    };
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
