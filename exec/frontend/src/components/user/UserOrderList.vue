<template>
  <div v-if="orderListLoaded" class="order-list-container">
    <div class="order-list-title">주문 목록</div>
    <div v-if="loaded" class="order-list-items">
      <div v-for="(orderItem, index) in orderItems" :key="index" class="order-list-item">
        <div class="order-list-header">
          <img class="item-logo" :src="orderItem.storeImg" alt="" />
          <div style="width: 60%; flex-grow: 1; padding-right: 10px">
            <div class="item-label"><label>스토어</label></div>
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
        <div
          v-if="statusCheck(orderItem.reservationStatus)"
          class="order-list-footer"
          @click="toggleReviewLoaded(index)"
        >
          <awesome icon="pencil-alt"></awesome>
          리뷰 쓰기
        </div>
        <form v-if="orderItems[index].reviewLoaded" class="review-form" @submit.prevent="submitForm(index)">
          <div class="startRadio">
            <label v-for="(star, starIndex) in 10" :key="starIndex" class="startRadio__box">
              <input type="radio" name="star" :value="0.5 * star" @input="starScore($event, index)" />
              <span class="startRadio__img"><span class="blind">별 1개</span></span>
            </label>
          </div>

          <textarea
            type="text"
            class="content-input"
            placeholder="정성스러운 후기는 가게 사장님에게 큰 힘이 됩니다."
            autofocus
            @input="reviewContent($event, index)"
          ></textarea>
          <button class="close-button" @click="toggleReviewLoaded(index)">닫기</button>
          <button class="submit-button" type="submit">등록</button>
        </form>
      </div>
    </div>
    <div v-else>주문내역이 없습니다.</div>
  </div>
</template>

