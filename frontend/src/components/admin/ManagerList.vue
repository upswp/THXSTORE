<template>
  <div style="max-width: 900px; margin: auto">
    <div class="manager-list-container">
      <div id="nameColumn">가게명</div>
      <div id="infoColumn">정보</div>
      <div id="infoColumn">사업자등록증</div>
      <div class="side-bar">
        <ul class="name-list">
          <li class="name-item" @click="clickNameList">🏬 {{ storeName }}</li>
          <li class="name-item">🏬 {{ storeName }}</li>
          <li class="name-item">🏬 {{ storeName }}</li>
        </ul>
      </div>
      <div class="manager-info-container">
        <div class="info-column">스토어 명:</div>
        <div class="info-data">{{ storeName }}</div>
        <div class="info-column">전화번호:</div>
        <div class="info-data">{{ phoneNum }}</div>
        <div class="info-column">스토어 주소:</div>
        <div class="info-data">
          {{ nomalAddress }} <br />
          {{ nomalAddress }}
        </div>
        <div class="info-column">사업자 번호:</div>
        <div class="info-data">{{ comResNum }}</div>
      </div>
      <!-- <div class="manager-copy-container" :style="{ 'background-image': require(copy.thumbnail) }"></div> -->
      <div class="manager-copy-container">
        <img src="@/assets/image/사업자 등록증.jpg" alt="" />
        <!-- src v-bind쓸 때  -->
        <!-- {{ thumbnail }} -->
      </div>
      <div class="button-group">
        <div class="pass-button">반려</div>
        <div class="fail-button">승인</div>
      </div>
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
  methods: {
    clickNameList() {
      console.log('된다.');
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample2';
.manager-list-container {
  width: 100%;
  padding: 5% 4%;
  margin: auto;
  @include flexbox;
  @include justify-content(center);
  flex-wrap: wrap;
  align-items: stretch;
  #nameColumn {
    flex-basis: 19%;
    text-align: center;
  }
  #infoColumn {
    flex-basis: 40.5%;
    text-align: center;
  }

  .side-bar {
    overflow-y: auto;
    overflow-x: hidden;
    width: 18%;
    background: $gray700;
    color: white;
    margin-right: 1%;
    min-height: 50%;
    @include xs-mobile {
      width: 50%;
      order: 1;
    }
    .name-list {
      margin: 10px 5px;
    }
    .name-item {
      @include flexbox;
      @include justify-content(center);
      // border: 1px black solid;
      margin-bottom: 5px;
      flex-wrap: wrap;
      align-items: center;
      cursor: pointer;

      &:hover {
        transform: scale(1.1);
      }
    }
  }
  .manager-info-container {
    @include flexbox;
    border: black 2px solid;
    width: 40%;
    align-items: center;
    flex-grow: 1;
    flex-wrap: wrap;
    @include xs-mobile {
      font-size: 0.7em;
      order: 3;
    }
    .info-column {
      flex-basis: 30%;
      text-align: left;
      padding-left: 2%;
      @include xs-mobile {
        text-align: center;
      }
    }
    .info-data {
      flex-basis: 60%;
      text-align: left;
      @include xs-mobile {
        text-align: center;
        margin-bottom: 0.2rem;
      }
    }
  }
  .manager-copy-container {
    border: indigo 2px solid;
    width: 40%;
    // background: url('../../assets/image/사업자 등록증.jpg') no-repeat;
    // object-fit: cover; // 이미지 태그에 넣는 것 width 100% height 100%
    background-size: contain;
    .img {
      object-fit: cover; // 이미지 태그에 넣는 것 width 100% height 100%
      height: 400px;
    }
    @include xs-mobile {
      order: 2;
      width: 49%;
    }
  }
  .button-group {
    @include flexbox;
    justify-content: space-around;
    text-align: center;
    width: 90%;
    padding-top: 20px;
    order: 4;
    .pass-button {
      display: inline-block;
      width: 30%;
      color: $white;
      background-color: $red400;
      padding: 10px 0;
      font-size: 1rem;
      cursor: pointer;
      @include shadow4;
      margin-bottom: 30px;
    }
    .fail-button {
      display: inline-block;
      width: 30%;
      color: $white;
      border: none;
      background-color: $green400;
      padding: 10px 0;
      font-size: 16px;
      cursor: pointer;
      @include box-shadow;
      margin-bottom: 30px;
    }
  }
}
</style>
