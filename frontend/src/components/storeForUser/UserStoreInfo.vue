<template>
  <div class="userstore-info-container">
    <div class="userstore-info-middle card-shadow">
      <div class="userstore-introduce">
        <div class="introduce-title">가게 소개</div>
        <pre class="introduce-content">
        {{ sideInfo.introduce }}
      </pre
        >
      </div>
      <div class="userstore-operation">
        <div class="operation-title">영업 정보</div>
        <div class="operation-content">
          <div class="content-label">
            <span class="label-icon">
              <awesome :icon="['far', 'clock']"></awesome>
            </span>
            운영시간 <span class="content-info">{{ sideInfo.openTime }} ~ {{ sideInfo.closeTime }}</span>
          </div>
          <div class="content-label">
            <span class="label-icon">
              <awesome icon="calendar-alt"></awesome>
            </span>
            휴무일 <span class="content-info font-red">{{ sideInfo.closeDay }}</span>
          </div>

          <div class="content-label">
            <span class="label-icon">
              <awesome icon="phone-alt"></awesome>
            </span>
            전화번호
            <span class="content-info">{{ baseInfo.phoneNum }}</span>
          </div>
        </div>
      </div>
    </div>
    <br />
    <div class="userstore-kakaomap card-shadow">
      <div class="kakaomap-title">찾아오시는 길</div>
      <div class="kakaomap-content">
        <KaKao-map v-if="loaded" :location="locationArr"></KaKao-map>
      </div>
      <input class="kakaomap-copy-button" :value="baseInfo.mainAddress" readonly />
      <div class="kakaomap-navigation-button" @click="clipboard">주소 복사하기</div>
    </div>
    <br />
    <div class="userstore-license card-shadow">
      <div class="license-title">사업자 정보</div>
      <div class="license-content">
        <div class="license-label">상호명</div>
        <div class="license-info">
          {{ baseInfo.name }}
        </div>
        <div class="license-label">사업자주소</div>
        <div class="license-info">
          {{ baseInfo.mainAddress }} <br />
          {{ baseInfo.subAddress }}
        </div>
        <div class="license-label">사업자번호</div>
        <div class="license-info">{{ baseInfo.license }}</div>
      </div>
    </div>
  </div>
</template>
<script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.7.1/clipboard.min.js"></script>
<script>
import KaKaoMap from '@/components/storeForUser/KakaoMapToShow2Marker';
import { getStoreInfo } from '@/api/userStore';
import { mapMutations } from 'vuex';
export default {
  props: {
    baseInfo: {
      type: Object,
      default: () => {},
      require: true,
    },
    sideInfo: {
      type: Object,
      default: () => {},
      require: true,
    },
  },
  components: {
    KaKaoMap,
  },
  data() {
    return {
      locationArr: [{ storeAddr: '' }, { userAddr: '대전 유성구 진잠로92번길 33' }],
      loaded: false,
    };
  },
  created() {
    this.changeCloseDayToKor();
    this.locationArr[0].storeAddr = this.baseInfo.mainAddress;
    this.loaded = true;
  },
  methods: {
    changeCloseDayToKor() {
      if (!this.sideInfo.closeDay) return '없음';
      this.sideInfo.closeDay = this.sideInfo.closeDay
        .replace('MON', '월요일')
        .replace('TUE', '화요일')
        .replace('WED', '수요일')
        .replace('THU', '목요일')
        .replace('FRI', '금요일')
        .replace('SAT', '토요일')
        .replace('SUN', '일요일')
        .replace('|', ', ');
    },
    ...mapMutations(['setSpinnerState']),
    clipboard(e) {
      e.target.previousSibling.select();
      document.execCommand('copy');
      alert('스토어 주소 복사 완료');
    },
  },
};
</script>
<style scoped lang="scss">
// @import '@/assets/scss/sample2';
// @import '@/assets/scss/sample2';
// @import '@/assets/scss/sample';
.userstore-info-container {
  margin-bottom: 20px;
  width: 100%;
}
.card-shadow {
  @include shadow1;
  border-radius: 5px;
}
.userstore-introduce {
  flex-basis: 50%;
  padding: 10px;
  border: 1px solid $gray200;
  @include flexbox;
  flex-wrap: wrap;
}
.operation-title {
  width: 100%;
  margin-bottom: 10px;
  font-weight: $bold;
  @include xs-mobile {
    font-size: 13px;
  }
}
.userstore-info-middle {
  @include flexbox;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 15px;
  }
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 13px;
  }
}
.userstore-operation {
  flex-basis: 50%;
  border: 1px solid $gray200;
  padding: 10px;
  border-left: 0px;
}
.operation-content {
  width: 100%;
}
.content-label {
  margin-bottom: 3px;
}
.content-info {
  display: inline-block;
  &.font-red {
    color: $red600;
    font-weight: 600;
  }
}
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
}
.introduce-title {
  margin-bottom: 10px;
  width: 100%;
  font-weight: $bold;
  @include xs-mobile {
    font-size: 13px;
  }
}
.introduce-content {
  text-indent: 0em;
  font-family: S-CoreDream-4Regular;
  white-space: pre-line;
  line-break: normal;
  font-size: 14px;
  @include xs-mobile {
    font-size: 12px;
  }
}
.license-label {
  margin-bottom: 3px;
  flex-basis: 30%;
  @include xs-mobile {
    font-size: 12px;
  }
}
.license-info {
  flex-basis: 70%;
  margin-bottom: 3px;
  @include xs-mobile {
    font-size: 12px;
  }
}
.label-icon {
  display: inline-block;
  width: 16px;
}
svg {
  color: $gray600;
}
</style>
