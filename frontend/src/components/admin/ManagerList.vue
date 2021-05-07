<template>
  <div style="max-width: 1080px; margin: 1px auto">
    <div class="manager-list-container">
      <div class="manager-info-container">
        <div class="side-bar">
          <ul class="name-list">
            <li v-for="(storeName, index) in storeNameArr" :key="index" class="name-item" @click="clickNameList(index)">
              <awesome icon="store" class="store"></awesome> {{ storeName }}
            </li>
          </ul>
        </div>
        <div class="manager-info-bottom">
          <div class="info-column">스토어 명:</div>
          <div class="info-data">{{ storeNameArr[order] }}</div>
          <div class="info-column">전화번호:</div>
          <div class="info-data">{{ phoneNumArr[order] }}</div>
          <div class="info-column">스토어 주소:</div>
          <div class="info-data">
            {{ nomalAddressArr[order] }} <br />
            {{ detailAddressArr[order] }}
          </div>
          <div class="info-column">사업자 번호:</div>
          <div class="info-data">{{ comResNumArr[order] }}</div>
        </div>
      </div>
      <!-- <div class="manager-copy-container" :style="{ 'background-image': require(copy.thumbnail) }"></div> -->
      <div class="manager-copy-container">
        <img :src="thumbnailArr[order]" alt="" />
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
import { getStoreEnrollmentList } from '@/api/seller';
export default {
  data() {
    return {
      storeNameArr: [],
      nomalAddressArr: [],
      detailAddressArr: [],
      phoneNumArr: [],
      comResNumArr: [],
      thumbnailArr: [],
      order: 1,
    };
  },
  created() {
    this.getstoreList();
  },
  methods: {
    async getstoreList() {
      const res = await getStoreEnrollmentList();
      const data = res.data;
      console.log(data);

      for (let i = 0; i < res.data.length; i++) {
        this.storeNameArr.push(data[i].name);
        this.nomalAddressArr.push(data[i].mainAddress);
        this.detailAddressArr.push(data[i].subAddress);
        this.phoneNumArr.push(data[i].phoneNum);
        this.comResNumArr.push(data[i].license);
        this.thumbnailArr.push(data[i].licenseImg);
      }
      console.log(this.storeNameArr);
    },
    async clickNameList(index) {
      this.order = index;
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample2';
.manager-list-container {
  width: 95%;
  padding: 3% 3% 2% 3%;
  margin: auto;
  background-color: white;
  @include flexbox;
  @include justify-content(center);
  flex-wrap: wrap;
  // align-items: stretch;
  @include box-shadow;
  @include mobile {
    width: 95%;
    padding: 2% 2% 2% 2%;
  }
  @include xs-mobile {
    width: 95%;
    padding: 2% 2% 2% 2%;
  }
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
    min-height: 70%;
    width: 100%;
    margin: 2%;
    background: black;
    border: 2px black solid;
    color: $blue600;
    font-size: 1.5em;
    // height: 300px;
    @include mobile {
      width: 100%;
      order: 1;
      min-height: 100px;
      margin: 7px;
    }
    @include xs-mobile {
      width: 100%;
      order: 1;
      min-height: 100px;
      margin: 7px;
    }
    .name-list {
      margin: 10px 5px;
    }
    .name-item {
      @include flexbox;
      // border: 1px black solid;
      margin-bottom: 5px;
      flex-wrap: wrap;
      align-items: center;
      cursor: pointer;

      &:hover {
        transform: scale(1.1) translateX(50px);
        transition: 0.5s;
      }
    }
  }
  .manager-info-container {
    @include flexbox;
    align-items: flex-start;
    border: grey 2px solid;
    width: 40%;
    // align-items: center;
    flex-grow: 1;
    flex-wrap: wrap;
    @include mobile {
      font-size: 0.6em;
      width: 100%;
      align-items: stretch;
    }
    @include xs-mobile {
      font-size: 0.6em;
      width: 100%;
      align-items: stretch;
    }
    .manager-info-bottom {
      @include flexbox;
      flex-wrap: wrap;
      width: 100%;
      @include mobile {
        order: 2;
        margin: 7px;
      }
      @include xs-mobile {
        order: 2;
        margin: 7px;
      }
      .info-column {
        flex-basis: 30%;
        text-align: left;
        padding-left: 2%;
        font-size: 1.2em;
        @include mobile {
          padding-left: 2%;
          font-size: 1.9em;
        }
        @include xs-mobile {
          padding-left: 2%;
        }
      }
      .info-data {
        flex-basis: 70%;
        font-size: 1.3em;
        @include mobile {
          margin-bottom: 0.2rem;
          font-size: 1.9em;
        }
        @include xs-mobile {
          margin-bottom: 0.2rem;
        }
      }
    }
  }
  .manager-copy-container {
    border: grey 2px solid;
    border-left: none;
    width: 50%;
    // height: 500px;
    padding: 5px;
    overflow: hidden;
    @include mobile {
      order: 2;
      width: 100%;
      border-top: none;
      border-left: grey 2px solid;
    }
    @include xs-mobile {
      order: 2;
      width: 100%;
      border-top: none;
      border-left: grey 2px solid;
    }
    // background: url('../../assets/image/사업자 등록증.jpg') no-repeat;
    // object-fit: cover; // 이미지 태그에 넣는 것 width 100% height 100%
    img {
      width: 100%;
      height: 100%;
      // height: 500px;
      object-fit: contain; // 이미지 태그에 넣는 것 width 100% height 100%
    }
  }
  .button-group {
    @include flexbox;
    justify-content: space-around;
    text-align: center;
    width: 90%;
    padding-top: 50px;
    order: 4;
    @include mobile {
      padding-top: 15px;
    }
    @include xs-mobile {
      padding-top: 15px;
    }
    .pass-button {
      display: inline-block;
      width: 30%;
      color: $white;
      background-color: $red400;
      padding: 10px 0;
      font-size: 1rem;
      cursor: pointer;
      @include shadow4;
      &:hover {
        transform: scale(1.2);
        transition: 0.4s;
      }
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
      &:hover {
        transform: scale(1.2);
        transition: 0.4s;
      }
    }
  }
}
</style>
