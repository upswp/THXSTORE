<template>
  <div class="store-product-container">
    <group-list></group-list>
    <group-detail></group-detail>
  </div>
</template>

<script>
import GroupList from '@/components/store/product/GroupList';
import GroupDetail from '@/components/store/product/GroupDetail';
export default {
  components: {
    GroupList,
    GroupDetail,
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
