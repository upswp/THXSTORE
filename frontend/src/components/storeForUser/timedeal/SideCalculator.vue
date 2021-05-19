<template>
  <aside class="calculator-container">
    <div class="select-wrapper">
      <input id="menu-select-button" v-model="isSelected" type="checkbox" class="menu-select-input" />
      <label for="menu-select-button" class="menu-select-button">
        <span>메뉴 선택하기 </span> <awesome icon="caret-down"></awesome
      ></label>
      <ul v-show="isSelected" class="menu-list">
        <li v-for="(menu, index) in menus" :key="index" class="menu-list-item">
          {{ index + 1 }}. {{ menu.name }} (재고: {{ menu.stock }})
        </li>
      </ul>
    </div>
    <div class="selected-wrapper">
      <div v-for="(menu, index) in selectedMenus" :key="index" class="selected-box">
        <div class="cancel-button"><awesome icon="times"></awesome></div>
        <div class="menu-name">{{ menu.name }}</div>
        <div class="menu-count-wrapper">
          <span @click="decrease(menu)">-</span>
          <input v-model="menu.count" type="number" class="menu-count" />
          <span @click="increase(menu)">+</span>
        </div>
        <div class="menu-payFor">{{ oneTrans(menu.payFor) }}원</div>
      </div>
    </div>
    <div class="total-pay-for-wrapper">
      <span class="total-count">총 {{ totalCount }}개</span>
      <span class="total-pay-for">{{ oneTrans(totalPayFor) }}원</span>
    </div>
    <div class="order-button">예약 하기</div>
  </aside>
</template>

<script>
import { oneTrans } from '@/utils/filters';
export default {
  props: {
    menus: {
      type: Array,
      default: () => {},
    },
  },
  data() {
    return {
      isSelected: false,
    };
  },
  computed: {
    selectedMenus() {
      return this.menus.filter(x => x.selected);
    },
    totalPayFor() {
      return this.selectedMenus.reduce((acc, item) => {
        return acc + item.payFor;
      }, 0);
    },
    totalCount() {
      return this.selectedMenus.reduce((acc, item) => {
        return acc + item.count;
      }, 0);
    },
  },
  methods: {
    oneTrans,
    decrease(menu) {
      if (menu.count > 1) menu.count--;
      menu.payFor = menu.count * menu.discounted;
      menu.payFor = menu.count * menu.discounted;
    },
    increase(menu) {
      if (menu.count < menu.stock) {
        menu.count++;
        menu.payFor = menu.count * menu.discounted;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
