<template>
  <div class="auth-container">
    <header class="auth-header">
      <transition-group name="fade" tag="ul" class="header-wrap">
        <li v-show="loaded" key="icon"><awesome icon="store" class="store"></awesome></li>
        <li v-show="loaded" key="title"><span class="title">Thx!Store</span></li>
      </transition-group>
    </header>
    <main class="main-wrapper">
      <section class="section-wrapper">
        <transition :name="transitionName" mode="out-in"><router-view></router-view> </transition>
      </section>
    </main>
    <aside class="privacy-policy" @click="showModal = true">
      <span>Privacy Policy</span>
    </aside>
    <privacy-policy v-if="showModal" @close="showModal = false"></privacy-policy>
    <div>
      <svg
        class="waves"
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 24 150 28"
        preserveAspectRatio="none"
        shape-rendering="auto"
      >
        <defs>
          <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
        </defs>
        <g class="parallax">
          <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
          <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
          <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
          <use xlink:href="#gentle-wave" x="48" y="7" fill="rgba(255,255,255,0.6)" />
        </g>
      </svg>
    </div>
  </div>
</template>

<script>
import PrivacyPolicy from '@/components/common/PrivacyPolicy';
export default {
  components: {
    PrivacyPolicy,
  },
  data() {
    return {
      transitionDefault: 'slide-left',
      showModal: false,
      windowWidth: '',
      transitionName: '',
      loaded: false,
    };
  },
  watch: {
    windowWidth(width) {
      if (width < 780) this.transitionName = 'fade';
      else this.transitionName = this.transitionDefault;
      this.loaded = true;
    },
  },
  mounted() {
    window.addEventListener('resize', this.setWindowWidth);
    this.setWindowWidth();
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.setWindowWidth);
  },
  created() {
    this.$router.beforeEach((to, from, next) => {
      let transitionName = to.meta.transitionName || from.meta.transitionName;

      if (transitionName === 'slide') {
        const toDepth = to.path.split('/').length;
        const fromDepth = from.path.split('/').length;
        transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left';
      }

      this.transitionDefault = transitionName || 'fade';

      next();
    });
    this.kakaoLoad();
    this.facebookLoad();
  },

  methods: {
    setWindowWidth() {
      this.windowWidth = window.innerWidth;
    },
    facebookLoad() {
      this.$_Facebook.init();
    },
    async kakaoLoad() {
      try {
        await this.$loadScript(`https://developers.kakao.com/sdk/js/kakao.js`);
        if (!window.Kakao.isInitialized()) {
          this.$_Kakao.init();
        }
      } catch (error) {
        console.log(error);
        alert('카카오 API 키를 다시 한번 확인해주세요');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@include fade-transition(5s);
.auth-container {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(90deg, #69b7eb, #cbcfce, #70bfec);
  background-size: 100% 100%;

  z-index: -1;
  &::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    min-height: 100vh;
    z-index: 0;
    opacity: 0;
    @include transition(opacity 1s);
    background: $blue400;
    @include mobile() {
      & {
        opacity: 1;
      }
    }
    @include xs-mobile() {
      & {
        opacity: 1;
      }
    }
  }
}
.auth-header {
  font-family: 'Pacifico', cursive;

  // color: $yellow800;
  @include cross-middle;
  font-size: 2rem;
}
.header-wrap {
  position: relative;
  min-height: 100px;
  padding: 32px 0;
  color: #00a1ff;
  @include flexbox;
  @include transition(color 1s);
  @include mobile() {
    color: white;
  }
  @include xs-mobile() {
    color: white;
  }
}
.store {
  font-size: 2rem;
  padding-right: 0.3rem;
}
.main-wrapper {
  @include slide-left-transition(0.4s);
  @include slide-right-transition(0.4s);
  @include mobile() {
    @include fade-transition(0.5s);
    @include fade-transition(0.5s);
  }
  @include xs-mobile() {
    @include fade-transition(0.5s);
    @include fade-transition(0.5s);
  }
}
.section-wrapper {
  @include flexbox;
  @include justify-content(center);
  margin-bottom: 40px;
}
.privacy-policy {
  z-index: 1;
  position: relative;
  @include flexbox;
  @include justify-content(center);
  span {
    font-weight: 600;
    color: $gray600;
    cursor: pointer;
    margin-bottom: 20px;
    @include transition(color 0.5s);
    &:hover {
      color: black;
    }
  }
}
.waves {
  position: fixed;
  bottom: 0px;
  width: 100%;
  z-index: 0;
}
.parallax > use {
  animation: move-forever 50s cubic-bezier(0.55, 0.5, 0.45, 0.5) infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 27s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 30s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 33s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 40s;
}
@keyframes move-forever {
  0% {
    transform: translate3d(-90px, 0, 0);
  }
  100% {
    transform: translate3d(85px, 0, 0);
  }
}
</style>
