<template>
  <main class="store-product-detail">
    <div class="group-name-wrapper">
      <div class="group-name">Group1</div>
      <div class="menu-add-button" @click="editOn(false)">새 메뉴 추가하기</div>
    </div>
    <div class="menu-container">
      <div v-for="(menu, index) in menus" :key="index" class="menu-wrapper">
        <div class="item-image">
          <img :src="menu.productImg" class="image" />
        </div>
        <div class="item-info">
          <div class="info-header">
            <div class="setting-button" @click="toggleEditButton(menu)">
              <span></span>
              <span></span>
              <span></span>
            </div>
            <div v-show="menu.editting" class="setting-wrapper">
              <div class="menu-update" @click="editOn(menu)">수정하기</div>
              <div class="menu-delete" @click="deleteItem(menu.productId)">삭제하기</div>
            </div>
            <div class="item-name">{{ menu.name }}</div>
            <div class="item-intro">{{ menu.introduce }}</div>
          </div>
          <div class="info-footer">
            <div class="item-price">가격: {{ menu.price }}</div>
            <div class="item-unit">판매단위: {{ menu.amount }}</div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import { getMenuListByGroup, deleteMenu } from '@/api/menu';
import { mapMutations } from 'vuex';
export default {
  props: {
    groupId: {
      type: Number,
      default: -1,
      require: true,
    },
  },
  data() {
    return {
      menus: [],
    };
  },
  watch: {
    async groupId(newValue) {
      await this.loadMenuList(newValue);
    },
  },
  async created() {
    if (this.groupId === -1) return;
    await this.loadMenuList(this.groupId);
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    editOn(menu) {
      if (menu) this.$emit('toggleEdit', menu);
      else this.$emit('toggleEdit', false);
    },
    toggleEditButton(menu) {
      menu.editting = !menu.editting;
    },
    async deleteItem(productId) {
      try {
        this.setSpinnerState(true);
        await deleteMenu(productId);
        await this.loadMenuList(this.groupId);
      } catch (error) {
        console.log(error);
        alert('메뉴 삭제가 실패했습니다.');
      } finally {
        this.setSpinnerState(false);
      }
    },
    async loadMenuList(groupId) {
      try {
        this.setSpinnerState(true);
        const { data } = await getMenuListByGroup(groupId);
        this.menus = data.map(x =>
          Object.assign(
            {
              editting: false,
            },
            x,
          ),
        );
        return data;
      } catch (error) {
        console.log(error);
        alert('그룹 별 메뉴 조회에 실패했습니다.');
      } finally {
        this.setSpinnerState(false);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.store-product-detail {
  padding: 10px;

  width: calc(100% - 200px);
  height: calc(100vh - 80px);

  @include lg-pc {
    overflow: auto;
  }
  @include pc {
    width: 100%;
    padding: 10px 0;
  }

  @include mobile {
    width: 100%;
    height: 100vh;
    padding: 10px 0;
  }
  @include xs-mobile {
    width: 100%;
    height: 100vh;
    padding: 10px 0;
  }
}
.menu-container {
  width: 100%;
  box-sizing: border-box;
  @include lg-pc {
    @include flexbox;
    @include flex-wrap(wrap);
  }
  @include mobile {
    @include flexbox;
    @include flex-wrap(wrap);
  }
}

.menu-wrapper {
  margin: 10px 0;
  padding: 5px;
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);

  min-height: 200px;
  @include lg-pc {
    width: 33%;
  }
  @include pc {
    width: 100%;
  }
  @include mobile {
    width: 50%;
  }
  @include xs-mobile {
    width: 100%;
  }
}
.item-image {
  width: 100%;
  height: 250px;
  @include mobile {
    height: 225px;
  }
  @include xs-mobile {
    height: 200px;
  }
}
.item-info {
  width: 100%;
}
.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center 50%;
}
.info-header {
  padding: 10px;
  margin: 0 10px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.4);
  border-radius: 10px;
  position: relative;
  top: -20px;
  background-color: white;
}
.item-name {
  text-align: center;
  font-weight: bold;
  margin: 10px;
  @include lg-pc {
    font-size: 20px;
  }
  @include pc {
    font-size: 18px;
  }
  @include xs-mobile {
    font-size: 14px;
  }
}
.item-intro {
  display: inline;
  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: $red100;
    margin-right: 5px;
    margin-bottom: 1px;
  }
}
.info-footer {
  @include flexbox;
  padding: 0 20px;
  margin-bottom: 10px;
}
.item-price {
  width: 50%;

  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: $red100;
    margin-right: 5px;
    margin-bottom: 1px;
  }
}
.item-unit {
  width: 50%;
  &::before {
    content: '';
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: $red100;
    margin-right: 5px;
    margin-bottom: 1px;
  }
}
.setting-button {
  cursor: pointer;
  position: absolute;
  top: -2px;
  right: 10px;
  font-size: 23px;
  span {
    display: inline-block;
    width: 6px;
    height: 6px;
    background-color: $gray600;
    border-radius: 50%;
    &:nth-child(1) {
      margin-right: 5px;
    }
    &:nth-child(2) {
      margin-right: 5px;
    }
    @include mobile {
      width: 5px;
      height: 5px;
    }
    @include xs-mobile {
      width: 4px;
      height: 4px;
    }
  }
}
.group-name {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 16px;
  }
  &::before {
    content: '📃';
    font-size: 18px;
    @include pc {
      font-size: 16px;
    }
    @include mobile {
      font-size: 14px;
    }
    @include xs-mobile {
      font-size: 14px;
    }
  }
}
.group-name-wrapper {
  @include flexbox;
  @include justify-content(space-between);
  @include align-items(center);
}
.menu-add-button {
  padding: 5px;
  background-color: $red100;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.2s;
  &:hover {
    background-color: $red400;
    color: white;
  }
}
.setting-wrapper {
  position: absolute;
  top: 30px;
  right: 10px;
  border-radius: 5px;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
  // top:-10px;
  // right:0px;
  display: inline-block;
  background-color: white;
}
.menu-update,
.menu-delete {
  padding: 10px;
  cursor: pointer;
  &:hover {
    background-color: $gray100;
  }
}
</style>
