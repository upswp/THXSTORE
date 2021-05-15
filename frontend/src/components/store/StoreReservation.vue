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
        <template v-for="(order, i) in reverseOrders">
          <tr v-for="(product, j) in order.reservationGroups" :key="`${i} ${j}`">
            <td v-if="j === 0" :rowspan="order.reservationGroups.length">{{ order.index }}</td>
            <td v-if="j === 0" :rowspan="order.reservationGroups.length" width="15%">
              {{ dateTrans(order.orderTime) }}
            </td>
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
                  <div class="order-accept status-button" @click="changeState('ACCEPT', order)">수락</div>
                  <div class="order-fail status-button" @click="rejectOrder(order)">거절</div>
                </div>
              </div>
              <div v-else-if="order.reservationStatus === 'ACCEPT'">
                <div class="status-label">접수 완료</div>
                <span class="status-button stand-by-status" @click="changeState('STAND_BY', order)">조리 완료</span>
              </div>
              <div v-else-if="order.reservationStatus === 'REJECT'">
                <div class="status-label">주문 취소</div>
              </div>
              <div v-else-if="order.reservationStatus === 'STAND_BY'">
                <div class="status-label">조리 완료</div>
                <span class="status-button finish-status" @click="changeState('FINISH', order)">수령 완료</span>
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
import { oneTrans, dateTrans } from '@/utils/filters';
import { getTotalOrders, setReservationStatus, cancelOrder } from '@/api/order';
import { mapMutations, mapGetters } from 'vuex';
export default {
  data() {
    return {
      orders: [],
      pusher: '',
    };
  },
  computed: {
    ...mapGetters(['getStoreId']),
    reverseOrders() {
      return [...this.orders].reverse();
    },
  },
  async created() {
    try {
      this.setSpinnerState(true);
      // order 등록
      const { data } = await getTotalOrders(this.getStoreId);
      for (let i = 0; i < data.length; i++) {
        this.computeAfterLoad(data[i], i + 1);
      }
      this.orders = data;

      // pusher 등록
      this.pusher = new Pusher(process.env.VUE_APP_PUSHER_APP_KEY, {
        cluster: process.env.VUE_APP_PUSHER_APP_CLUSTER,
      });
      let channel = this.pusher.subscribe(`${this.getStoreId}-channel`);
      channel.bind('my-event', data => {
        this.computeAfterLoad(data, this.orders.length + 1);
        data['reservationStatus'] = 'DEFAULT';
        this.orders.push(data);
      });
      this.setSpinnerState(false);
    } catch (error) {
      this.setSpinnerState(false);
      console.log(error);
      alert('주문 내역 불러오기를 실패했습니다.');
    }
  },
  beforeDestroy() {
    this.pusher.disconnect();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    oneTrans,
    dateTrans,
    discounting(rate, price) {
      const origin = ((100 - rate) / 100) * price;
      return Math.floor(origin / 100) * 100;
    },
    computeAfterLoad(order, index) {
      let total = 0;
      order.reservationGroups.forEach(p => {
        p['discounted'] = this.discounting(p.rate, p.price);
        p['computed'] = p.discounted * p.count;
        total += p.computed;
      });
      order['index'] = index;
      order['total'] = total;
    },
    async changeState(state, order) {
      try {
        this.setSpinnerState(true);
        await setReservationStatus({
          reservationStatus: state,
          storeId: order.storeId,
          memberId: order.userId,
        });
        order.reservationStatus = state;
        this.setSpinnerState(false);
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('주문 상태 변경에 실패하였습니다');
      }
    },
    async rejectOrder(order) {
      try {
        this.setSpinnerState(true);
        await cancelOrder(order.userId, order.storeId);
        this.setSpinnerState(false);
        order.reservationStatus = 'REJECT';
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('주문 취소에 실패하였습니다');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
