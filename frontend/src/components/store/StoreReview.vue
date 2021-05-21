<template>
  <div class="userstore-review-container">
    <div class="userstore-review-title">사용자 리뷰</div>
    <div v-if="loaded" class="userstore-review-items">
      <div v-for="(reviewItem, index) in reviewItems" :key="index" class="userstore-review-item">
        <div class="review-header-container">
          <!-- <div class="review-thumbnail"><img :src="reviewItem.logo" /></div> -->
          <div class="review-thumbnail"><img :src="reviewItem.profileImg" /></div>
          <div class="review-info">
            <div class="member-label"><label>고객</label></div>
            <div class="member-name">{{ reviewItem.memberName }}</div>
            <div class="star-ratings">
              <div class="star-ratings-fill" :style="{ width: ratingToPercent(reviewItem.star) + '%' }">
                <!-- <div class="star-ratings-fill" style="width: 100%"> -->
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
        <div v-else class="review-list-footer" @click="toggleAnswerFormLoaded(index)">✏️ 답글</div>
        <form v-if="reviewItems[index].answerFormLoaded" class="answer-form" @submit.prevent="submitForm(index)">
          <textarea
            type="text"
            class="answer-input"
            placeholder="리뷰에대한 답글을 달아주세요"
            autofocus
            @input="answerContent($event, index)"
          ></textarea>
          <button class="close-button" @click="toggleAnswerFormLoaded(index)">닫기</button>
          <button class="submit-button" type="submit">등록</button>
        </form>
      </div>
    </div>
    <div v-else style="text-align: center">작성된 리뷰가 없습니다.</div>
  </div>
</template>

<script>
import { getStoreReview, registerStoreAnswer } from '@/api/userOrder';
import { dateTrans } from '@/utils/filters';
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      loaded: false,
      reviewItems: [],
      answerLoaded: true,
    };
  },
  created() {
    this.getStoreReviewList();
  },
  methods: {
    dateTrans,
    ...mapMutations(['setSpinnerState']),
    toggleAnswerLoaded(index) {
      this.reviewItems[index].answerLoaded = !this.reviewItems[index].answerLoaded;
    },
    toggleAnswerFormLoaded(index) {
      this.reviewItems[index].answerFormLoaded = !this.reviewItems[index].answerFormLoaded;
    },
    answerCheck(answerCheck) {
      if (answerCheck) return true;
      return false;
    },
    answerContent($event, index) {
      this.reviewItems[index].answerContent = $event.target.value;
    },
    async submitForm(index) {
      try {
        this.setSpinnerState(true);
        const rawData = {
          storeId: this.reviewItems[index].storeId,
          comment: this.reviewItems[index].answerContent,
          reviewId: this.reviewItems[index].reviewId,
        };
        const { data } = await registerStoreAnswer(rawData);
        this.toggleAnswerFormLoaded(index);
        this.setSpinnerState(false);
        if (data.message) {
          alert('이미 답글을 작성하셨습니다.');
        } else {
          alert('답글이 정상적으로 등록되었습니다.');
        }
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
      }
    },
    async getStoreReviewList() {
      try {
        this.setSpinnerState(true);
        const storeId = this.$store.state.storeId;
        const { data } = await getStoreReview(storeId);
        data.forEach(x => {
          x['answerLoaded'] = false;
          x['answerFormLoaded'] = false;
          x['answerContent'] = '';
        });
        this.reviewItems = data;
        this.setSpinnerState(false);
        if (this.reviewItems.length) {
          this.loaded = true;
        }
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
@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
.userstore-review-container {
  width: 100%;
  margin: 0px 10px auto;
  max-width: 1180px;
  .userstore-review-title {
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
  .userstore-review-items {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 10px 20px;
    grid-auto-rows: minmax(100px, auto);
    // @include flexbox;
    // justify-content: space-between;
    // flex-wrap: wrap;
    background-color: white;
    border: 1px $gray200 solid;
    padding: 1%;
    // flex-direction: column;
    .userstore-review-item {
      width: 100%;
      @include shadow1;
      padding: 1%;
      border-radius: 0px 0px 40px 0px;
      border: 2px black solid;
      margin-bottom: 15px;
      // @include lg-pc {
      //   width: 32%;
      // }
      // @include mobile {
      //   width: 49%;
      // }
      // @include xs-mobile {
      //   width: 80%;
      // }
      margin: 7px auto;
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
      .review-thumbnail {
        $length: clamp(20px, 30%, 120px);
        border-radius: 10%;
        width: $length;
        height: $length;
        margin-right: 10px;
        cursor: pointer;
        img {
          border-radius: 5px;
          width: 100px;
          height: 105px;
          object-fit: cover;
          object-position: center 50%;
        }
        @include mobile {
          $length: clamp(20px, 20%, 120px);
          width: $length;
          height: $length;
        }
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
        .member-label {
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
        .member-name {
          @include lg-pc {
            font-size: 1.4em;
          }
          @include mobile {
            font-size: 1.2em;
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
        margin-bottom: 3px;
        border-radius: 30px;
        padding: 2px 2%;
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
    }
    .answer-comment {
      width: 100%;
      padding: 2%;
      margin-top: 5px;
      animation: fade-in 1s;
      animation-fill-mode: alt;

      pre {
        font-family: S-CoreDream-4Regular;
        overflow: hidden;
        white-space: pre-line;
        line-break: strict;
        height: 100px;
        overflow-y: auto;
      }
    }
    .review-list-footer {
      width: 100%;
      text-align: center;
      // position: absolute;
      bottom: 0px;

      &:hover {
        cursor: pointer;
      }
    }
    .answer-form {
      padding-top: 15px;
      text-align: center;
      .answer-input {
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
