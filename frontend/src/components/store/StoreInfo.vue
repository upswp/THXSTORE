<template>
  <div class="store-info-container">
    <img class="store-thumbnail-image" src="@/assets/image/thumbnail_example.jpg" />
    <section class="store-content-wrapper">
      <header class="store-header">
        <img class="store-logo" src="@/assets/image/logo.jpg" />
        <div class="store-title">더스토어 계산점</div>
      </header>
      <article class="essential-info-container">
        <div class="essential-title">
          ※ 기본 정보
          <awesome icon="pen-square" class="correct-icon"></awesome>
        </div>
        <div class="essential-items">
          <div class="essential-item">
            <awesome icon="id-card" class="icon"></awesome>
            사업자등록번호 111-11-111111
          </div>
          <div class="essential-item">
            <awesome icon="phone-alt" class="icon"></awesome>
            042-1234-5678
          </div>
          <div class="essential-item">
            <awesome icon="map-marker-alt" class="icon"></awesome>
            대전 유성구 문화원로 140
          </div>
          <kakao-map :location="location"></kakao-map>
        </div>
      </article>
    </section>
    <section class="additional-info-container">
      <div class="additional-title">※ 추가 정보</div>
      <div class="additional-items">
        <div class="additional-item">
          <div class="additional-item">
            <div class="additional-item-label">카테고리</div>
            <div>
              <select class="store-category">
                <option value="chineses">중식</option>
                <option value="japanese">일식</option>
              </select>
            </div>
          </div>
          <div class="additional-item-label">영업시간</div>
          <div class="time">
            <div class="toggleWrapper">
              <input id="dn" type="checkbox" class="toggle-switch" />
              <label for="dn" class="toggle"><div class="toggle__handler"></div></label>
            </div>
            <select class="time-select">
              <option v-for="(i, index) in 12" :key="index" :value="i - 1">{{ i - 1 === 0 ? '00' : i - 1 }}</option>
            </select>
            <span>:</span>
            <select class="time-select">
              <option v-for="(i, index) in 6" :key="index" :value="10 * (i - 1)">
                {{ i - 1 === 0 ? '00' : 10 * (i - 1) }}
              </option>
            </select>
          </div>
        </div>
        <div class="additional-item">
          <div class="additional-item-label">공휴일</div>
          <div>
            <input type="text" placeholder="영업시간" />
          </div>
        </div>

        <div class="additional-item">
          <div class="additional-item-label">가게 소개</div>
          <div>
            <textarea type="text" placeholder="가게 소개" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import KakaoMap from '@/components/common/KakaoMap';
export default {
  components: {
    KakaoMap,
  },
  data() {
    return {
      location: '대전 유성구 문화원로 140',
    };
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
