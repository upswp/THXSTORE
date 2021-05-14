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
          <tr v-for="(product, j) in order.orderList" :key="j">
            <td v-if="j === 0" :rowspan="order.orderList.length">{{ i + 1 }}</td>
            <td v-if="j === 0" :rowspan="order.orderList.length" width="15%">{{ order.orderTime }}</td>
            <td v-if="j === 0" :rowspan="order.orderList.length">{{ order.nickname }}</td>
            <td class="name">{{ product.productName }}</td>
            <td class="amount">{{ product.count }}</td>
            <td>{{ oneTrans(product.discounted) }}</td>
            <td>{{ oneTrans(product.computed) }}</td>
            <td v-if="j === 0" :rowspan="order.orderList.length" class="computed">{{ oneTrans(order.total) }}</td>
            <td v-if="j === 0" :rowspan="order.orderList.length">
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
export default {
  data() {
    return {
      orders: [
        {
          userId: 1,
          nickname: '대현',
          orderList: [
            {
              productName: '불닭',
              price: '18000',
              discountRate: '30',
              count: '2',
            },
            {
              productName: '된장찌개',
              price: '6000',
              discountRate: '20',
              count: '4',
            },
          ],
          reservationStatus: 'DEFAULT',
          orderTime: '2021년 5월 13일 목요일 오후 9:31:12',
        },
        {
          userId: 2,
          nickname: '동식',
          orderList: [
            {
              productName: '닭도리탕',
              price: '24000',
              discountRate: '20',
              count: '1',
            },
            {
              productName: '공깃밥',
              price: '1000',
              discountRate: '10',
              count: '3',
            },
            {
              productName: '맥주',
              price: '4000',
              discountRate: '10',
              count: '5',
            },
          ],
          reservationStatus: 'ACCEPT',
          orderTime: '2021년 5월 13일 목요일 오후 9:23:12',
        },
        {
          userId: 3,
          nickname: '정욱',
          orderList: [
            {
              productName: '국밥',
              price: '6000',
              discountRate: '15',
              count: '2',
            },
            {
              productName: '맛보기 순대',
              price: '5000',
              discountRate: '20',
              count: '1',
            },
          ],
          reservationStatus: 'STAND_BY',
          orderTime: '2021년 5월 13일 목요일 오후 9:01:12',
        },
        {
          userId: 4,
          nickname: '영준',
          orderList: [
            {
              productName: '안동찜닭',
              price: '18000',
              discountRate: '40',
              count: '1',
            },
            {
              productName: '소주',
              price: '3000',
              discountRate: '10',
              count: '6',
            },
          ],
          reservationStatus: 'FINISH',
          orderTime: '2021년 5월 13일 목요일 오후 8:41:12',
        },
      ],
    };
  },
  created() {
    for (const order of this.orders) {
      let total = 0;
      order.orderList.forEach(p => {
        p['discounted'] = this.discounting(p.discountRate, p.price);
        p['computed'] = p.discounted * p.count;
        total += p.computed;
      });
      order['total'] = total;
    }
  },
  methods: {
    oneTrans,
    discounting(rate, price) {
      const origin = ((100 - rate) / 100) * price;
      return Math.floor(origin / 100) * 100;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
