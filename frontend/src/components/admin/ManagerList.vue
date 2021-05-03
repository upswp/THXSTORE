<template>
  <div class="manager-list-container">
    <div class="side-bar">
      <ul class="name-list">
        <li class="name-item">🏬 {{ storeName }}</li>
        <li class="name-item">🏬 {{ storeName }}</li>
        <li class="name-item">🏬 {{ storeName }}</li>
      </ul>
    </div>
    <div class="manager-info-container">
      <div class="info-column">스토어 이름</div>
      <div class="info-data">{{ storeName }}</div>
      <div class="info-column">스토어 전화번호</div>
      <div class="info-data">{{ phoneNum }}</div>
      <div class="info-column">스토어 주소</div>
      <div class="info-data">{{ nomalAddress }}}</div>
      <div class="info-column">스토어 사업자 번호</div>
      <div class="info-data">{{ comResNum }}</div>
    </div>
    <!-- <div class="manager-copy-container" :style="'background-image : url(' + copy.thumbnail + ')'"></div> -->
    <div class="manager-copy-container">
      <img src="@/assets/image/basic_profile.jpg" alt="" />
      <!-- {{ thumbnail }} -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      storeName: '든든한끼',
      nomalAddress: '대전 유성구 어딘가',
      detailAddress: '3층',
      phoneNum: '010-9265-5275',
      comResNum: '045-24-45678',
      thumbnail: `@/assets/image/basic_profile.jpg`,
    };
  },
};
</script>

<style lang="scss" scoped>
.manager-list-container {
  width: 100%;
  max-width: 1280px;
  padding: 5% 4%;
  @include flexbox;
  @include justify-content(center);
  flex-wrap: wrap;
  align-items: stretch;
  .side-bar {
    width: 20%;
    border: hotpink 2px solid;
    min-height: 50%;
    .name-list {
      margin: 10px 5px;
    }
    .name-item {
      @include flexbox;
      border: 1px black solid;
      margin-bottom: 5px;
      flex-wrap: wrap;
      align-items: center;
      @include justify-content(center);
    }
  }
  .manager-info-container {
    @include flexbox;
    border: black 2px solid;
    width: 30%;
    align-items: center;
    flex-grow: 1;
    flex-wrap: wrap;
    .info-column {
      flex-basis: 40%;
      text-align: center;
    }
    .info-data {
      flex-basis: 60%;
      text-align: center;
    }
  }
  .manager-copy-container {
    border: indigo 2px solid;
    width: 30%;
    background-size: contain;
  }
}
</style>
