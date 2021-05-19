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
      <div class="button-wrapper">
        <div v-if="!error" class="cancel-button" @click="cancelOrder">주문 취소</div>
        <div class="confirm-button" :class="{ success: !error, fail: error }" @click="$emit('confirmStatus')">확인</div>
      </div>
    </div>
  </div>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { cancelOrderForUser } from '@/api/order';
import { mapMutations } from 'vuex';
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
    ...mapMutations(['setSpinnerState']),
    totalPayFor() {
      return this.message.reduce((acc, item) => acc + item.payFor, 0);
    },
    async cancelOrder() {
      try {
        this.setSpinnerState(true);
        await cancelOrderForUser(this.$route.params.storeId);
      } catch (error) {
        console.log(error);
        if (error.response.status === 400) {
          alert('이미 주문이 접수 되었습니다.');
        } else {
          alert('주문 취소가 불가능한 상태입니다');
        }
      } finally {
        this.setSpinnerState(false);
        this.$emit('confirmStatus');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.button-wrapper {
  @include flexbox;
  @include justify-content(space-between);
}
.cancel-button {
  padding: 10px;
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  text-align: center;
  cursor: pointer;
  color: white;
  border-radius: 5px;
  background-color: rgb(195, 55, 55);
  &:hover {
    background-color: rgb(195, 17, 17);
  }
  flex-basis: 45%;
}
.modal-container {
  position: fixed;
  z-index: 12;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);

  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
}
.modal-body {
  background-color: white;
  border-radius: 5px;
  @include lg-pc {
    width: 600px;
    padding: 50px;

    font-size: 16px;
  }
  @include pc {
    width: 500px;
    padding: 50px;
    font-size: 15px;
  }
  @include mobile {
    width: 400px;
    padding: 40px;
    font-size: 14px;
  }
  @include xs-mobile {
    padding: 30px;
    width: 300px;
    font-size: 13px;
  }
}
.fail-header {
  font-weight: bold;
  color: rgb(200, 28, 28);
  margin-bottom: 10px;
  @include lg-pc {
    font-size: 20px;
  }
  @include pc {
    font-size: 19px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 17px;
  }
}
.order-fail,
.order-success {
  margin-bottom: 40px;
}
.confirm-button {
  padding: 10px;
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  text-align: center;
  cursor: pointer;
  color: white;
  border-radius: 5px;
  &.fail {
    background-color: rgb(195, 55, 55);
    &:hover {
      background-color: rgb(195, 17, 17);
    }
  }
  &.success {
    flex-basis: 45%;
    background-color: rgb(89, 201, 107);
    &:hover {
      background-color: rgb(37, 184, 73);
    }
  }
}
.success-header {
  font-weight: bold;
  color: rgb(10, 131, 80);
  margin-bottom: 20px;
  @include lg-pc {
    font-size: 20px;
  }
  @include pc {
    font-size: 19px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 17px;
  }
}
.order-table {
  border: 1px solid $gray400;
  border-left: none;
  border-right: none;
  padding: 10px;
  width: 100%;
  th {
    text-align: right;
    padding: 5px 0;
  }
  td {
    text-align: right;
  }
  margin-bottom: 20px;
}
.total-pay-label {
  @include lg-pc {
    font-size: 14px;
  }
  @include pc {
    font-size: 13px;
  }
  @include mobile {
    font-size: 12px;
  }
  @include xs-mobile {
    font-size: 11px;
  }
  text-align: right;
  margin-bottom: 5px;
  padding-right: 10px;
}
.total-pay {
  @include lg-pc {
    font-size: 20px;
  }
  @include pc {
    font-size: 19px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 17px;
  }
  color: rgb(221, 16, 16);
  font-weight: bold;
  text-align: right;
  padding-right: 10px;
}
</style>
