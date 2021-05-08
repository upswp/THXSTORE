<template>
  <div class="store-info-container">
    <img class="store-thumbnail-image" src="@/assets/image/thumbnail_example.jpg" />
    <basic-info></basic-info>
    <additional-info></additional-info>
  </div>
</template>

<script>
import BasicInfo from '@/components/store/info/BasicInfo';
import AdditionalInfo from '@/components/store/info/AdditionalInfo';
export default {
  components: {
    BasicInfo,
    AdditionalInfo,
  },
};
</script>

<style lang="scss" scoped>
.store-info-container {
  width: 80%;
  border: 1px solid $gray200;
  @include mobile {
    width: 100%;
  }
  @include xs-mobile {
    width: 100%;
  }
}
.store-thumbnail-image {
  width: 100%;
  height: 10vw;
  object-fit: cover;
  object-position: center 50%;
}
</style>
