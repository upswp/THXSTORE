<template>
  <div class="edit-container">
    <section class="edit-wrapper">
      <label for="upload-button" class="upload-button-wrapper">
        <img :src="menu.productImg" class="menu-image" />
        <input id="upload-button" type="file" @change="uploadImg" />
      </label>
      <div class="menu-name">
        <label for="menu-name-label">
          <div class="menu-label">상품명</div>
          <input
            id="menu-name-label"
            v-model="menu.name"
            type="text"
            class="menu-input"
            placeholder="name"
            maxlength="30"
          />
        </label>
      </div>
      <div class="menu-price-amount">
        <div class="menu-price">
          <label for="menu-price-label">
            <div class="menu-label">가격</div>
            <input
              id="menu-price-label"
              v-model="menu.price"
              type="text"
              class="menu-input"
              placeholder="price"
              maxlength="10"
            />
          </label>
        </div>
        <div class="menu-amount">
          <label for="menu-amount-label">
            <div class="menu-label">단위</div>
            <input
              id="menu-amount-label"
              v-model="menu.amount"
              type="text"
              class="menu-input"
              placeholder="amount"
              maxlength="20"
            />
          </label>
        </div>
      </div>
      <div class="menu-intro">
        <label for="menu-intro-label">
          <div class="menu-label">메뉴 설명</div>
          <textarea id="menu-intro-label" v-model="menu.introduce" class="menu-textarea" maxlength="300"></textarea>
        </label>
      </div>
      <button class="submit-button" @click="submit">완료</button>
    </section>
  </div>
</template>
<script>
import { getMenu, registerMenuByGroup, updateMenu } from '@/api/menu';
import { mapMutations } from 'vuex';
export default {
  props: {
    productId: {
      type: Number,
      default: -1,
      require: true,
    },
    groupId: {
      type: Number,
      default: -1,
      require: true,
    },
  },
  data() {
    return {
      menu: {
        name: '',
        price: '',
        amount: '',
        introduce: '',
        productImg: require('@/assets/image/uploadGuide.jpg'),
      },
      origin: {},
      menuFile: '',
    };
  },
  async created() {
    if (this.productId === -1) return;
    try {
      const { data } = await getMenu(this.productId);
      this.origin = data;
      console.log(data);
    } catch (error) {
      console.log(error);
      alert('메뉴 상세 조회에 실패하였습니다.');
    }
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    uploadImg(e) {
      const file = e.target.files[0];
      this.menuFile = file;
      this.menu.productImg = URL.createObjectURL(file);
    },
    async createMenu() {
      try {
        this.setSpinnerState(true);
        const frm = new FormData();
        frm.append('groupId', this.groupId);
        frm.append('name', this.menu.name);
        frm.append('price', this.menu.price);
        frm.append('amount', this.menu.amount);
        frm.append('introduce', this.menu.introduce);
        frm.append('productImg', this.menuFile);
        await registerMenuByGroup(frm);
        this.setSpinnerState(false);
        this.$emit('toggleEdit', false);
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('메뉴 등록에 실패하였습니다.');
      }
    },
    async updateMenu() {
      try {
        this.setSpinnerState(true);
        const frm = new FormData();
        frm.append('productId', this.productId);
        if (this.origin.name !== this.menu.name) frm.append('name', this.menu.name);
        if (this.origin.price !== this.menu.price) frm.append('price', this.menu.price);
        if (this.origin.amount !== this.menu.amount) frm.append('amount', this.menu.amount);
        if (this.origin.introduce !== this.menu.introduce) frm.append('introduce', this.menu.introduce);
        if (this.menuFile) frm.append('productImg', this.menuFile);

        await updateMenu(frm);
        this.setSpinnerState(false);
        this.$emit('toggleEdit', false);
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        alert('메뉴 수정에 실패하였습니다.');
      }
    },
    submit() {
      if (this.productId === -1) this.createMenu();
      else this.updateMenu();
    },
  },
};
</script>

<style lang="scss" scoped>
.edit-container {
  width: 100%;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}
.edit-image-wrapper {
  width: clamp(300px, 100%, 600px);
}
.edit-wrapper {
  width: clamp(300px, 100%, 600px);
  padding: 10px;
  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.2);
}
.menu-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  object-position: 50% center;
  cursor: pointer;
}
.menu-name,
.menu-intro {
  padding: 5px;
  margin-bottom: 5px;
}
.menu-price,
.menu-amount {
  width: 50%;
  padding: 5px;
  margin-bottom: 5px;
}

.menu-input {
  border: none;
  padding: 5px 0;
  width: 100%;
  border-bottom: 1px solid $gray400;
}
.menu-label {
  font-weight: bold;
  font-size: 18px;
  margin-bottom: 3px;
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}
.menu-price-amount {
  @include flexbox;
}
.menu-textarea {
  min-height: 100px;
  border: none;
  padding: 5px 0;
  width: 100%;
  border: 1px solid $gray400;
  resize: none;
}
#upload-button {
  display: none;
}
.submit-button {
  width: 100%;
  background-color: $gray100;
  border: none;
  transition: all 0.2s;
  &:hover {
    background-color: $purple600;
    color: white;
  }
}
</style>
