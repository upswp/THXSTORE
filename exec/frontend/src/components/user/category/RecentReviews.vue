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
        <div class="order-menu-list">
          <span v-for="(orderMenu, menuOrder) in reviewItem.reservationGroupDtoList" :key="menuOrder">{{
            orderMenu.productName
          }}</span>
        </div>
        <div class="review-comment">
          <pre>{{ reviewItem.comment }}</pre>
        </div>
        <div v-if="answerCheck(reviewItem.answerDto.comment)" class="answer-item" @click="toggleAnswerLoaded(index)">
          💌 사장님의 편지
          <div v-if="reviewItems[index].answerLoaded">
            <div class="answer-comment">
              <pre>"{{ reviewItem.answerDto.comment }}"</pre>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserReview } from '@/api/userOrder';
import { dateTrans } from '@/utils/filters';
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      reviewItems: [],
      answerLoaded: true,
    };
  },
  created() {
    this.getUserReviewList();
  },
  methods: {
    dateTrans,
    ...mapMutations(['setSpinnerState']),
    toggleAnswerLoaded(index) {
      this.reviewItems[index].answerLoaded = !this.reviewItems[index].answerLoaded;
    },
    answerCheck(answerCheck) {
      if (answerCheck) return true;
      return false;
    },
    async getUserReviewList() {
      try {
        this.setSpinnerState(true);
        const userId = this.$store.state.userInfo.id;
        const { data } = await getUserReview(userId);
        data.forEach(x => {
          x['answerLoaded'] = false;
        });
        this.setSpinnerState(false);
        this.reviewItems = data;
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
      }
    },
    ratingToPercent(star) {
      const score = star * 20;
      return score + 1.5;
    },
  },
};
</script>

<style lang="scss" scoped>
.recent-review-container {
  width: 100%;
  margin: auto;
  max-width: 1180px;
  .recent-review-title {
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
  .recent-review-items {
    @include flexbox;
    background-color: white;
    border: 1px $gray200 solid;
    padding: 1%;
    justify-content: space-between;
    flex-wrap: wrap;
    // flex-direction: column;
    .recent-review-item {
      width: 49%;
      @include shadow1;
      padding: 1%;
      border-radius: 0px 0px 40px 0px;
      border: 2px black solid;
      margin-bottom: 15px;
      @include lg-pc {
        width: 32%;
      }
      @include mobile {
        width: 49%;
      }
      @include xs-mobile {
        width: 80%;
        margin: 7px auto;
      }
    }
    .review-header-container {
      padding: 1%;
      @include flexbox;
      @include align-items(center);
      justify-content: space-between;
      flex-grow: 1;
      flex-wrap: wrap;
      margin-bottom: 10px;
      // border: $gray600 2px solid;
      border-radius: 10px;
      .review-logo {
        $length: clamp(20px, 30%, 120px);
        border-radius: 10%;
        width: $length;
        height: $length;
        object-fit: cover;
        object-position: center 50%;
        margin-right: 10px;
        cursor: pointer;
      }
      .review-info {
        position: relative;
        width: 65%;
        @include mobile {
          width: 65%;
        }
        @include xs-mobile {
          width: 65%;
        }
        .store-label {
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
        .store-name {
          @include lg-pc {
            font-size: 1.4em;
          }
          @include mobile {
            font-size: 1.1em;
          }
        }
        .review-date {
          display: inline-block;
          width: 100%;
          text-align: start;
          @include lg-pc {
            font-size: 0.8em;
          }
          @include pc {
            font-size: 0.7em;
          }
          @include mobile {
            font-size: 0.8em;
          }
          @include xs-mobile {
            font-size: 0.7em;
          }
        }
      }
    }
    .order-menu-list {
      width: 100%;
      padding: 0 2% 0 2%;
      @include flexbox;
      flex-wrap: wrap;
      span {
        border-radius: 30px;
        margin-bottom: 3px;
        padding: 1px 2%;
        margin-right: 1%;
        color: white;
        background-color: $blue200;
        @include lg-pc {
          font-size: 12px;
        }
        @include mobile {
          font-size: 10px;
        }
        @include pc {
          font-size: 10px;
        }
        @include xs-mobile {
          font-size: 0.7em;
        }
      }
    }
    .review-comment {
      width: 100%;
      padding: 2%;
      @include xs-mobile {
        font-size: 0.8em;
      }
      pre {
        font-family: S-CoreDream-4Regular;
        overflow: hidden;
        white-space: pre-line;
        line-break: strict;
        height: 80px;
        overflow-y: auto;
      }
    }
    .answer-item {
      @include xs-mobile {
        font-size: 0.8em;
      }
      &:hover {
        cursor: pointer;
      }
      .answer-comment {
        width: 100%;
        padding: 2%;
        margin-top: 5px;

        pre {
          font-family: Noto Sans KR;
          overflow: hidden;
          white-space: pre-line;
          line-break: strict;
          height: 100px;
          overflow-y: auto;
        }
      }
    }
  }
}

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
