<template>
  <div v-if="orderListLoaded" class="order-list-container">
    <header class="page-title">주문 목록</header>
    <section v-if="orderList.length > 0" class="order-items">
      <!-- 주문 내역 카드 -->
      <article v-for="(order, index) in orderList" :key="index" class="order-item">
        <!-- 가게 정보 및 주문 상태 -->
        <header class="order-header">
          <figure class="store-logo">
            <img :src="order.storeImg" />
          </figure>
          <main class="store-info">
            <div class="store-name">{{ order.storeName }}</div>
            <div class="order-date">{{ dateTrans(order.orderTime) }}</div>
            <div class="order-status">{{ orderStatusStrConvert[order.reservationStatus] }}</div>
          </main>
          <div v-if="statusCheck(order)" class="review-button" @click="toggleReviewWritten(index)">
            <awesome icon="edit"></awesome> 리뷰
          </div>
        </header>
        <!-- 주문 상세 내역 -->
        <div class="order-menu">
          <div v-for="(menu, menuOrder) in order.reservationGroups" :key="menuOrder" class="order-body">
            <span class="menu-name">{{ menu.productName }} </span>
            <span class="menu-count"> {{ menu.count }}개</span>
            <div class="menu-price">{{ wonTrans(menu.computed) }}원</div>
          </div>
        </div>
        <div class="total-price-container">
          <div class="total-price-label">총 결제금액</div>
          <div class="total-price">{{ wonTrans(order.totalPayment) }}원</div>
        </div>

        <!-- <form v-if="order.reviewLoaded" class="review-form" @submit.prevent="submitForm(index)">
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
        </form> -->

        <!-- v-if로 모달창을 만들어서 리뷰를 쓸 수 있는 공간을 제공해줄 것 -->
      </article>
    </section>
    <section v-else>주문내역이 없습니다.</section>
  </div>
</template>

<script>
import { getUserOrders, registerReview } from '@/api/userOrder';
import { dateTrans, wonTrans, orderStatusStrConvert } from '@/utils/filters';
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
      orderStatusStrConvert,
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
    wonTrans,
    toggleReviewWritten(index) {
      this.orderItems[index].reviewWritten = !this.orderItems[index].reviewWritten;
      this.orderItems[index].reviewContent = '';
      this.orderItems[index].starScore = '';
    },
    getdiscountPrice(itemMenu, index) {
      const discountedPrice = Math.floor(itemMenu.price * (100 - itemMenu.rate)) / 100;
      console.log('discountedPrice', discountedPrice);
      this.orderItems[index].computed += discountedPrice;
      return discountedPrice;
    },
    statusCheck(order) {
      if (order.reservationStatus === 'FINISH') return true;
      return false;
    },
    async getUserOrdersList() {
      try {
        this.setSpinnerState(true);
        const { data } = await getUserOrders();
        data.forEach(x => {
          x['totalPayment'] = 0;
          x['reviewWritten'] = false;
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
  width: 100%;
  padding: 0 1%;
}
.page-title {
  @include xl-font;
  text-align: center;
  font-weight: bolder;
  margin-bottom: 20px;
}
.order-items {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, auto));
  gap: 10px;
  padding: 1%;
  background-color: white;
  border: 1px solid $gray200;
}
.order-item {
  position: relative;
  padding: 1%;
  margin-bottom: 15px;
  border: 2px $gray600 solid;
  border-radius: 0px 0px 40px 0px;
  @include shadow1;
}
.order-header {
  position: relative;
  @include flexbox;
  @include align-items(center);
  padding: 1%;
  margin-bottom: 10px;
  border-radius: 10px;
}
.store-logo {
  $length: clamp(80px, 5vw, 100px);
  border-radius: 10%;
  width: $length;
  height: $length;
  cursor: pointer;
  margin: 0;
  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    border: 1px solid $gray300;
    object-fit: cover;
    object-position: center center;
  }
}
.store-info {
  flex-grow: 1;
  height: clamp(80px, 5vw, 100px);
  padding: 20px 10px;
}
.store-name {
  @include lg-font;
  font-weight: bold;
  margin-bottom: 3px;
}
.order-date {
  width: 100%;
  @include xs-font;
}

.order-status {
  color: $red600;
  width: 100%;
  text-align: end;
}
.review-button {
  position: absolute;
  top: 5px;
  right: 10px;
  color: $gray600;
  @include sm-font;
  @include transition(color 0.3s);
  &:hover {
    cursor: pointer;
    color: $purple800;
  }
}
.order-menu {
  margin: 0 4%;
  height: 100px;
  overflow-y: auto;
}
.order-body {
  @include flexbox;
  @include align-items(baseline);
  margin-bottom: 3px;
}
.menu-name {
  width: 145px;
  flex-grow: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  // @include flexbox;
  @include align-items(center);
  &::before {
    display: inline-block;
    content: '';
    width: 8px;
    height: 8px;
    border-radius: 50%;
    margin-right: 5px;
    background-color: $purple400;
  }
}
.menu-count {
  width: 40px;
  margin-left: 5px;
  color: $gray800;
}
.menu-price {
  width: 95px;
  flex-grow: 1;
  text-align: end;
}

.total-price-container {
  border-top: $gray400 1px solid;
  margin: 0 4% 35px 4%;
  padding: 2%;
  // border: 2px solid $blue400;
  @include flexbox;
  margin-top: 10px;
  font-weight: bold;
}
.total-price-label {
  width: 70%;
}
.total-price {
  width: 30%;
  text-align: end;
}

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

.review-form {
  text-align: center;

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
</style>
