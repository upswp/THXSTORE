<template>
<<<<<<< HEAD
  <div class="time-deal-container">
    <div v-if="loaded" class="content-wrapper">
      <section class="time-deal-section">
        <masonry :cols="cols" :gutter="10">
          <div v-for="(item, index) in timeDealList" :key="index" class="item-card">
            <div class="card-wrapper">
              <img
                :src="item.productImg"
                class="product-img"
                :style="{ height: item.stock > 30 ? '300px' : item.stock > 20 ? '280px' : '260px' }"
                @click="toggleCard(item, index)"
              />
              <div class="item-info">
                <span class="item-name">{{ item.name }}</span>
              </div>
              <div class="item-discounted">
                <div class="item-rate">-{{ item.rate }}%</div>
                <div class="discounted-price">{{ oneTrans(item.discounted) }}원</div>
              </div>
              <transition name="slide">
                <div v-if="item.selected" class="item-selected" @click="toggleCard(item, index)">
                  <awesome :icon="['far', 'check-circle']"></awesome>
                </div>
              </transition>
              <div v-if="item.stock === 0" class="item-sold-out">Sold Out</div>
            </div>
          </div>
        </masonry>
      </section>
      <side-calculator :menus="timeDealList"></side-calculator>
    </div>
    <div v-else class="content-except">
      {{ message }}
    </div>
=======
  <div class="timedeal-container">
    <header>
      <h3>타임딜 남은 시간 : {{ countdown }}</h3>
    </header>
    <br />
    <br />
    <div v-for="(timedealItem, index) in timedealList" :key="index" class="timedeal-item">
      <div class="item-thumbnail">
        <img :src="timedealItem.productImg" />
      </div>
      <div class="item-info">
        <div class="reserved-stock">{{ timedealItem.stock }}장 남음</div>
        <div class="info-name">
          {{ timedealItem.name }} <small> ({{ timedealItem.amount }})</small>
        </div>
        <div class="info-introduce">
          <p>
            {{ timedealItem.introduce }}
          </p>
        </div>

        <div class="info-footer">
          <div class="info-timedeal-price">
            <div class="info-origin-price">
              <s>{{ oneTrans(timedealItem.price) }}원</s>
            </div>
            <!-- <div class="price-label">타임딜가</div> -->
            <div class="info-sale-price">
              <b>{{ oneTrans(timedealItem.discounted) }}</b
              >원
            </div>
          </div>
          <div class="order-number-container">
            <button class="quantity-minus-button" @click="minusCount(timedealItem)">-</button>
            <input
              id="quantity-value"
              v-model="timedealItem.count"
              type="number"
              class="quantity-value"
              placeholder="1"
              @input="accItem(timedealItem)"
            />
            <button class="quantity-plus-button" @click="plusCount(timedealItem)">+</button>
            개
            <button>
              <awesome icon="cart-arrow-down" class="cart-arrow-down" @click="inputCart(index)"></awesome>
            </button>
          </div>
          <div class="item-sum">{{ oneTrans(timedealItem.computed) }}</div>
        </div>
      </div>
    </div>
    <footer class="timedeal-footer-group">
      <div class="timedeal-sum">{{ totalSum }} 요</div>
      <button class="timedeal-sum" @click="makeOrder()">예약번호 받기</button>
    </footer>
>>>>>>> d7be88b ([S04P31B202-354] feat: 타임딜조회 함수 연결)
  </div>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { getStoreTimedeal } from '@/api/userStore';
