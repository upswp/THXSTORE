<template>
  <div class="userstore-info-container">
    <div class="userstore-summury">간략 정보</div>
    <br />
    <div class="userstore-introduce card-shadow">
      <div class="introduce-title">가게 소개</div>
      <div class="introduce-content">
        {{ storeInfo.sideInfo.introduce }}
      </div>
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
        <KaKao-map :location="locationArr"></KaKao-map>
      </div>
      <div id="foo" class="kakaomap-copy-button">주소 복사</div>
      <div class="kakaomap-navigation-button" data-clipboard-target="asdfsadfsd">길찾기</div>
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
<script>
import KaKaoMap from '@/components/storeForUser/KakaoMapToShow2Marker';
import { getStoreInfo } from '@/api/userStore';

export default {
  components: {
    KaKaoMap,
  },
  data() {
    return {
      locationArr: [
        { storeAddr: '대전 유성구 원내동79-15 번지', storeLat: 30, storeLon: 170 },
        { userAddr: '대전 유성구 진잠로92번길 33', userLat: 34, userLon: 174 },
      ],
      storeInfo: [],
    };
  },
  created() {
    this.getUserStoreInfo();
  },
  methods: {
    async getUserStoreInfo() {
      console.log(this.$route.params.storeId);
      const paramsId = this.$route.params.storeId;
      const { data } = await getStoreInfo(paramsId);
      this.storeInfo = data;
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
      line-break: normal;
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
      width: 50%;
      background-color: $gray400;
      text-align: center;
      border: $gray200 2px solid;
    }
    .kakaomap-navigation-button {
      display: inline-block;
      width: 50%;
      background-color: $blue400;
      text-align: center;
      border: $gray200 2px solid;
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
