<template>
  <div class="container">
    <header class="header-wrapper">
      <div class="login-header">Login to Thx!Store</div>
    </header>
    <div class="submit-items">
      <input v-model="userData.id" v-focus class="submit-item" type="text" placeholder="아이디를 입력하세요" />
      <input
        v-model="userData.password"
        class="submit-item"
        type="password"
        placeholder="비밀번호를 입력하세요"
        @keydown.enter="submitForm"
      />
      <button class="submit-item btn" :disabled="btnDisabled" @click="submitForm">로그인</button>
    </div>
    <div class="line-division">OR</div>
    <div class="external-items">
      <button class="external-item" type="button" @click="kakaoLogin">
        <img src="@/assets/logo/kakao.svg" />
        <b> 카카오톡으로 로그인하기</b>
      </button>
      <button id="loginBtn" class="external-item" type="button">
        <img src="@/assets/logo/google.svg" />
        <b style="margin-right: 32px"> 구글로 로그인하기</b>
      </button>
      <button class="external-item" type="button" @click="facebookLogin">
        <img src="@/assets/logo/facebook.svg" />
        <b> 페이스북으로 로그인하기</b>
      </button>
    </div>
    <hr class="footer-division" />
    <footer class="footer-container">
      <span @click="moveToPage('signup')">회원가입하기</span>
      <span>|</span>
      <span @click="moveToPage('mailcode')">비밀번호찾기</span>
    </footer>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
  data() {
    return {
      userData: { id: '', password: '' },
      btnDisabled: false,
    };
  },
  mounted() {
    this.googleLoad();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    async googleLoad() {
      try {
        await this.$loadScript(`https://apis.google.com/js/api:client.js`);
        this.$_Google.init();
      } catch (error) {
        console.log(error);
        alert('구글 클라이언트 API 키를 다시 한번 확인해주세요');
      }
    },
    moveToPage(name) {
      this.$router.push({ name });
    },
    kakaoLogin() {
      this.$_Kakao.login();
    },
    facebookLogin() {
      this.$_Facebook.login();
    },
    async submitForm() {
      try {
        this.setSpinnerState(true);
        await this.$store.dispatch('LOGIN', {
          email: this.userData.id,
          password: this.userData.password,
        });
        this.setSpinnerState(false);
        this.$router.push({ name: 'userProfile' });
      } catch (error) {
        console.log(error);
        this.setSpinnerState(false);
        if (confirm('아직 가입되지 않은 회원입니다. \n회원가입 화면으로 이동하시겠습니까?')) {
          this.$router.push({ name: 'signup' });
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  z-index: 1;
  width: clamp(320px, 80%, 430px);
  min-height: 500px;
  padding: 20px;
  box-shadow: 1px 10px 20px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  background-color: rgba(255, 255, 255, 0.7);
}
.submit-item {
  @include sm-font;
  width: 100%;
  background-color: white;
  border: none;
  // border: 2.5px solid #dfe1e6;
  padding: 10px 15px;
  border-radius: 3px;
  margin-bottom: 20px;
  &.btn {
    width: 100%;
    padding: 10px 0;
    border: none;
    box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
    color: white;
    font-size: bolder;
    background-color: $blue400;
    margin-bottom: 30px;
    &:disabled {
      background-color: $gray400;
    }
    &:hover:enabled {
      background-color: $blue600;
    }
  }
}
.external-item {
  width: 100%;
  @include cross-middle;
  @include box-shadow;
  margin-bottom: 20px;
  background-color: white;
  border: none;
  // border: 1px solid $gray100;
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
.header-wrapper {
  @include flexbox;
  @include justify-content(center);
  margin-bottom: 20px;
  font: {
    size: 18px;
    weight: 600;
  }
  color: $navy800;
}
.footer-division {
  border: none;
  @include flexbox;
  @include flex-basis(100%);
  &::after {
    content: '';
    @include flex-grow(1);
    background: $blue300;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
  }
  margin-bottom: 20px;
}
.footer-container {
  font-size: 14px;
  @include flexbox;
  @include justify-content(center);
  span {
    color: $gray600;
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
</style>
