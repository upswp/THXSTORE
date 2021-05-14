<template>
  <div class="time-deal-container">
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
            <label :for="`menu-check-${index}`" class="checkbox"></label>
            {{ menu.name }}</label
          >
          <div class="menu-content">
            <img :src="menu.productImg" class="menu-img" />
            <div class="menu-discount-info">
              <table class="time-deal-variable">
                <th>할인율</th>
                <th>재고</th>
                <tr>
                  <td>
                    <input
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
                  <td>{{ menu.price }}</td>
                  <td>{{ menu.discount }}</td>
                </tr>
              </table>
            </div>
          </div>
        </li>
      </ul>
    </section>
    <footer>
      <div class="time-deal-start-header">시작 시간</div>
      <div class="time-deal-start">
        <select v-model="startHour" class="time-select">
          <option v-for="(i, index) in 24" :key="index" :value="timeStrConvert(i - 1, 1)">
            {{ timeStrConvert(i - 1, 1) }}
          </option>
        </select>
        <span>:</span>
        <select v-model="startMinute" class="time-select">
          <option v-for="(i, index) in ['00', '30']" :key="index" :value="i">
            {{ i }}
          </option>
        </select>
      </div>
      <div class="time-deal-button-wrapper">
        <div class="time-deal-button">타임딜 시작</div>
      </div>
    </footer>
  </div>
</template>

<script>
import { getTimeDeal, registerTimeDeal } from '@/api/timeDeal';
import { getTotalMenu } from '@/api/menu';
import { timeStrConvert } from '@/utils/filters';
import { mapGetters, mapMutations } from 'vuex';
export default {
  data() {
    return {
      openTab: [],
      selectedMenus: [],
      startHour: '00',
      startMinute: '00',
      menus: [
        {
          productId: 1,
          name: '우육면 우육면 우육면 우육면 우육면 우육면 우육면 우육면 우육면 우육면',
          price: 9000,
          productImg:
            'https://images.unsplash.com/photo-1432139509613-5c4255815697?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=632&q=80',
          amount: '1인분',
          introduce:
            '따뜻한 우육면 한사발따뜻한 우육면 한사발 따뜻한 우육면 따뜻한 우육면 발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발따뜻한 우육면 한사발',
          rate: 0,
          stock: 0,
          discount: 9000,
        },
        {
          productId: 2,
          name: '마파두부',
          price: 12000,
          productImg:
            'https://images.unsplash.com/photo-1432139509613-5c4255815697?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=632&q=80',
          amount: '1인분',
          introduce: '따뜻한 마파두부 덮밥',
          rate: 0,
          stock: 0,
          discount: 12000,
        },
        {
          productId: 3,
          name: '햄버거',
          price: 7000,
          productImg:
            'https://images.unsplash.com/photo-1432139509613-5c4255815697?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=632&q=80',
          amount: '1인분',
          introduce: '치즈 듬뿍 햄버거',
          rate: 0,
          stock: 0,
          discount: 7000,
        },
      ],
    };
  },

  computed: {
    ...mapGetters(['getStoreId']),
  },
  async created() {
    try {
      this.setSpinnerState(true);
      const { data } = await getTimeDeal(this.getStoreId);
      this.setSpinnerState(false);
      if (data.status === 'PROGRESS' || data.status === 'COMPLETE') {
        this.$router.push({ name: 'storeReservation' });
      } else {
        // 타임딜이 대기 중인 상태에서는 selectedMenus의 정보를 변경해야한다.
        // 추가적으로 타임딜 시작 버튼을 제거하고, 현재 타임딜 시작까지 몇 분 남았는지 알려줘야한다.
      }
    } catch (error) {
      if (error.response.status === 400) {
        try {
          const response = await getTotalMenu(this.getStoreId);
          this.menus = response.data;
          this.setSpinnerState(false);
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
  methods: {
    ...mapMutations(['setSpinnerState']),
    timeStrConvert,
    discounting(menu) {
      const origin = (menu.price / 100) * (100 - parseInt(menu.rate));
      menu.discount = Math.floor(origin / 100) * 100;
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
  },
};
</script>

<style lang="scss" scoped>
@mixin color-pattern {
  background-color: $green200;
  color: white;
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
  background-color: $gray200;
  padding: 10px;
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
</style>
