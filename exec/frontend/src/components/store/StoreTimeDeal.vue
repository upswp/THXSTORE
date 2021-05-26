<template>
  <div v-if="loaded" class="time-deal-container">
    <header class="time-deal-title">타임딜 등록</header>
    <section class="tiem-deal-accordian-container">
      <ul class="time-deal-accordian">
        <li v-for="(menu, index) in menus" :key="index" class="accordian-item">
          <input :id="`menu-radio-${index}`" v-model="openTab" type="checkbox" :value="index" class="menu-item-radio" />

          <label :for="`menu-radio-${index}`" class="menu-item-label">
            <input
              :id="`menu-check-${index}`"
              v-model="selectedMenus"
              type="checkbox"
              :value="index"
              class="check-input"
            />
            <label v-if="!reservation" :for="`menu-check-${index}`" class="checkbox"></label>
            {{ index + 1 }}. {{ menu.name }}</label
          >
          <div class="menu-content">
            <img :src="menu.productImg" class="menu-img" />
            <div class="menu-discount-info">
              <table class="time-deal-variable">
                <th>할인율</th>
                <th>재고</th>
                <tr>
                  <td>
                    <input v-if="reservation" v-model="menu.rate" type="text" disabled class="counter-input-disabled" />
                    <input
                      v-else
                      v-model="menu.rate"
                      type="number"
                      step="1"
                      class="counter-input"
                      min="0"
                      max="100"
                      @input="limit(menu, 'rate')"
                    />%
                  </td>
                  <td>
                    <input
                      v-if="reservation"
                      v-model="menu.stock"
                      type="text"
                      disabled
                      class="counter-input-disabled"
                    />
                    <input
                      v-else
                      v-model="menu.stock"
                      type="number"
                      step="1"
                      class="counter-input"
                      min="0"
                      max="100"
                      @input="limit(menu, 'stock')"
                    />개
                  </td>
                </tr>
              </table>
              <table class="time-deal-price">
                <th>정상가</th>
                <th>할인 적용</th>
                <tr>
                  <td>{{ oneTrans(menu.price) }}원</td>
                  <td>{{ oneTrans(menu.discounted) }}원</td>
                </tr>
              </table>
            </div>
          </div>
        </li>
      </ul>
    </section>
    <footer>
      <div class="time-deal-start-header">{{ reservation ? '타임딜까지 남은 시간' : '시작 시간' }}</div>
      <div v-if="!reservation" class="time-deal-start">
        <select v-model="startHour" class="time-select">
          <option v-for="(i, index) in 24" :key="index" :value="timeStrConvert(i - 1, 1)">
            {{ timeStrConvert(i - 1, 1) }}
          </option>
        </select>
        <span>:</span>
        <select v-model="startMinute" class="time-select">
          <option v-for="(i, index) in 12" :key="index" :value="timeStrConvert(i - 1, 5)">
            {{ timeStrConvert(i - 1, 5) }}
          </option>
        </select>
      </div>
      <div v-else class="time-deal-ready"></div>
      <div class="time-deal-button-wrapper">
        <button v-if="!reservation" class="time-deal-button" :disabled="!validForm" @click="goTimeDeal">
          타임딜 시작
        </button>
        <div v-else>
          <span class="tiem-deal-counter">{{ countdown }}</span>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import { getTimeDeal, registerTimeDeal } from '@/api/timeDeal';
