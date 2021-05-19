<template>
  <div class="modal-container">
    <div class="modal-body">
      <div v-if="error" class="order-fail">
        <div class="fail-header">예약 실패</div>
        <div>
          <b>{{ message.join(', ') }}</b
          >의 재고가 부족해서 주문이 실패했습니다.
        </div>
        <div>페이지를 다시 로딩합니다.</div>
      </div>
      <div v-else class="order-success">
        <div class="success-header">예약 성공</div>
        <table class="order-table">
          <th>메뉴명</th>
          <th>할인된 가격</th>
          <th>수량</th>
          <th>금액</th>
          <tr v-for="(menu, index) in message" :key="index">
            <td>{{ menu.name }}</td>
            <td>{{ oneTrans(menu.discounted) }}원</td>
            <td>{{ menu.count }}</td>
            <td>{{ oneTrans(menu.payFor) }}원</td>
          </tr>
        </table>
        <div class="total-pay-label">총 금액</div>
        <div class="total-pay">{{ oneTrans(totalPayFor()) }}원</div>
      </div>
      <div class="confirm-button" :class="{ success: !error, fail: error }" @click="$emit('confirmStatus')">확인</div>
    </div>
  </div>
</template>

<script>
import { oneTrans } from '@/utils/filters';
export default {
  props: {
    error: {
      type: Boolean,
      default: false,
      require: true,
    },
    message: {
      type: Array,
      default: () => [],
      require: true,
    },
  },
  methods: {
    oneTrans,
    totalPayFor() {
      return this.message.reduce((acc, item) => acc + item.payFor, 0);
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
