<template>
  <div class="user-container">
    <div class="router-view-wrapper">
      <transition name="slide-right" mode="out-in">
        <router-view
          :review-list="userReviewList"
          :review-list-loaded="userReviewListLoaded"
          :order-list="userOrderList"
          :order-list-loaded="userOrderListLoaded"
        ></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
import { getUserReviews, getUserOrders } from '@/api/userOrder';
import { mapMutations, mapGetters } from 'vuex';
import { roundDownPrice } from '@/utils/filters';
export default {
  data() {
    return {
      userReviewList: [],
      userReviewListLoaded: false,
      userOrderList: [],
      userOrderListLoaded: false,
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
  },
  created() {
    // this.setSpinnerState(true);
    this.loadUserReviewList();
    this.loadUserOrderList();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    loadUserReviewList() {
      getUserReviews(this.getUserInfo.id)
        .then(({ data }) => {
          data.forEach(review => {
            review['answerLoaded'] = false;
          });
          this.userReviewList = data;
          this.userReviewListLoaded = true;
        })
        .catch(error => {
          console.log(error);
          alert('리뷰를 불러오는데 실패하였습니다');
        });
    },
    loadUserOrderList() {
      getUserOrders()
        .then(({ data }) => {
          data.forEach(order => {
            Object.assign(order, {
              totalPayment: 0,
              reviewLoaded: false,
              starScore: '',
              reviewContent: '',
            });
            order.reservationGroups.forEach(product => {
              product['computed'] = roundDownPrice(product.price, product.rate);
              order['totalPayment'] += product.computed;
            });
          });
          this.userOrderList = data;
          this.userOrderListLoaded = true;
        })
        .catch(error => {
          console.log(error);
          alert('주문내역을 불러오는데 실패하였습니다');
        });
    },
  },
};
</script>

<style lang="scss" scoped>
@include slide-left-transition(0.4s);
@include slide-right-transition(0.4s);
.user-container {
  width: 100%;
  padding: {
    top: 20px;
    left: 5%;
    right: 5%;
  }
  @include xs-mobile {
    padding: 20px 0 0 0;
  }
}
.router-view-wrapper {
  @include flexbox;
  @include justify-content(center);
}
</style>
