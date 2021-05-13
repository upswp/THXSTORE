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
import { timeStrConvert } from '@/utils/filters';
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
  created() {},
  methods: {
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
@import '@/assets/scss/sample';
</style>
