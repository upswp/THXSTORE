<template>
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
  </div>
</template>

<script>
import { oneTrans } from '@/utils/filters';
import { getStoreTimedeal } from '@/api/userStore';
import { mapMutations } from 'vuex';
import SideCalculator from '@/components/storeForUser/timedeal/SideCalculator.vue';
export default {
  components: {
    SideCalculator,
  },
  data() {
    return {
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
    },
  },
};
</script>

<style scoped lang="scss">
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
  }
}
</style>
