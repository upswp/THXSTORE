<template>
  <div class="userstore-menu-containter">
    <div v-for="(menuGroupContainer, index) in storeMenuArr" :key="index" class="menu-group-container">
      <div class="menu-group-title" @click="showAccordion">
        <div class="group-title">{{ menuGroupContainer.name }}</div>
        <awesome icon="chevron-up" class="chevron-up"></awesome>
      </div>
      <div class="transition-div">
        <div class="menu-list-container">
          <div v-for="(menuList, menuIndex) in menuGroupContainer.product" :key="menuIndex" class="menu-list-item">
            <div class="menu-list-info">
              <div class="info-name">
                <b>{{ menuList.name }}</b>
              </div>
              <div class="info-content">
                {{ menuList.introduce }}
              </div>
              <br />
              <label class="info-degree"> {{ menuList.amount }} </label>
              <div class="info-price">{{ menuList.price }}원</div>
            </div>
            <div class="menu-thumbnail">
              <img :src="menuList.productImg" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getStoreMenu } from '@/api/userStore';
export default {
  data() {
    return {
      storeMenuArr: [],
    };
  },
  created() {
    this.getMenuList();
  },
  methods: {
    async getMenuList() {
      try {
        const storeId = this.$route.params.storeId;
        const { data } = await getStoreMenu(storeId);
        this.storeMenuArr = data;
      } catch (error) {
        console.log(error);
      }
    },
    showAccordion(e) {
      console.log(e.currentTarget.querySelector('svg'));
      e.currentTarget.querySelector('svg').classList.toggle('upside-down');
      e.currentTarget.nextElementSibling.classList.toggle('hidden');
    },
  },
};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample2';
</style>
