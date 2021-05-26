<template>
  <div class="feed-aside">
    <div class="profile-wrapper">
      <img :src="getUserInfo.profileImage" class="profile-image" />
    </div>
    <div class="current-location">
      <awesome icon="map-marker-alt"></awesome>
      {{ getUserInfo.address }} <span class="location-change" @click="$router.push({ name: 'user' })">변경</span>
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
      distance: 5,
    };
  },

  computed: {
    ...mapGetters(['getUserInfo']),
  },
  watch: {
    distance(newValue) {
      this.$emit('newDistance', newValue);
    },
  },
};
</script>

<style lang="scss" scoped>
.feed-aside {
  background-color: $gray100;
  border-radius: 5px;
  border: 1px solid $gray400;
  padding: 10px;
  margin: 10px;
  text-align: center;
  height: fit-content;
  @include lg-pc {
    font-size: 14px;
    width: 200px;
  }
  @include pc {
    font-size: 13px;
    width: 200px;
  }
  @include mobile {
    font-size: 12px;
    width: 100%;
    margin: 0;
    border-radius: 0;
    border: none;
  }
  @include xs-mobile {
    font-size: 11px;
    width: 100%;
    margin: 0;
    border-radius: 0;
    border: none;
  }
}
.profile-wrapper {
  @include flexbox;
  @include justify-content(center);
  margin-bottom: 5px;
  img {
    padding: 5px;
    // background-color: $red100;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
    object-position: center 50%;
  }
  @include mobile {
    display: none;
  }
  @include xs-mobile {
    display: none;
  }
}
hr {
  @include mobile {
    display: none;
  }
  @include xs-mobile {
    display: none;
  }
}
.greeting-ment {
  @include mobile {
    display: none;
  }
  @include xs-mobile {
    display: none;
  }
  margin-bottom: 3px;
}
.radio-input {
  display: none;
  &:checked + .radio::before {
    content: '\2713';
    font-weight: bold;
    background-color: $blue600;
    color: white;
    border: none;
  }
}
.radio {
  cursor: pointer;
  @include flexbox;
  @include align-items(center);
  margin-bottom: 5px;
  margin-right: 5px;
  &::before {
    display: inline-block;
    margin-right: 3px;
    width: 20px;
    height: 20px;
    border: 1px solid $gray600;
    content: '';
    cursor: pointer;
    border-radius: 3px;
    @include flexbox;
    @include justify-content(center);
    @include align-items(center);
  }
}
.feed-control-wrapper {
  padding: 10px 10px 0 10px;
  @include mobile {
    @include flexbox;
  }
  @include xs-mobile {
    @include flexbox;
  }
}
.search-label {
  text-align: left;
  padding: 10px 10px 0;
}
.location-change {
  margin-top: 5px;
  display: inline-block;
  padding: 1px 4px;
  border: 1px solid $gray400;
  border-radius: 10px;
  vertical-align: baseline;
  cursor: pointer;
  &:hover {
    background-color: $gray600;
    color: white;
  }
}
</style>
