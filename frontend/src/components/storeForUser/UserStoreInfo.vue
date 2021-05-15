<template>
  <div class="userstore-info-container">
    <div class="userstore-summury">간략 정보</div>
    <br />
    <div class="userstore-introduce card-shadow">
      <div class="introduce-title">가게 소개</div>
      <pre class="introduce-content">
        {{ storeInfo.sideInfo.introduce }}
      </pre>
    </div>
    <br />
    <div class="userstore-operation card-shadow">
      <div class="operation-title">영업 정보</div>
      <div class="operation-content">
        <div class="content-label">운영시간</div>
        <div class="content-info">{{ storeInfo.sideInfo.openTime }}</div>
        <div class="content-label">휴무일</div>
        <div class="content-info">{{ storeInfo.sideInfo.closeDay }}</div>
        <div class="content-label">전화번호</div>
        <div class="content-info">{{ storeInfo.baseInfo.phoneNum }}</div>
      </div>
    </div>
    <br />
    <div class="userstore-kakaomap card-shadow">
      <div class="kakaomap-title">찾아오시는 길</div>
      <div class="kakaomap-content">
        <KaKao-map v-if="loaded" :location="locationArr"></KaKao-map>
      </div>
      <input class="kakaomap-copy-button" :value="storeInfo.baseInfo.mainAddress" readonly />
      <div class="kakaomap-navigation-button" @click="clipboard">주소 복사하기</div>
    </div>
    <br />
    <div class="userstore-license card-shadow">
      <div class="license-title">사업자 정보</div>
      <div class="license-content">
        <div class="license-label">상호명</div>
        <div class="license-info">
          {{ storeInfo.baseInfo.name }}
        </div>
        <div class="license-label">사업자주소</div>
        <div class="license-info">
          {{ storeInfo.baseInfo.mainAddress }} <br />
          {{ storeInfo.baseInfo.subAddress }}
        </div>
        <div class="license-label">사업자번호</div>
        <div class="license-info">{{ storeInfo.baseInfo.license }}</div>
      </div>
    </div>
  </div>
</template>
<script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.7.1/clipboard.min.js"></script>
<script>
import KaKaoMap from '@/components/storeForUser/KakaoMapToShow2Marker';
import { getStoreInfo } from '@/api/userStore';
import { mapMutations, mapGetters } from 'vuex';
export default {
  components: {
    KaKaoMap,
  },
  data() {
    return {
      storeInfo: [],
      locationArr: [{ storeAddr: '' }, { userAddr: '대전 유성구 진잠로92번길 33' }],
      loaded: false,
    };
  },
  computed: {
    ...mapGetters(['getWatchedStore']),
    // getWatchedStroe() {
    //   return this.$store.state.watechedstore;
    // },
  },
  created() {
    this.storeInfo = this.getWatchedStore;
    this.locationArr[0].storeAddr = this.storeInfo.baseInfo.mainAddress;
    this.loaded = true;
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    clipboard(e) {
      e.target.previousSibling.select();
      document.execCommand('copy');
      alert('스토어 주소 복사 완료');
    },
    async getUserStoreInfo() {
      this.storeInfo = this.$store.state.watchedStore;
      this.locationArr[0].storeAddr = this.storeInfo.baseInfo.mainAddress;
      console.log('스토어주소', this.locationArr[0].storeAddr);

      console.log(this.storeInfo);
      this.setSpinnerState(false);
    },
  },
};
</script>
<style scoped lang="scss">
// @import '@/assets/scss/sample2';
// @import '@/assets/scss/sample2';

.userstore-info-container {
  margin-bottom: 20px;
  width: 100%;
  .card-shadow {
    @include shadow1;
    padding: 2%;
    border-radius: 5%;
  }
  .userstore-introduce {
    @include flexbox;
    flex-wrap: wrap;
    .introduce-title {
      margin-bottom: 10px;
      width: 100%;
      font-weight: $bold;
      @include xs-mobile {
        font-size: 13px;
      }
    }
    .introduce-content {
      font-family: S-CoreDream-4Regular;
      white-space: pre-wrap;
      line-break: normal;
      font-size: 14px;
      @include xs-mobile {
        font-size: 12px;
      }
    }
  }
  .userstore-operation {
    .operation-title {
      width: 100%;
      margin-bottom: 10px;
      font-weight: $bold;
      @include xs-mobile {
        font-size: 13px;
      }
    }
    .operation-content {
      @include flexbox;
      flex-wrap: wrap;
      width: 100%;
      .content-label {
        flex-basis: 30%;
        @include xs-mobile {
          font-size: 12px;
        }
      }
      .content-info {
        width: 70%;
      }
    }
  }
  .userstore-kakaomap {
    .kakaomap-title {
      width: 100%;
      margin-bottom: 10px;
      font-weight: $bold;
      @include xs-mobile {
        font-size: 13px;
      }
    }
    .kakaomap-content {
      border: $gray200 2px solid;
      @include xs-mobile {
        font-size: 12px;
      }
    }
    .kakaomap-copy-button {
      display: inline-block;
      width: 70%;
      border: none;
      background-color: $gray400;
      text-align: center;
      border: $gray200 2px solid;
    }
    .kakaomap-navigation-button {
      display: inline-block;
      color: white;
      width: 30%;
      padding: 1px 2px;
      background-color: $blue400;
      text-align: center;
      border: $gray200 2px solid;
      &:hover {
        background-color: $blue600;
        cursor: pointer;
      }
    }
  }
  .userstore-license {
    .license-title {
      width: 100%;
      margin-bottom: 10px;
      font-weight: $bold;
      @include xs-mobile {
        font-size: 13px;
      }
    }
    .license-content {
      @include flexbox;
      flex-wrap: wrap;
      .license-label {
        flex-basis: 30%;
        @include xs-mobile {
          font-size: 12px;
        }
      }
      .license-info {
        flex-basis: 70%;
        @include xs-mobile {
          font-size: 12px;
        }
      }
    }
  }
}
</style>
