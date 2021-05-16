<template>
  <div class="timedeal-container">
    <header><h3>타임딜 남은 시간 : 1시간</h3></header>
    <br />
    <br />
    <div v-for="(timedealItem, index) in timedealList" :key="index" class="timedeal-item">
      <div class="item-thumbnail">
        <img :src="timedealItem.productImg" />
      </div>
      <div class="item-info">
        <div class="reserved-stock">{{ timedealItem.stock }} 남음</div>
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
              <s>{{ timedealItem.price }}원</s>
            </div>
            <!-- <div class="price-label">타임딜가</div> -->
            <div class="info-sale-price">
              <b>{{ getComputed(timedealItem) }}</b
              >원
            </div>
          </div>
          <div class="order-number-container">
            <button class="quantity-minus-button">-</button>
            <input
              id="quantity-value"
              v-model="timedealItem.count"
              type="number"
              class="quantity-value"
              placeholder="1"
              min="1"
              :max="timedealItem.stock"
              @change="accItem(timedealItem)"
            />
            개
            <!-- <button class="quantity-plus-button">+</button> -->
            <awesome icon="cart-arrow-down" class="cart-arrow-down"></awesome>
          </div>
          <div class="item-sum">{{ timedealItem.computed }}</div>
          <input v-model="timedealItem.computed" class="item-sum" />
        </div>
      </div>
    </div>
    <footer class="timedeal-footer-group">
      <div class="timedeal-sum">총합계는 요</div>
      <button class="timedeal-sum">예약번호 받기</button>
    </footer>
  </div>
</template>

<script>
import { getStoreTimedeal } from '@/api/userStore';
export default {
  data() {
    return {
      timedealList: [],
    };
  },
  mounted() {
    window.scrollTo({ top: 137, left: 0, behavior: 'smooth' });
  },
  created() {
    this.getTimedealList();
  },
  methods: {
    accItem(timedealItem) {
      timedealItem.computed = timedealItem.count * timedealItem.discounted;
    },
    getComputed(item) {
      return item.discounted;
    },
    async getTimedealList() {
      const storeId = this.$route.params.storeId;
      const { data } = await getStoreTimedeal(storeId);
      this.timedealList = data.timeDeal;
      for (let i = 0; i < this.timedealList.length; i++) {
        this.timedealList[i].count = 0;
        this.timedealList[i].checkOrder = 0;
        this.timedealList[i].computed = 0;
        this.timedealList[i].discounted = (this.timedealList[i].price * (100 - this.timedealList[i].rate)) / 100;
      }
      // console.log(this.timedealList);
    },
  },
};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample2';
</style>
