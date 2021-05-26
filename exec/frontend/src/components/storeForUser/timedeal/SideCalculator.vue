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
          <input v-model="menu.count" type="number" class="menu-count" @input="checkValid(menu)" />
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
    <order-modal
      v-if="confirm"
      :error="error"
      :message="confirmBody"
      :reservation-id="reservationId"
      @confirmStatus="confirmStatus"
    ></order-modal>
  </aside>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { makeDeal } from '@/api/order';
import { mapMutations, mapGetters } from 'vuex';
import OrderModal from '@/components/storeForUser/timedeal/OrderModal';
export default {
  components: {
    OrderModal,
  },
  props: {
    menus: {
      type: Array,
      default: () => {},
    },
  },
  data() {
    return {
      isSelected: false,
      confirm: false,
      error: false,
      confirmBody: '',
      reservationId: '',
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
    selectedMenus() {
      return this.menus.filter(x => x.selected);
    },
    totalPayFor() {
      return this.selectedMenus.reduce((acc, item) => {
        return acc + parseInt(item.payFor);
      }, 0);
    },
    totalCount() {
      return this.selectedMenus.reduce((acc, item) => {
        return acc + parseInt(item.count);
      }, 0);
    },
  },
  methods: {
    checkValid(menu) {
      if (isNaN(menu.count)) menu.count = 0;
      else if (parseInt(menu.count) < 0) menu.count = 0;
      else if (menu.count > menu.stock) menu.count = menu.stock;
      console.log(menu.count);
      menu.payFor = menu.count * menu.discounted;
    },
    confirmStatus() {
      this.error = false;
      this.confirm = false;
      this.confirmBody = '';
      this.reservationId = '';
      this.$router.go(0);
    },
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
        const { data } = await makeDeal({
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
        this.reservationId = data;
        this.setSpinnerState(false);
        this.confirmBody = orderList;
        this.confirm = true;
      } catch (error) {
        console.log(error.response.status);
        this.setSpinnerState(false);
        if (error.response.status === 400) {
          this.error = true;
          this.confirmBody = error.response.data;
          this.confirm = true;
        } else {
          alert('주문하는데 실패했습니다.');
        }
      }
    },
    decrease(menu) {
      if (menu.count > 1) menu.count--;
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
.calculator-container {
  height: fit-content;
  border: 1px solid $gray400;
  padding: 10px;
  @include pc {
    font-size: 16px;
  }
  @include mobile {
    font-size: 14px;
    position: fixed;
    right: 0;
    bottom: 0px;
    background-color: white;
    width: calc(100% - 20px);
    left: 0;
    margin: auto;
    z-index: 2;
  }
  @include xs-mobile {
    font-size: 12px;
    position: fixed;
    bottom: 0px;
    background-color: white;
    width: 100%;
    width: calc(100% - 20px);
    left: 0;
    right: 0;
    margin: auto;
    z-index: 2;
  }
}
.menu-select-button {
  cursor: pointer;
  width: 100%;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
  @include flexbox;
  @include justify-content(space-between);
  padding: 5px 10px;
  border: 1px solid $gray400;
  svg {
    transition: all 0.3s;
  }
}
.menu-select-input {
  display: none;
  &:checked + .menu-select-button {
    border: 1px solid $blue400;
    svg {
      transform: rotate(180deg);
    }
  }
}
.menu-list {
  position: absolute;
  width: 100%;
  border: 1px solid $blue400;
  border-top: none;
  padding: 5px;
  background-color: white;
  z-index: 5;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
}
.menu-list-item {
  padding: 5px 5px;
  &:hover {
    background-color: $gray400;
    cursor: pointer;
  }
}
.select-wrapper {
  position: relative;
}
.selected-wrapper {
  width: 100%;
  padding: 5px 0;
  height: 300px;
  overflow: auto;
  margin-bottom: 20px;
  @include mobile {
    height: 150px;
  }
  @include xs-mobile {
    height: 150px;
  }
}
.selected-box {
  width: calc(100% - 5px);
  border: 1px solid $gray200;
  padding: 10px;
  position: relative;
  margin-bottom: 5px;
}
.cancel-button {
  color: $gray600;
  text-align: right;
  cursor: pointer;
  svg {
    transition: all 0.6s;
    &:hover {
      transform: rotate(180deg);
    }
  }
}
.menu-count {
  width: 50px;
  padding: 5px;
  text-align: center;
  border: 1px solid $gray600;
  border-right: 0px;
  border-left: 0px;
}
.menu-count-wrapper {
  @include flexbox;
  span {
    border: 1px solid $gray600;
    width: 30px;
    @include flexbox;
    @include justify-content(center);
    @include align-items(center);
    cursor: pointer;
  }
  font-size: 14px;
  margin-bottom: 10px;
}

input[type='number']::-webkit-outer-spin-button,
input[type='number']::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.menu-name {
  margin-bottom: 5px;
}
.menu-payFor {
  text-align: right;
}
.total-pay-for-wrapper {
  @include flexbox;
  @include justify-content(space-between);
  @include align-items(baseline);
}
.total-pay-for {
  color: #f54d53;
  font-size: 20px;
  font-weight: bold;
}
.order-button {
  margin: 10px 0;
  padding: 10px;
  font-size: 20px;
  font-weight: bold;
  background-image: linear-gradient(0.25turn, #fc5243, #fc3c56);
  border-radius: 5px;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  color: white;
  cursor: pointer;
  &:hover {
    background-image: linear-gradient(0.25turn, #fa4f40, #ff203e);
  }
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 14px;
  }
}
</style>
