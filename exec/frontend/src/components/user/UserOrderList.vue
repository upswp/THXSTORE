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

        <form v-if="order.reviewLoaded" class="review-form" @submit.prevent="submitForm(index)">
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
@import '@/assets/scss/sample';
</style>
