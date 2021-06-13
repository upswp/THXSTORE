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
            휴무일 <span class="content-info font-red">{{ holidays }}</span>
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
        <Kakao-map :location="location"></Kakao-map>
      </div>
      <div ref="mainAddress" class="kakaomap-copy-button">{{ baseInfo.mainAddress }}</div>
      <div class="kakaomap-navigation-button" @click="copyAddress">주소 복사하기</div>
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
<script>
import KakaoMap from '@/components/storeForUser/KakaoMapForUser';
import { mapMutations, mapGetters } from 'vuex';
import { holidayStrConvert } from '@/utils/filters';
export default {
  components: {
    KakaoMap,
  },
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
  data() {
    return {
      location: {
        store: '',
        user: '',
      },
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
    holidays() {
      return holidayStrConvert(this.sideInfo.closeDay);
    },
  },
  created() {
    this.setSpinnerState(false);
    this.location.user = this.getUserInfo.address;
    this.location.store = this.baseInfo.mainAddress;
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    copyAddress() {
      navigator.clipboard.writeText(this.$refs.mainAddress.innerText);
      alert('스토어 주소 복사 완료');
    },
  },
};
</script>
<style scoped lang="scss">
@import '@/assets/scss/sample';
</style>
