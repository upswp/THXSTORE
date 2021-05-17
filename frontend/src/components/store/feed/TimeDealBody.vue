<template>
  <div class="feed-above-container">
    <div v-for="(feed, index) in feedList" :key="index" class="feed-wrapper">
      <div class="store-head">
        <div class="store-name-wrapper">
          <img :src="feed.logo" class="store-logo" />
          <span class="store-name">{{ feed.name }}</span>
          <span class="store-category" :class="categoryClassConvert[feed.storeCategory]">{{
            categoryStrConvert[feed.storeCategory]
          }}</span>
        </div>
        <div class="info-wrapper">
          <div>
            <awesome icon="hourglass-half"></awesome>
            타임딜 종료 시간: <span class="deal-end-time">{{ getEndTime(feed.timeDealStart) }}</span>
          </div>
          <div class="item-address">
            <awesome icon="map-marker-alt"></awesome>
            {{ feed.mainAddress }} / {{ Math.floor(feed.distance * 1000) }}m
          </div>
        </div>
      </div>
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide>
          <img :src="feed.thumbImg" />
        </swiper-slide>
        <swiper-slide v-for="(item, index2) in feed.timeDealList" :key="index2"
          ><img :src="item.productImg" />
          <div class="item-info">
            <span class="item-name">{{ item.name }}</span>
            <span class="item-rate">-{{ item.rate }}%</span>
          </div>
          <div class="item-discounted">
            <div class="original-price">{{ oneTrans(item.price) }}</div>
            <div class="discounted-price">{{ oneTrans(item.discounted) }}원</div>
          </div>
          <div v-if="item.stock === 0" class="sold-out">매진 되었습니다.</div>
        </swiper-slide>
        <div slot="pagination" class="swiper-pagination swiper-pagination-bullets"></div>
      </swiper>
    </div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';
import { endTime } from '@/utils/time';
import { categoryStrConvert, categoryClassConvert, timeStrConvert, oneTrans } from '@/utils/filters';
export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  props: {
    feedList: {
      type: Array,
      default: () => [],
      require: true,
    },
  },
  data() {
    return {
      swiperOption: {
        slidesPerView: 3,
        pagination: {
          el: '.swiper-pagination',
          renderBullet(index, className) {
            return `<span class="${className} swiper-pagination-bullet-custom"></span>`;
          },
        },
        breakpoints: {
          768: {
            slidesPerView: 3,
            spaceBetween: 10,
          },
          320: {
            slidesPerView: 2,
            spaceBetween: 10,
          },
        },
      },
      categoryStrConvert,
      categoryClassConvert,
    };
  },
  methods: {
    getEndTime(startTime) {
      const [startHour, startMinute] = startTime.split(':');
      const start = new Date();
      start.setHours(startHour);
      start.setMinutes(startMinute);
      const end = endTime(start);
      return timeStrConvert(end.getHours(), 1) + ':' + timeStrConvert(end.getMinutes(), 1);
    },
    oneTrans,
  },
};
</script>

<style lang="scss" scoped>
svg {
  color: $gray600;
}
.swiper {
  cursor: pointer;
  width: 100%;
  @include lg-pc {
    height: 200px;
  }
  @include pc {
    height: 150px;
  }
  @include mobile {
    height: 150px;
  }
  @include xs-mobile {
    height: 150px;
  }

  border-radius: 10px;
  .swiper-slide {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-weight: bold;
    background-color: white;

    img {
      width: 100%;
      @include lg-pc {
        height: 200px;
      }
      @include pc {
        height: 150px;
      }
      @include mobile {
        height: 150px;
      }
      @include xs-mobile {
        height: 150px;
      }
      object-fit: cover;
      object-position: center 50%;
    }
    color: black;
  }
  /deep/ .swiper-pagination-bullet-custom {
    width: 20px;
    height: 3px !important;
    text-align: center;
    color: black;
    opacity: 0.7;
    border-radius: 0%;
    background: rgba(#00a2ff, 0.6);
    transition: all 0.8s;

    &:hover {
      opacity: 1;
    }

    &.swiper-pagination-bullet-active {
      opacity: 1;
      color: white;
      background: #00a2ff;
    }
  }
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
  .item-rate {
    color: rgb(224, 26, 26);
  }
}
.feed-above-container {
  width: clamp(320px, calc(100% - 200px), 1000px);

  // width:  1000px;
  min-height: 100%;
  padding: 10px 0;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 15px;
  }
  @include mobile {
    font-size: 14px;
    width: 100%;
    padding: 10px;
  }
  @include xs-mobile {
    font-size: 13px;
    width: 100%;
    padding: 10px;
  }
}
.feed-wrapper {
  padding: 10px;
  border: 1px solid $gray400;
  border-radius: 10px;
  margin-bottom: 10px;
}
.store-head {
  @include lg-pc {
    @include flexbox;
    @include justify-content(space-between);
    @include align-items(center);
  }
  @include pc {
    @include flexbox;
    @include justify-content(space-between);
    @include align-items(center);
  }
  @include mobile {
    @include flexbox;
    @include justify-content(space-between);
    @include align-items(center);
  }

  border: 1px solid $gray400;
  padding: 10px;
  margin-bottom: 10px;
}
.store-name {
  font-size: 22px;
  font-weight: bold;
  margin: 10px;
  @include lg-pc {
    font-size: 22px;
  }
  @include pc {
    font-size: 20px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
}
.store-name-wrapper {
  @include flexbox;
  @include align-items(center);
}
.store-category {
  padding: 5px;
  border-radius: 10px;
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
}
.store-logo {
  width: 50px;
  height: 50px;
  border-radius: 10px;
  border: 1px solid $gray400;
}
.info-wrapper {
  text-align: right;
  @include xs-mobile {
    text-align: left;
  }
}
.item-discounted {
  position: absolute;
  padding: 3px;
  border-radius: 3px;
  bottom: 3px;
  right: 3px;
  background-color: white;
}
.deal-end-time {
  color: $red800;
  font-weight: 900;
}

.original-price {
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
  text-decoration: line-through;
}
.discounted-price {
  color: rgb(224, 26, 26);
}

.sold-out {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.3);
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  color: white;
}

$alpha: 0.5;
.category-default {
  background-color: $gray400;
}
.category-kr {
  background-color: rgba(234, 78, 40, $alpha);
  color: #ffffff;
}
.category-cn {
  background-color: rgba(86, 197, 246, $alpha);
  color: black;
}
.category-jp {
  background-color: rgba(169, 2, 42, $alpha);
  color: #ffffff;
}
.category-chicken {
  background-color: rgba(16, 62, 46, $alpha);
  color: #ffffff;
}
.category-pizza {
  background-color: rgba(255, 33, 143, $alpha);
  color: #fff;
}
.category-shcool-food {
  background-color: rgba(65, 184, 131, $alpha);
  color: black;
}
.category-pig-hock {
  background-color: rgba(49, 120, 198, $alpha);
  color: #ffffff;
}
.category-fastfood {
  background-color: rgba(19, 72, 113, $alpha);
  color: #fff;
}
.category-steam-soup {
  background-color: rgba(30, 56, 187, $alpha);
  color: #fff;
}
.category-cafe {
  background-color: rgba(97, 21, 246, $alpha);
  color: #fff;
}
</style>
