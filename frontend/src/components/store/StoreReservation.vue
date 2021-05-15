<template>
  <div class="store-reservation-container">
    <header class="reservation-header">타임딜 예약 내역</header>
    <section class="reservation-body">
      <table class="reservation-table">
        <th>번호</th>
        <th>주문시각</th>
        <th>주문자</th>
        <th>메뉴명</th>
        <th>수량</th>
        <th>할인가</th>
        <th>메뉴 별 금액</th>
        <th>총 합</th>
        <th>상태</th>
        <template v-for="(order, i) in orders">
          <tr v-for="(product, j) in order.reservationGroups" :key="j">
            <td v-if="j === 0" :rowspan="order.reservationGroups.length">{{ i + 1 }}</td>
            <td v-if="j === 0" :rowspan="order.reservationGroups.length" width="15%">{{ order.orderTime }}</td>
            <td v-if="j === 0" :rowspan="order.reservationGroups.length">{{ order.nickname }}</td>
            <td class="name">{{ product.productName }}</td>
            <td class="amount">{{ product.count }}</td>
            <td>{{ oneTrans(product.discounted) }}</td>
            <td>{{ oneTrans(product.computed) }}</td>
            <td v-if="j === 0" :rowspan="order.reservationGroups.length" class="computed">
              {{ oneTrans(order.total) }}
            </td>
            <td v-if="j === 0" :rowspan="order.reservationGroups.length">
              <div v-if="order.reservationStatus === 'DEFAULT'" class="default-option">
                <div class="status-label">주문 접수</div>
                <div class="standBy-wrapper">
                  <div class="order-accept status-button">수락</div>
                  <div class="order-fail status-button">거절</div>
                </div>
              </div>
              <div v-else-if="order.reservationStatus === 'ACCEPT'">
                <div class="status-label">접수 완료</div>
                <span class="status-button next-status">조리 완료</span>
              </div>
              <div v-else-if="order.reservationStatus === 'STAND_BY'">
                <div class="status-label">조리 완료</div>
                <span class="status-button next-status">수령 완료</span>
              </div>
              <div v-else>
                <div class="status-label">수령 완료</div>
              </div>
            </td>
          </tr>
        </template>
      </table>
    </section>
  </div>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { getTotalOrders } from '@/api/order';
import { mapMutations, mapGetters } from 'vuex';
export default {
  data() {
    return {
      orders: [],
    };
  },
  computed: {
    ...mapGetters(['getStoreId']),
  },
  async created() {
    // for (const order of this.orders) {
    //   let total = 0;
    //   order.orderList.forEach(p => {
    //     p['discounted'] = this.discounting(p.discountRate, p.price);
    //     p['computed'] = p.discounted * p.count;
    //     total += p.computed;
    //   });
    //   order['total'] = total;
    // }
    try {
      this.setSpinnerState(true);
      const { data } = await getTotalOrders(this.getStoreId);
      for (const order of data) {
        this.computeAfterLoad(order);
      }
      this.orders = data;
      this.setSpinnerState(false);
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    oneTrans,
    discounting(rate, price) {
      const origin = ((100 - rate) / 100) * price;
      return Math.floor(origin / 100) * 100;
    },
    computeAfterLoad(order) {
      let total = 0;
      order.reservationGroups.forEach(p => {
        p['discounted'] = this.discounting(p.rate, p.price);
        p['computed'] = p.discounted * p.count;
        total += p.computed;
      });
      order['total'] = total;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