<<<<<<< HEAD
import { mapMutations } from 'vuex';
import SideCalculator from '@/components/storeForUser/timedeal/SideCalculator.vue';
=======
import { oneTrans } from '@/utils/filters';
import { endTime, countDownTimer } from '@/utils/time';
>>>>>>> d7be88b ([S04P31B202-354] feat: 타임딜조회 함수 연결)
export default {
  components: {
    SideCalculator,
  },
  data() {
    return {
<<<<<<< HEAD
      timeDealList: [],
      selected: [],
      storeId: this.$route.params.storeId,
      options: {
        width: 300,
        padding: 10,
      },
      loaded: false,
      message: '잠시만 기다려주세요. 데이터를 불러오고 있습니다.',
      cols: { default: 4, 1200: 3, 1000: 2, 900: 1, 768: 3, 500: 2, 360: 1 },
=======
      timer: '',
      timerDone: false,
      countdown: '',
      loaded: false,
      timedealList: [],
      reservationGroup: [],
      checkList: [],
      totalSum: 0,
>>>>>>> d7be88b ([S04P31B202-354] feat: 타임딜조회 함수 연결)
    };
  },

  beforeDestroy() {
    clearInterval(this.timer);
  },
  async created() {
    await this.getTimedealList();
    window.scrollTo({ top: 137, left: 0, behavior: 'smooth' });
    this.counterOn();
    this.makeReservationGroup();
  },
  methods: {
<<<<<<< HEAD
    ...mapMutations(['setSpinnerState']),
    oneTrans,
    toggleCard(card, index) {
      card.selected = !card.selected;
      card.count = 1;
      card.payFor = card.discounted;
      this.selected[index] = !this.selected[index];
    },
    async getTimedealList() {
      try {
        this.setSpinnerState(true);
        const { data } = await getStoreTimedeal(this.storeId);
        this.timeDealList = data.timeDeal.map(x => {
          const originComputed = (x.price * (100 - x.rate)) / 100;
          return Object.assign(x, {
            count: 0,
            computed: 0,
            selected: false,
            discounted: Math.floor(originComputed / 100) * 100,
            height: x.stock > 30 ? 130 : x.stock > 20 ? 120 : 110,
            payFor: 0,
          });
        });
        this.selected = Array.from({ length: data.timeDeal.length }, () => false);
        this.loaded = true;
        this.setSpinnerState(false);
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
        this.message = '해당 가게는 타임딜을 진행하고 있지 않습니다';
      }
=======
    oneTrans,
    makeReservationGroup() {
      for (let i = 0; i < this.timedealList.length; i++) {
        this.checkList[i] = false;
        console.log('레저베이션그룹', this.checkList);
      }
    },
    inputCart(index) {
      // this.reservationGroup[index].productId = timedealItem.productId;
      // this.reservationGroup[index].productName = timedealItem.name;
      // this.reservationGroup[index].price = timedealItem.price;
      // this.reservationGroup[index].count = timedealItem.count;
      // this.reservationGroup[index].rate = timedealItem.rate;
      this.checkList[index] = !this.checkList[index];
      if (this.checkList[index]) {
        this.totalSum += this.timedealList[index].computed;
      } else this.totalSum -= this.timedealList[index].computed;

      console.log('레저베이션 그룹에 메뉴를 담는다', this.reservationGroup);
    },
    makeOrder() {
      const formData = new FormData();
      // for (let i =0 ; )
      formData.append('reservationGroups', this.reservationGroup);
      formData.append('userId', this.$store.state.userInfo.id);
      formData.append('storeId', this.$store.state.storeId);
      formData.append('nickname', this.$store.state.userInfo.nickname);
    },
    plusCount(timedealItem) {
      timedealItem.count++;
      if (timedealItem.count > timedealItem.stock) {
        return (timedealItem.count = timedealItem.stock);
      }
      this.accItem(timedealItem);
    },
    minusCount(timedealItem) {
      timedealItem.count--;
      if (timedealItem.count < 0) {
        return (timedealItem.count = 0);
      }
      this.accItem(timedealItem);
    },
    counterOn() {
      const storeId = this.$route.params.storeId;
      getStoreTimedeal(storeId).then(response => {
        const data = response.data;
        const [startHour, startMinute] = data.startTime.split(':');
        const start = new Date();
        start.setHours(startHour);
        start.setMinutes(startMinute);
        this.timer = countDownTimer(endTime(start), this);
      });
    },
    accItem(timedealItem) {
      const origin = timedealItem.count * timedealItem.discounted;
      timedealItem.computed = Math.floor(origin / 100) * 100;
    },
    async getTimedealList() {
      const storeId = this.$route.params.storeId;
      const { data } = await getStoreTimedeal(storeId);
      console.log(data);
      this.startTime = data.startTime;
      this.timedealList = data.timeDeal.map(x =>
        Object.assign(x, { count: 0, checkOrder: 0, computed: 0, discounted: (x.price * (100 - x.rate)) / 100 }),
      );
      this.checkList = Array.from({ length: this.timedealList.length }, () => false);

      // console.log(this.timedealList);
>>>>>>> d7be88b ([S04P31B202-354] feat: 타임딜조회 함수 연결)
    },
  },
};
</script>

<style scoped lang="scss">
<<<<<<< HEAD
.content-except {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  font-weight: bold;
  @include lg-pc {
    font-size: 28px;
  }
  @include pc {
    font-size: 24px;
  }
  @include mobile {
    font-size: 20px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
  color: $gray600;
  height: 300px;
}
.time-deal-section {
  @include lg-pc {
    width: calc(100% - 280px);
    padding-right: 10px;
  }
  @include pc {
    width: calc(100% - 280px);
    padding-right: 10px;
  }
  @include mobile {
    margin-bottom: 360px;
  }
  @include xs-mobile {
    margin-bottom: 360px;
  }
}
.card-wrapper {
  cursor: pointer;
}
.product-img {
  width: 100%;
  object-fit: cover;
  object-position: center 50%;
  border-radius: 10px;
}

.item-card {
  width: 100%;
  margin-bottom: 10px;
  position: relative;
}
.item-selected {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  font-size: 50px;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  color: rgb(11, 228, 11);
  background-color: rgba(0, 0, 0, 0.5);
}
.item-not-selected {
  position: absolute;
  background-color: transparent;
  top: 0px;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  color: white;
  font-size: 20px;
}
.item-sold-out {
  z-index: 1;
  position: absolute;
  background-color: rgba(0, 0, 0, 0.8);
  font-size: 18px;
  font-weight: bold;
  top: 0;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  color: rgb(246, 119, 119);
}
.content-wrapper {
  @include lg-pc {
    @include flexbox;
  }
  @include pc {
    @include flexbox;
  }
}
.calculator-container {
  @include lg-pc {
    width: 280px;
  }
  @include pc {
    width: 280px;
  }
}

.slide-enter-active {
  transition: all 0.4s;
}
.slide-leave-active {
  transition: all 0.4s;
}
.slide-enter, .slide-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateY(10px);
  opacity: 0;
}
.item-info {
  padding: 3px;
  border-radius: 8px;
  position: absolute;
  width: 100%;
  top: 0px;
  @include flexbox;
  @include justify-content(space-between);
  span {
    padding: 3px;
    background-color: white;
    border-radius: 5px;
  }
  .item-name {
    max-width: 150px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
.item-discounted {
  position: absolute;
  padding: 3px;
  border-radius: 3px;
  bottom: 8px;
  right: 3px;
  background-color: white;
}
.item-rate {
  color: rgb(224, 26, 26);
  text-align: right;
  @include lg-pc {
    font-size: 13px;
  }
  @include pc {
    font-size: 12px;
  }
  @include mobile {
    font-size: 10px;
  }
  @include xs-mobile {
    font-size: 9px;
=======
// @import '@/assets/scss/sample2';
.timedeal-container {
  border: 2px solid $gray400;
  header {
    text-align: center;
  }
  .timedeal-item {
    border: 2px solid $blue400;
    border-radius: 15px;
    @include shadow1;
    @include flexbox;
    flex-wrap: wrap;
    width: 95%;
    margin: auto;
    @include xs-mobile {
      width: 100%;
    }

    .item-thumbnail {
      @include flexbox;
      align-items: center;
      width: 60%;
      padding: 1%;
      text-align: center;
      border-right: 2px solid $gray400;
      @include xs-mobile {
        width: 100%;
      }
      img {
        object-fit: cover;
        object-position: center 50%;
        border-radius: 5%;
        @include lg-pc {
          height: 278px;
          width: 100%;
        }
        @include pc {
          height: 210px;
          width: 100%;
        }
        @include mobile {
          height: 210px;
          width: 100%;
        }
        @include xs-mobile {
          width: 100%;
        }
      }
    }
    .item-info {
      @include flexbox;
      width: 40%;
      padding: 1%;
      flex-direction: column;
      // justify-content: space-between;
      // flex-wrap: wrap;
      @include xs-mobile {
        width: 100%;
      }
      .info-header {
        @include flexbox;
        width: 100%;

        .timedeal-title {
          width: 30%;
          margin-right: 5%;
          border-radius: 10px;
          border: $gray200 solid 1px;

          .timedeal-logo {
            width: 60%;
            background-color: $red600;
            text-align: center;
            color: whitesmoke;
            border-radius: 10px;
            padding: 2%;
          }
        }
      }
      .reserved-stock {
        width: 100%;
        font-size: 0.9em;
        text-align: end;
        @include xs-mobile {
          font-size: 0.6em;
        }
      }
      .info-name {
        font-weight: bold;
        @include lg-pc {
          font-size: 1.3em;
          padding-top: 10px;
          padding-bottom: 10px;
        }
        @include xs-mobile {
          font-size: 0.9em;
        }
      }
      .info-introduce {
        @include flexbox;
        height: 150px;
        word-break: keep-all;
        align-items: center;
        @include pc {
          font-size: 0.9em;
          height: 120px;
        }
        @include mobile {
          font-size: 0.9em;
          height: 120px;
        }
        @include xs-mobile {
          word-break: normal;
          padding-top: 10px;
          padding-bottom: 10px;
          height: 70px;
          line-height: 15px;
          display: -webkit-box;
          -webkit-line-clamp: 4;
          -webkit-box-orient: vertical;
          overflow: hidden;
          font-size: 0.7em;
        }
      }
      .info-footer {
        @include flexbox;
        flex-wrap: wrap;
        // padding-top: 50px;
        justify-content: space-between;
        .info-timedeal-price {
          margin-bottom: 10px;
          display: inline-block;
          width: 100%;
          .info-origin-price {
            color: red;
            font-size: 0.8em;
            @include xs-mobile {
              font-size: 0.7em;
            }
            &::before {
              content: '타임딜가z ';
              color: white;
              font-size: 15px;
              @include mobile {
                content: '타임딜가  ';
                font-size: 0.7em;
              }
              @include xs-mobile {
                font-size: 1em;
              }
            }
          }
          .info-sale-price {
            &::before {
              content: '타임딜가 ';
              color: $red600;
              font-weight: bold;
              font-size: 15px;
              @include mobile {
                font-size: 0.7em;
              }
              @include xs-mobile {
                font-size: 0.7em;
              }
            }
            @include mobile {
              font-size: 0.9em;
            }
            // @include xs-mobile {
            //     font-size: 0.7em;
            // }
          }
        }
        .order-number-container {
          input[type='number']::-webkit-outer-spin-button,
          input[type='number']::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
          }
          @include flexbox;
          align-items: center;
          width: 60%;
          height: 25px;
          .quantity-minus-button {
            height: 100%;
            border-radius: 0px;
            line-height: 100%;
            border: none;
          }
          .quantity-value {
            display: inline-block;
            width: 30%;
            height: 100%;
            border: 1px $gray200 solid;
            padding: 5px;
            @include pc {
              font-size: 0.8em;
            }
            @include mobile {
              font-size: 0.6em;
            }
          }
          .quantity-plus-button {
            height: 100%;
            border: none;
            border-radius: 0px;
            line-height: 100%;
          }
          .cart-arrow-down {
            font-size: 1.3em;
            margin-left: 5px;
            color: $gray400;
            @include mobile {
              font-size: 1em;
            }
            &:hover {
              cursor: pointer;
              color: $red600;
            }
          }
        }
        .item-sum {
          width: 38%;
          text-align: end;
          margin-right: 2%;
          line-height: 25px;
          border-bottom: 2px $gray400 solid;
          @include pc {
            display: table-cell;
            vertical-align: middle;
            font-size: 0.9em;
          }
          @include mobile {
            display: table-cell;
            vertical-align: middle;
            font-size: 0.8em;
          }
          @include xs-mobile {
            display: table-cell;
            vertical-align: middle;
            font-size: 0.9em;
          }
        }
      }
    }
    .item-sum {
      width: 30%;
    }
>>>>>>> 795cf7d ([S04P31B202-354] chore: scss파일 정리)
  }
}
</style>
