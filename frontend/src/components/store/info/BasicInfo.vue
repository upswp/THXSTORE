<template>
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
.store-content-wrapper {
  border: 1px solid $gray200;
  // border-top: 10px solid #2571b9;
  min-height: 100px;
  position: relative;
  margin: -15px 12px 9px;
  background-color: white;

  &::before {
    position: absolute;
    top: -1px;
    right: -1px;
    left: -1px;
    height: 7px;
    background: $blue300;
    content: '';
  }
  @include lg-pc {
    padding: 20px 10px;
  }
  @include pc {
    padding: 20px 5px;
  }
  @include mobile {
    padding: 10px 5px;
  }
  @include xs-mobile {
    padding: 10px 5px;
  }
}
.store-header {
  @include flexbox;
  @include align-items(center);
  margin-bottom: 10px;
}
.store-logo {
  $length: clamp(80px, 12vw, 150px);
  width: $length;
  height: $length;
  object-fit: cover;
  object-position: center 50%;
  margin-right: 10px;
}
.store-title {
  width: calc(100% - clamp(80px, 12vw, 150px));
  font-size: 30px;
  @include pc {
    font-size: 26px;
  }
  @include mobile {
    font-size: 22px;
  }
  @include xs-mobile {
    font-size: 20px;
  }
  font-weight: 800;
}
.essential-info-container {
  padding: 0 10px;
}
.essential-title {
  margin-bottom: 10px;
  font-weight: 600;
  font-size: 22px;
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 14px;
    width: 100%;
  }
}
.correct-icon {
  color: $gray600;
  cursor: pointer;
  &:hover {
    color: $blue800;
  }
}
.essential-items {
  width: 100%;
  @include flexbox;
  @include align-items(center);
  flex-wrap: wrap;
}
.map-container {
  @include justify-content(flex-start);
}
.essential-item {
  width: 50%;
  &.one-line {
    width: 100%;
  }
  margin-bottom: 10px;
  font-size: 12px;
  @include flexbox;
  // @include justify-content(center);
  @include align-items(center);
  @include lg-pc {
    font-size: 16px;
  }
  @include xs-mobile {
    width: 100%;
  }
}
.icon {
  width: 20px;
  margin-right: 5px;
  color: $gray800;
}
</style>
