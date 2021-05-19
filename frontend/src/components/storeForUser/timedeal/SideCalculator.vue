<template>
  <aside class="calculator-container">
    <div class="select-wrapper">
      <input id="menu-select-button" v-model="isSelected" type="checkbox" class="menu-select-input" />
      <label for="menu-select-button" class="menu-select-button">
        <span>메뉴 선택하기 </span> <awesome icon="caret-down"></awesome
      ></label>
      <ul v-show="isSelected" class="menu-list">
        <li v-for="(menu, index) in menus" :key="index" class="menu-list-item" @click="addMenu(menu)">
          {{ index + 1 }}. {{ menu.name }} (재고: {{ menu.stock }})
        </li>
      </ul>
    </div>
    <div ref="selectedWrapper" class="selected-wrapper">
      <div v-for="(menu, index) in selectedMenus" :key="index" class="selected-box">
        <div class="cancel-button"><awesome icon="times" @click="subMenu(menu)"></awesome></div>
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
    <div class="order-button" @click="getDeal">예약 하기</div>
  </aside>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { makeDeal } from '@/api/order';
import { mapMutations, mapGetters } from 'vuex';
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
    ...mapGetters(['getUserInfo']),
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
    ...mapMutations(['setSpinnerState']),
    oneTrans,
    addMenu(menu) {
      if (menu.selected === true) {
        alert('이미 포함된 메뉴입니다');
        return;
      }
      if (menu.stock === 0) {
        alert('현재 재고가 없습니다');
        return;
      }
      menu.selected = true;
      menu.count = 0;
      this.increase(menu);
      this.isSelected = false;
    },
    subMenu(menu) {
      if (menu.selected === false) return;
      menu.selected = false;
      menu.count = 0;
      menu.payFor = 0;
    },
    async getDeal() {
      try {
        this.setSpinnerState(true);
        const orderList = this.menus.filter(x => x.selected);
        await makeDeal({
          userId: this.getUserInfo.id,
          storeId: this.$route.params.storeId,
          nickname: this.getUserInfo.nickname,
          reservationGroups: orderList.map(x => {
            return {
              productId: x.productId,
              productName: x.name,
              price: x.price,
              count: x.count,
              rate: x.rate,
            };
          }),
        });
        this.setSpinnerState(false);
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('주문하는데 실패했습니다.');
      }
    },
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