<script>
import { getUserOrders, registerReview } from '@/api/userOrder';
import { dateTrans, oneTrans } from '@/utils/filters';
import { mapMutations } from 'vuex';
export default {
  props: {
    orderList: {
      type: Array,
      default: () => [],
      require: true,
    },
    orderListLoaded: {
      type: Boolean,
      default: false,
      require: true,
    },
  },
  data() {
    return {
      orderItems: [],
      loaded: false,
      reviewLoaded: true,
    };
  },
  async created() {
    // await this.getUserOrdersList();
    // this.changeResStatusToKor();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    starScore($event, index) {
      this.orderItems[index].starScore = parseFloat($event.target.value);
    },
    reviewContent($event, index) {
      console.log('reviewContent', $event.target.value);
      this.orderItems[index].reviewContent = $event.target.value;
    },
    dateTrans,
    oneTrans,
    toggleReviewLoaded(index) {
      this.orderItems[index].reviewLoaded = !this.orderItems[index].reviewLoaded;
      this.orderItems[index].reviewContent = '';
      this.orderItems[index].starScore = '';
    },
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
      try {
        this.setSpinnerState(true);
        const { data } = await getUserOrders();
        data.forEach(x => {
          x['totalPayment'] = 0;
          x['reviewLoaded'] = false;
          x['starScore'] = '';
          x['reviewContent'] = '';
          x.reservationGroups.forEach(product => {
            product['computed'] = Math.floor(product.price * (100 - product.rate)) / 100;
            x['totalPayment'] += product.computed;
          });
        });
        this.setSpinnerState(false);
        this.orderItems = data;
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
      }
    },
    async submitForm(index) {
      try {
        const rawData = {
          storeId: this.orderItems[index].storeId,
          memberId: this.orderItems[index].userId,
          reservationId: this.orderItems[index].reservationId,
          star: this.orderItems[index].starScore,
          comment: this.orderItems[index].reviewContent,
          memberName: this.$store.state.userInfo.nickname,
        };
        this.setSpinnerState(true);
        const { data } = await registerReview(rawData);
        this.orderItems[index].reservationStatus = false;
        this.toggleReviewLoaded(index);
        this.setSpinnerState(false);
        if (data.message) {
          alert('이미 리뷰를 작성하셨습니다.');
        } else {
          alert('리뷰가 정상적으로 등록되었습니다.');
        }
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.order-list-container {
  width: 80%;
  max-width: 1080px;
  .order-list-title {
    font-size: 24px;
    text-align: center;
    font-weight: 600;
    margin-bottom: 20px;
    @include mobile() {
      font-size: 18px;
    }
    @include xs-mobile() {
      font-size: 16px;
    }
  }
  .order-list-items {
    @include flexbox;
    background-color: white;

    border: 1px $gray200 solid;
    padding: 1%;
    justify-content: space-between;
    flex-wrap: wrap;
    // flex-direction: column;
    .order-list-header {
      padding: 1%;
      @include flexbox;
      @include align-items(center);
      flex-wrap: wrap;
      margin-bottom: 10px;
      // border: $gray600 2px solid;
      border-radius: 10px;
      .item-label {
        width: 60%;
        color: white;
        padding: 0.7%;
        margin-bottom: 5px;
        label {
          border-radius: 30px;
          padding: 1px 5%;
          background-color: $gray400;
          @include mobile {
            font-size: 12px;
          }
          @include pc {
            font-size: 12px;
          }
          @include xs-mobile {
            font-size: 0.7em;
          }
        }
      }
      .item-store {
        @include lg-pc {
          font-size: 1.4em;
        }
        @include mobile {
          font-size: 1.2em;
        }
      }
      .item-date {
        width: 100%;
        text-align: end;
      }
      .item-status {
        color: $red600;
        width: 100%;
        padding-top: 1%;
        text-align: end;
        @include xs-mobile {
          font-size: 0.8em;
        }
      }
      .item-logo {
        $length: clamp(20px, 30%, 120px);
        border-radius: 10%;
        width: $length;
        height: $length;
        object-fit: cover;
        object-position: center 50%;
        margin-right: 10px;
        cursor: pointer;
        @include mobile {
          $length: clamp(20px, 20%, 120px);
          width: $length;
          height: $length;
        }
      }
    }

    .order-list-item {
      @include shadow1;
      padding: 1%;
      border-radius: 0px 0px 40px 0px;
      border: 2px black solid;
      margin-bottom: 15px;
      width: 49%;
      position: relative;
      @include mobile {
        width: 100%;
      }
      @include xs-mobile {
        width: 100%;
      }
      .order-menu-container {
        margin: 0 4%;
        height: 70px;
        overflow-y: auto;
      }
      .order-list-body {
        @include flexbox;
        padding: 1%;
        .item-name {
          text-align: start;
          width: 70%;
          font-size: 0.9em;
        }
        .item-price {
          width: 30%;
          // padding: 2%;
          font-size: 0.9em;
          text-align: end;
        }
      }
      .order-list-accum-price {
        border-top: $gray400 1px solid;
        margin: 0 4% 35px 4%;
        padding: 2%;
        // border: 2px solid $blue400;
        @include flexbox;
        margin-top: 10px;
        .accum-price-label {
          width: 70%;
        }
        .accum-price {
          width: 30%;
          text-align: end;
        }
      }

      .order-list-footer {
        width: 100%;
        text-align: center;
        padding: 0px 10px 15px 10px;
        // position: absolute;
        bottom: 0px;

        &:hover {
          cursor: pointer;
        }
      }
      .review-form {
        text-align: center;
        .content-input {
          width: 95%;
          height: 100px;
          margin-bottom: 2%;
          resize: none;
          font-size: 0.8em;
          border: black 2px solid;
        }
        .close-button {
          background-color: $red400;
          border: none;
          width: 30%;
          margin-right: 15%;
          color: white;
        }
        .submit-button {
          background-color: $green400;
          border: none;
          color: white;
          width: 30%;
        }

        EDIT ON .blind {
          position: absolute;
          overflow: hidden;
          margin: -1px;
          padding: 0;
          width: 1px;
          height: 1px;
          border: none;
          clip: rect(0, 0, 0, 0);
        }

        .startRadio {
          display: inline-block;
          overflow: hidden;
          height: 40px;
          margin-bottom: 2%;
          &:after {
            content: '';
            display: block;
            position: relative;
            z-index: 10;
            height: 40px;
            background: url('data:image/png;base64,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')
              repeat-x 0 0;
            background-size: contain;
            pointer-events: none;
          }
          &__box {
            position: relative;
            z-index: 1;
            float: left;
            width: 20px;
            height: 40px;
            cursor: pointer;
            input {
              opacity: 0 !important;
              height: 0 !important;
              width: 0 !important;
              position: absolute !important;

              &:checked + .startRadio__img {
                background-color: #0084ff;
              }
            }
          }
          &__img {
            display: block;
            position: absolute;
            right: 0;
            width: 500px;
            height: 40px;
            pointer-events: none;
          }
        }
      }
    }
  }
}
</style>