import { getTotalMenu } from '@/api/menu';
import { timeStrConvert, oneTrans } from '@/utils/filters';
import { mapGetters, mapMutations } from 'vuex';
import { countDownTimer } from '@/utils/time';
export default {
  data() {
    return {
      openTab: [],
      selectedMenus: [],
      startHour: '00',
      startMinute: '00',
      menus: [],
      reservation: false,
      timer: '',
      timerDone: false,
      countdown: '',
      loaded: false,
    };
  },
  computed: {
    ...mapGetters(['getStoreId']),
    validForm() {
      if (this.selectedMenus.length === 0) return false;
      return true;
    },
  },
  watch: {
    timerDone(newValue) {
      if (newValue) {
        this.countdown = '0';
        // this.$router.push({ name: 'storeReservation' });
      }
    },
  },
  async created() {
    try {
      this.setSpinnerState(true);
      const { data } = await getTimeDeal(this.getStoreId);
      this.setSpinnerState(false);
      // if (data.status === 'PROGRESS' || data.status === 'COMPLETE') {
      // alert('오늘은 더 이상 타임딜을 등록할 수 없습니다! 내일을 기대해주세요~');
      // this.$router.push({ name: 'storeReservation' });
      // } else {
      // 타임딜이 대기 중인 상태에서는 selectedMenus의 정보를 변경해야한다.
      this.openTab = Array.from({ length: data.timeDeal.length }, (v, i) => i);
      const [startHour, startMinute] = data.startTime.split(':');
      this.startHour = startHour;
      this.startMinute = startMinute;
      this.selectedMenus = [...this.openTab];
      this.menus = data.timeDeal;
      this.menus.forEach(menu => this.discounting(menu));
      this.reservation = true;
      this.$nextTick(() => {
        const start = new Date();
        start.setHours(this.startHour);
        start.setMinutes(this.startMinute);
        this.timer = countDownTimer(start, this);
      });
      this.loaded = true;
      // }
    } catch (error) {
      if (error.response.status === 400) {
        try {
          const response = await getTotalMenu(this.getStoreId);
          this.menus = response.data.map(x =>
            Object.assign(x, {
              rate: 0,
              stock: 0,
              discounted: x.price,
            }),
          );
          this.setSpinnerState(false);
          this.loaded = true;
        } catch (error) {
          alert('메뉴 조회에 실패하였습니다.');
          this.setSpinnerState(false);
        }
      } else {
        alert('타임딜 조회에 실패하였습니다.');
        this.setSpinnerState(false);
      }
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    oneTrans,
    countDownTimer,
    ...mapMutations(['setSpinnerState']),
    timeValid() {
      // 2. 현재 시간이 설정한 시간 보다 짧을 경우
      const now = new Date();
      const start = new Date(now.getFullYear(), now.getMonth(), now.getDate(), this.startHour, this.startMinute);
      if (now >= start) return false;
      return true;
    },
    timeStrConvert,
    discounting(menu) {
      const origin = (menu.price / 100) * (100 - parseInt(menu.rate));
      menu.discounted = Math.floor(origin / 100) * 100;
    },
    limit(menu, target) {
      if (target === 'rate') {
        if (menu.rate === '') {
          menu.rate = 0;
          return;
        }
        const num = parseInt(menu.rate);
        menu.rate = Math.min(100, Math.max(0, num));
        this.discounting(menu);
      } else {
        if (menu.stock === '') {
          menu.rate = 0;
          return;
        }
        const num = parseInt(menu.stock);
        menu.stock = Math.min(100, Math.max(0, num));
      }
    },
    async goTimeDeal() {
      if (!this.timeValid()) {
        alert('타임딜 시작 시간은 현재 시각보다 이후로 설정해주세요.');
        return;
      }
      try {
        const timeDealList = this.selectedMenus.filter(
          index => this.menus[index].stock > 0 && this.menus[index].rate > 0,
        );
        if (timeDealList.length === 0) {
          alert('유효한 할인율과 재고를 적어주세요');
          return;
        } else if (timeDealList.length === 1) {
          alert('두 개 이상의 상품을 등록해주세요');
          return;
        }
        this.setSpinnerState(true);
        await registerTimeDeal({
          storeId: this.getStoreId,
          startTime: this.startHour + ':' + this.startMinute,
          timeDealList: timeDealList.map(index => {
            return {
              productId: this.menus[index].id,
              stock: this.menus[index].stock,
              rate: this.menus[index].rate,
            };
          }),
        });
        this.setSpinnerState(false);
        alert('성공적으로 타임딜이 등록 되었습니다.');
        this.$router.go();
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('타임딜 등록에 실패하였습니다.');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@mixin color-pattern {
  background-color: $green200;
  color: white;
}
.tiem-deal-counter {
  font-size: 20px;
  margin-bottom: 20px;
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
}
.time-deal-container {
  padding-top: 10px;
  width: clamp(320px, 100%, 1000px);
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}

.time-deal-title {
  text-align: center;
  font-weight: bold;
  font-size: 22px;
  @include pc {
    font-size: 20px;
  }
  @include mobile {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
  margin-bottom: 20px;
}

.time-deal-accordian-container {
  width: 100%;
}
.time-deal-accordian {
  display: block;
  width: 100%;
}
.accordian-item {
  display: block;
  width: 100%;
}
.menu-content {
  transition: all 0.3s;
  height: 0;
  overflow: hidden;
  border: {
    right: 1px solid $gray400;
    left: 1px solid $gray400;
    bottom: 1px solid $gray400;
  }
  margin-bottom: 2px;
}
.menu-item-radio {
  display: none;
  &:checked ~ .menu-item-label {
    @include color-pattern;
  }
  &:checked ~ .menu-content {
    padding: 5px;
    @include flexbox;
    height: 113px;
  }
}
.menu-item-label {
  padding: 5px;
  @include flexbox;
  @include align-items(center);

  width: 100%;
  background-color: white;

  border: 1px solid $gray400;
  cursor: pointer;
  transition: background-color 0.2s ease-out;
  &:hover {
    @include color-pattern;
  }
}
.menu-img {
  width: 100px;
  height: 102px;
  object-fit: cover;
  object-position: 50% center;
  display: inline-block;
}
.check-input {
  display: none;
  &:checked + .checkbox::before {
    content: '\2713';
    font-weight: bold;
    color: $green600;
  }
}
.checkbox {
  display: inline-block;
  margin-right: 5px;
  &::before {
    display: inline-block;
    margin-right: 1px;
    width: 20px;
    height: 20px;
    border: 1px solid $gray600;
    background-color: white;
    content: '';
    cursor: pointer;
    border-radius: 3px;
    @include flexbox;
    @include justify-content(center);
    @include align-items(center);
  }
}

.counter-input {
  margin-bottom: 10px;
  padding-left: 15px;
  padding-top: 2px;
  border: none;
  text-align: center;
  background-color: $red100;
}
.counter-input-disabled {
  display: inline-block;
  width: 40px;
  margin-bottom: 10px;
  padding-top: 2px;
  border: none;
  text-align: center;
  background-color: $red100;
}
.menu-discount-info {
  width: calc(100% - 100px);
  padding: 0 10px;
}

.time-deal-variable {
  width: 100%;
  th {
    width: 50%;
    text-align: center;
    background-color: $gray100;
  }
  td {
    padding-top: 5px;
    text-align: center;
  }
}
.time-deal-price {
  width: 100%;
  th {
    width: 50%;
    text-align: center;
    background-color: $gray100;
  }
  td {
    padding-top: 5px;
    text-align: center;
  }
}
.time-select {
  display: block;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  &::-ms-expand {
    display: none;
  }
  width: 60px;
  height: 32px;
  @include mobile {
    height: 28px;
  }
  @include xs-mobile {
    height: 28px;
  }
  border: 2px solid;
  border-color: $blue400;
  color: $gray800;
  padding: 0 20px 0 8px;
  border-radius: 4px;
  // line-height: var(--leading-tight);
  background: url('../../assets/image/arrow.svg') no-repeat 90% 50%;
  background-size: 10px;
  text-indent: 0;
  cursor: pointer;
  padding: 5px;
}
.time-deal-start {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  margin-bottom: 40px;
}
.time-deal-button-wrapper {
  text-align: center;
}
.time-deal-button {
  margin: 10px auto;
  width: 200px;
  &:disabled {
    background-color: $gray200;
    color: $gray600;
  }
  background-color: $yellow200;
  padding: 10px;
  border: none;
  color: rgb(4, 92, 4);
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s;
  &:hover {
    background-color: $blue400;
    color: white;
  }
}
.tiem-deal-accordian-container {
  margin-bottom: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.time-deal-start-header {
  text-align: center;
  font-weight: bold;
  margin-bottom: 10px;
  @include lg-pc {
    font-size: 20px;
  }
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 14px;
  }
}
.time-deal-ready {
}
</style>
