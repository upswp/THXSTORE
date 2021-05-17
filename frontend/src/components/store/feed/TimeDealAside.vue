<template>
  <div class="feed-aside">
    <div class="profile-wrapper">
      <img :src="getUserInfo.profileImage" />
    </div>
    <div class="current-location">
      <awesome icon="map-marker-alt"></awesome>
      {{ getUserInfo.address }} <span class="location-change">변경</span>
    </div>
    <hr />
    <div class="search-label">
      <awesome icon="search"></awesome>
      타임딜 검색 반경
    </div>
    <div class="feed-control-wrapper">
      <input id="1km" v-model="distance" type="radio" value="1" class="radio-input" />
      <label for="1km" class="radio">1km 이내</label>
      <input id="3km" v-model="distance" type="radio" value="3" class="radio-input" />
      <label for="3km" class="radio">3km 이내</label>
      <input id="5km" v-model="distance" type="radio" value="5" class="radio-input" />
      <label for="5km" class="radio">5km 이내</label>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
  data() {
    return {
      distance: 1,
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
