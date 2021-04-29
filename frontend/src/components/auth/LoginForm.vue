<template>
  <div class="container">
    <header>
      <div class="login-header">Login to Thx!Store</div>
    </header>
    <form @submit.prevent="submitForm">
      <div class="submit-items">
        <input v-model="userData.id" v-focus class="submit-item" type="text" placeholder="아이디를 입력하세요" />
        <input v-model="userData.password" class="submit-item" type="password" placeholder="비밀번호를 입력하세요" />
        <button class="submit-item btn" type="submit" :disabled="btnDisabled">
          <b>로그인</b>
        </button>
      </div>
    </form>
    <div class="line-division">OR</div>
    <template v-show="isSocialForm">
      <div class="external-items">
        <button class="external-item" type="button" @click="kakaoLogin">
          <img src="@/assets/logo/kakao.svg" />
          <b> 카카오톡으로 로그인하기</b>
        </button>
        <button id="loginBtn" class="external-item" type="button" @click="googleLogin">
          <img src="@/assets/logo/google.svg" />
          <b style="margin-right: 32px"> 구글로 로그인하기</b>
        </button>
        <button class="external-item" type="button" @click="facebookLogin">
          <img src="@/assets/logo/facebook.svg" />
          <b> 페이스북으로 로그인하기</b>
        </button>
      </div>
    </template>
    <hr />
    <footer>
      <span @click="moveToPage('signup')">회원가입하기</span>
      <span>|</span>
      <span @click="moveToPage('mailcode')">비밀번호찾기</span>
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userData: { id: '', password: '' },
      btnDisabled: false,
      isSocialForm: true,
    };
  },

  methods: {
    moveToPage(name) {
      this.$router.push({ name });
    },
    kakaoLogin() {
      this.$_Kakao.login();
    },
    facebookLogin() {
      this.$_Facebook.login();
    },
    googleLogin() {
      this.$_Google.login();
    },
    async submitForm() {
      try {
        await this.$store.dispatch('LOGIN', {
          userId: null,
          email: this.userData.id,
          password: this.userData.password,
          social: null,
        });
        this.$router.push({ name: 'main' });
      } catch (error) {
        if (confirm('아직 가입되지 않은 회원입니다. \n회원가입 화면으로 이동하시겠습니까?')) {
          this.router.push({ name: 'signup' });
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  z-index: 1;
  padding: 20px;
  width: clamp(360px, 30%, 430px);
  min-height: 500px;
  @include box-shadow;
  // background: #f9fafc;
  background: white;

  header {
    @include flexbox;
    @include justify-content(center);
    margin-bottom: 20px;
    font: {
      size: 18px;
      weight: 600;
    }
    color: $gray500;
  }

  form {
    font-size: 14px;
    input {
      width: 100%;
      background-color: $gray000;
      border: 2.5px solid #dfe1e6;
      padding: 10px 15px;
      border-radius: 3px;
      margin-bottom: 20px;
    }
    button {
      color: $white;
      border: none;
      background-color: $blue400;
      width: 100%;
      padding: 10px 0;
      font-size: 16px;
      @include box-shadow;
      margin-bottom: 30px;
      &:disabled {
        background-color: $gray400;
      }
      &:hover:enabled {
        background-color: $blue600;
      }
    }
  }

  .line-division {
    @include flexbox;
    @include align-items(center);
    @include flex-basis(100%);
    color: $blue300;
    &::before,
    &::after {
      content: '';
      @include flex-grow(1);
      background: $blue300;
      height: 1px;
      font-size: 0px;
      line-height: 0px;
      margin: 0px 16px;
    }
    margin-bottom: 30px;
  }
  .external-items {
    button.external-item {
      width: 100%;
      @include flexbox;
      @include align-items(center);
      @include justify-content(center);
      @include box-shadow;
      margin-bottom: 20px;
      background-color: white;
      border: 1px solid $gray100;
      color: $gray600;
      font-size: 13px;
      &:hover {
        background-color: rgba(0, 0, 0, 0.1);
        color: $gray800;
      }
      &:active {
        border: 1px solid $blue600;
        color: $blue600;
      }
      img {
        width: 2rem;
        height: 2rem;
        margin-right: 5px;
      }
    }
  }
  hr {
    border: none;
    @include flexbox;
    @include flex-basis(100%);
    &::after {
      content: '';
      @include flex-grow(1);
      background: $blue100;
      height: 1px;
      font-size: 0px;
      line-height: 0px;
    }
    margin-bottom: 20px;
  }
  footer {
    font-size: 14px;
    @include flexbox;
    @include justify-content(center);
    span {
      color: $gray400;
      cursor: pointer;
      &:nth-child(2) {
        margin: 0 10px;
        color: $gray400 !important;
      }
      &:hover {
        color: $blue600;
      }
    }
  }
}
</style>
