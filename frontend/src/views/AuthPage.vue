<template>
  <div class="auth-container">
    <header>
      <ul class="header-wrap">
        <li><awesome icon="store" class="store"></awesome></li>
        <li><span class="title">Thx!Store</span></li>
      </ul>
    </header>
    <div class="main-wrap">
      <main>
        <transition :name="transitionName" mode="out-in"><router-view></router-view> </transition>
      </main>
      <aside>
        <div class="privacy-policy">Privacy Policy</div>
      </aside>
    </div>
    <Footer />
  </div>
</template>

<script>
const DEFAULT_TRANSITION = 'fade';
export default {
  data() {
    return {
      transitionName: DEFAULT_TRANSITION,
    };
  },
  created() {
    this.$router.beforeEach((to, from, next) => {
      let transitionName = to.meta.transitionName || from.meta.transitionName;

      if (transitionName === 'slide') {
        const toDepth = to.path.split('/').length;
        const fromDepth = from.path.split('/').length;
        transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left';
      }

      this.transitionName = transitionName || DEFAULT_TRANSITION;

      next();
    });
    this.kakaoLoad();
    this.facebookLoad();
  },
  mounted() {
    this.googleLoad();
  },
  methods: {
    facebookLoad() {
      this.$_Facebook.init();
    },
    async googleLoad() {
      try {
        await this.$loadScript(`https://apis.google.com/js/api:client.js`);
        this.$_Google.init();
      } catch (error) {
        console.log(error);
        alert('구글 클라이언트 API 키를 다시 한번 확인해주세요');
      }
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
@mixin auth-transition($target) {
  @include transition($target 1s linear);
}
.auth-container {
  background: linear-gradient(0.25turn, $blue400, $gray200, $gray200, $blue400);
  // background-color: $green400;
  z-index: -1;
  min-height: 100vh;

  &::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    min-height: 100vh;
    background: $blue400;
    @include auth-transition(opacity);
    z-index: 0;
    opacity: 0;
    @include mobile() {
      & {
        opacity: 1;
      }
    }
  }

  header {
    font-family: 'Pacifico', cursive;
    color: $blue800;
    @include flexbox;
    @include justify-content(center);
    font-size: 2rem;
    @include align-items(center);
    .header-wrap {
      margin: 32px 0;
      padding: 0.1rem;
      @include flexbox;
      position: relative;
      cursor: pointer;
      @include mobile() {
        color: white;
        @include auth-transition(color);
      }
      .store {
        font-size: 2rem;
        padding-right: 0.3rem;
      }
    }
  }

  .main-wrap {
    @include pc {
      @include slide-transition;
    }
    @include mobile {
      @include fade-transition(slide-right, 0.5s);
      @include fade-transition(slide-left, 0.5s);
    }
    main {
      @include flexbox();
      @include justify-content(center);
      margin-bottom: 40px;
    }
    aside {
      @include flexbox();
      @include justify-content(center);
      .privacy-policy {
        font-weight: 600;
        z-index: 1;
        color: $blue600;
        cursor: pointer;
        margin-bottom: 20px;
        @include auth-transition(color);
        @include mobile() {
          color: white;
        }
      }
    }
  }
}
</style>
