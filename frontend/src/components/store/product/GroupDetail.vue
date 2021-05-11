<template>
  <main class="store-product-detail">
    <div class="group-name-wrapper">
      <div class="group-name">Group1</div>
      <div class="menu-add-button" @click="editOn(false)">새 메뉴 추가하기</div>
    </div>
    <div class="menu-container">
      <div v-for="(menu, index) in menus" :key="index" class="menu-wrapper">
        <div class="item-image">
          <img src="https://mp-seoul-image-production-s3.mangoplate.com/806040_1615606519483261.jpg" class="image" />
        </div>
        <div class="item-info">
          <div class="info-header">
            <div class="setting-button" @click="toggleEditButton(menu)">
              <span></span>
              <span></span>
              <span></span>
            </div>
            <div v-show="menu.editting" class="setting-wrapper">
              <div class="menu-update" @click="editOn(menu)">수정하기</div>
              <div class="menu-delete">삭제하기</div>
            </div>
            <div class="item-name">스테이크 크림파스타</div>
            <div class="item-intro">꾸덕하면서 매콤한 크림파스타에 살살 녹는 소고기 등심 스테이크</div>
          </div>
          <div class="info-footer">
            <div class="item-price">가격: 15,000</div>
            <div class="item-unit">판매단위: 1인분</div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      menus: Array.from({ length: 14 }, () =>
        Object.assign(
          {},
          {
            editting: false,
          },
        ),
      ),
    };
  },
  methods: {
    editOn(menu) {
      if (menu) this.$emit('toggleEdit', menu);
      else this.$emit('toggleEdit', false);
    },
    toggleEditButton(menu) {
      menu.editting = !menu.editting;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
