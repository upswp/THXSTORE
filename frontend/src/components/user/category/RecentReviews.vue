<template>
  <div class="recent-review-container">
    <div class="recent-review-title">최근 리뷰</div>
    <div class="recent-review-items">
      <div v-for="(reviewItem, index) in reviewItems" :key="index" class="recent-review-item">
        <div class="review-header-container">
          <div class="review-logo"><img :src="reviewItem.logo" /></div>
          <div class="review-info">
            <div class="store-label"><label>스토어</label></div>
            <div class="store-name">{{ reviewItem.storeName }}</div>
            <div class="star-ratings">
              <div class="star-ratings-fill" :style="{ width: ratingToPercent(reviewItem.star) + '%' }">
                <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
              </div>
              <div class="star-ratings-base">
                <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
              </div>
            </div>
            <div class="review-date">{{ dateTrans(reviewItem.dateTime) }}</div>
          </div>
        </div>
        <div class="review-comment">
          <pre>{{ reviewItem.comment }} asdfasdfas dfasdfasdfasd</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getRegisterReview } from '@/api/userOrder';
import { dateTrans } from '@/utils/filters';
export default {
  data() {
    return {
      reviewItems: [],
    };
  },
  created() {
    this.ratingToPercent();
    this.getRegisterReviewList();
  },
  methods: {
    dateTrans,
    async getRegisterReviewList() {
      const userId = this.$store.state.userInfo.id;
      const { data } = await getRegisterReview(userId);
      this.reviewItems = data;
      console.log(data);
    },
    ratingToPercent(star) {
      //   console.log(this.restaurant.averageScore);
      const score = star * 20;
      return score + 1.5;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample2.scss';
.star-ratings {
  display: inline-block;
  color: #aaa9a9;
  position: relative;
  unicode-bidi: bidi-override;
  width: max-content;
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 1.3px;
  -webkit-text-stroke-color: #2b2a29;
}

.star-ratings-fill {
  color: #fff58c;
  padding: 0;
  position: absolute;
  z-index: 1;
  display: flex;
  top: 0;
  left: 0;
  overflow: hidden;
  -webkit-text-fill-color: gold;
}

.star-ratings-base {
  z-index: 0;
  padding: 0;
}
</style>
