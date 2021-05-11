<template>
  <div class="store-product-container">
    <group-list></group-list>
    <group-detail v-show="!editable" @toggleEdit="toggleEdit"></group-detail>
    <group-edit v-show="editable" :menu="menu" @toggleEdit="toggleEdit"></group-edit>
  </div>
</template>

<script>
import GroupList from '@/components/store/product/GroupList';
import GroupDetail from '@/components/store/product/GroupDetail';
import GroupEdit from '@/components/store/product/GroupEdit';
export default {
  components: {
    GroupList,
    GroupDetail,
    GroupEdit,
  },
  data() {
    return {
      editable: false,
      menu: '',
    };
  },
  methods: {
    toggleEdit(e) {
      if (e) this.menu = e;
      else this.menu = '';
      this.editable = !this.editable;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-product-container {
  background-color: white;
  padding-top: 10px;
  width: 100%;

  @include lg-pc {
    @include flexbox;
    @include justify-content(center);
  }
  @include pc {
  }
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}
</style>
