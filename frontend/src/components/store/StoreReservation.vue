<template>
  <div v-if="loaded" class="store-reservation-container">
    <header class="reservation-header">타임딜 예약 내역</header>
    <nav class="reservation-countdown">
      <span class="time-deal-counter">{{ countdown }}</span>
    </nav>
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
    <!-- <div class="temp"><awesome icon="store" class="store"></awesome>Thx!Store</div> -->
  </div>
</template>

<script>
import { oneTrans, dateTrans } from '@/utils/filters';
import { getTimeDeal } from '@/api/timeDeal';
import { getTotalOrders, setReservationStatus, cancelOrder } from '@/api/order';
import { mapMutations, mapGetters } from 'vuex';
import { timeStrConvert } from '@/utils/filters';
import { endTime, countDownTimer } from '@/utils/time';
export default {
  data() {
    return {
      orders: [],
      pusher: '',
      timer: '',
      timerDone: false,
      countdown: '',
      loaded: false,
    };
  },
  computed: {
    ...mapGetters(['getStoreId']),
    reverseOrders() {
      return [...this.orders].reverse();
    },
  },
  watch: {
    timerDone(newValue) {
      if (newValue) this.countdown = '해당 이벤트가 종료 되었습니다!';
    },
  },

  beforeDestroy() {
    clearInterval(this.timer);
  },
  async created() {
    try {
      this.setSpinnerState(true);

      // 타임딜 상태와 시작시간을 확인하여 타이머를 작동시킨다.
      this.counterOn();

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
      let audio = new Audio(require('@/assets/audio/AddOrder.mp3'));
      channel.bind('my-event', data => {
        this.computeAfterLoad(data, this.orders.length + 1);
        data['reservationStatus'] = 'DEFAULT';
        this.orders.push(data);
        audio.play();
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
          reservationId: order.reservationId,
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
        await cancelOrder(order.reservationId);
        this.setSpinnerState(false);
        order.reservationStatus = 'REJECT';
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('주문 취소에 실패하였습니다');
      }
    },
    counterOn() {
      getTimeDeal(this.getStoreId)
        .then(response => {
          const data = response.data;
          // 1. 타임딜의 상태를 파악하여 오직 PROGRESS일 때만 타이머를 작동시킨다.
          if (data.status === 'PROGRESS') {
            const [startHour, startMinute] = data.startTime.split(':');
            const start = new Date();
            start.setHours(startHour);
            start.setMinutes(startMinute);
            this.timer = countDownTimer(endTime(start), this);
          } else if (data.status === 'COMPLETE') {
            this.countdown = '타임딜 종료';
          } else if (data.status === 'RESERVATION') {
            this.countdown = '타임딜 시작 대기 중';
          }
          this.loaded = true;
        })
        .catch(error => {
          alert('타임딜을 먼저 등록해주세요');
          this.$router.push({ name: 'storeTimeDeal' });
        });
    },
  },
};
</script>

<style lang="scss" scoped>
// .temp {
//   height: 500px;
//   font-size: 240px;
//   font-family: 'Pacifico', cursive;
//   @include flexbox;
//   @include justify-content(center);
//   color: $blue800;
//   svg {
//     margin-right: 5px;
//   }
// }
.store-reservation-container {
  width: 100%;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 14px;
  }
  @include mobile {
    font-size: 12px;
  }
  @include xs-mobile {
    font-size: 10px;
  }
}
.reservation-header {
  text-align: center;
  font-weight: bold;
  margin-bottom: 20px;
  @include lg-pc {
    font-size: 22px;
  }
  @include pc {
    font-size: 20px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
}
.reservation-body {
  @include flexbox;
  @include justify-content(center);
  min-height: 100%;
}
.reservation-table {
  width: 100%;
  border: 1px solid $gray400;
  th {
    background-color: $green200;
    color: rgb(5, 6, 68);
    padding: 10px 0;
  }
  td {
    text-align: center;
    border: 1px solid $gray400;
    padding: 5px 0;
    &.name {
      background-color: $gray100;
      color: rgba(255, 4, 4, 0.767);
      font-weight: bold;
    }
    &.computed {
      background-color: $gray100;
      color: rgba(255, 4, 4, 0.767);
      font-weight: bold;
    }
    &.amount {
      background-color: $gray100;
      color: rgba(9, 5, 68, 0.767);
      font-weight: bold;
    }
  }
}
.standBy-wrapper {
  @include lg-pc {
    @include flexbox;
    @include justify-content(space-around);
  }
  @include lg-pc {
    @include flexbox;
    @include justify-content(space-around);
  }
  @include mobile {
    @include flexbox;
    @include justify-content(space-around);
  }
}
.status-button {
  display: inline-block;
  width: 30%;
  text-align: center;
  padding: 10px 5px;
  border-radius: 5px;
  margin-right: 5px;
  @include xs-mobile {
    display: block;
    width: 100%;
    padding: 5px 2px;
    margin-bottom: 3px;
    margin-right: 0px;
  }
}
.order-accept {
  background-color: $blue400;
  color: white;
  cursor: pointer;
  &:hover {
    background-color: $blue600;
  }
  margin-right: 5px;
}
.order-fail {
  background-color: $red400;
  color: white;
  cursor: pointer;
  &:hover {
    background-color: $red600;
  }
}
.status-label {
  font-weight: bold;
  margin-bottom: 5px;
}
.stand-by-status {
  cursor: pointer;
  width: auto;
  margin-right: 0px;
  @include xs-mobile {
    display: block;
    width: 100%;
    padding: 5px 2px;
  }
  background-color: rgba(20, 187, 20, 0.609);
  color: white;
  &:hover {
    background-color: rgba(20, 187, 20, 0.87);
  }
}
.finish-status {
  cursor: pointer;
  width: auto;
  margin-right: 0px;
  @include xs-mobile {
    display: block;
    width: 100%;
    padding: 5px 2px;
  }
  background-color: rgba(82, 107, 134, 0.87);
  color: white;
  &:hover {
    background-color: rgba(61, 145, 224, 0.986);
  }
}
@keyframes pumping {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(1.1);
  }
}
.reservation-countdown {
  text-align: right;
  margin-bottom: 10px;
  padding-right: 5px;
}
.time-deal-counter {
  display: inline-block;
  animation: pumping 0.5s infinite alternate;
  color: red;
  font-weight: bold;

  @include lg-pc {
    font-size: 20px;
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
