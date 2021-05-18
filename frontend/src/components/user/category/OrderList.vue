<template>
  <div v-if="loaded" class="order-list-container">
    <div class="order-list-title">{{ orderItems[0].nickname }}님의 예약 목록</div>
    <div class="order-list-items">
      <div v-for="(orderItem, index) in orderItems" :key="index" class="order-list-item">
        <div class="order-list-header">
          <img class="item-logo" :src="orderItem.storeImg" alt="" />
          <div style="width: 65%">
            <div class="item-store">
              <b>{{ orderItem.storeName }}</b>
            </div>
            <div class="item-date">
              <small>{{ dateTrans(orderItem.orderTime) }}</small>
            </div>
            <div class="item-status">{{ orderItem.reservationStatus }}</div>
          </div>
        </div>
        <div class="order-menu-container">
          <div v-for="(itemMenu, menuOrder) in orderItem.reservationGroups" :key="menuOrder" class="order-list-body">
            <div class="item-name">
              {{ itemMenu.productName }} <small>{{ itemMenu.count }}개</small>
            </div>
            <div class="item-price">{{ oneTrans(itemMenu.computed) }}</div>
          </div>
        </div>
        <div class="order-list-accum-price">
          <div class="accum-price-label"><b>총 결제금액</b></div>
          <div class="accum-price">
            <b>{{ oneTrans(orderItem.totalPayment) }}</b>
          </div>
        </div>
        <div v-if="statusCheck(orderItem.reservationStatus)" class="order-list-footer" @click="modal">
          <awesome icon="pencil-alt"></awesome>
          리뷰 쓰기
          <div v-if="reviewLoaded" class="review-form">
            <div class="star-input">0 0 0 0 0</div>
            <input type="textarea" class="content-input" />
            <button>취소</button>
            <button>등록</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserOrders } from '@/api/userOrder';
import { dateTrans, oneTrans } from '@/utils/filters';
export default {
  data() {
    return {
      orderItems: [],
      loaded: false,
      reviewLoaded: true,
    };
  },
  async created() {
    await this.getUserOrdersList();
    this.changeResStatusToKor();
  },
  methods: {
    dateTrans,
    oneTrans,
    getdiscountPrice(itemMenu, index) {
      const discountedPrice = Math.floor(itemMenu.price * (100 - itemMenu.rate)) / 100;
      console.log('discountedPrice', discountedPrice);
      this.orderItems[index].computed += discountedPrice;
      return discountedPrice;
    },
    changeResStatusToKor() {
      for (let i = 0; i < this.orderItems.length; i++) {
        this.orderItems[i].reservationStatus = this.orderItems[i].reservationStatus
          .replace('DEFAULT', '주문 대기')
          .replace('ACCEPT', '주문 접수')
          .replace('STANDBY_BY', '수령 대기')
          .replace('FINISH', '수령 완료');
      }
      this.loaded = true;
    },
    statusCheck(state) {
      if (state === '수령 완료') return true;
      return false;
    },
    async getUserOrdersList() {
      const { data } = await getUserOrders();
      data.forEach(x => {
        x['totalPayment'] = 0;
        x.reservationGroups.forEach(product => {
          product['computed'] = Math.floor(product.price * (100 - product.rate)) / 100;
          x['totalPayment'] += product.computed;
        });
      });
      this.orderItems = data;
      console.log('this.orderItems', this.orderItems);
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample2.scss';
</style>
