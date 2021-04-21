<template>
  <div class="container">
    <header>
      <div class="signup-header">Sign up for Thx!Store</div>
    </header>
    <form @submit.prevent="submitForm">
      <div class="submit-items">
        <input v-model="userData.email" v-focus class="submit-item email" type="text" placeholder="이메일" />
        <div ref="email" class="label" :class="validationClass.email">{{ validationMsg.email }}</div>
        <input v-model="userData.password1" class="submit-item" type="password" placeholder="비밀번호" />
        <div ref="password1" class="label" :class="validationClass.password1">
          {{ validationMsg.password1 }}
        </div>
        <input v-model="userData.password2" class="submit-item" type="password" placeholder="비밀번호 재입력" />
        <div ref="password2" class="label" :class="validationClass.password2">{{ validationMsg.password2 }}</div>
        <input v-model="userData.nickname" class="submit-item" type="text" placeholder="닉네임" />
        <button class="submit-item btn" type="submit" :disabled="btnDisabled">
          <b>회원가입</b>
        </button>
      </div>
    </form>
    <div class="line-division">OR</div>
    <div class="external-items">
      <button class="external-item" type="button">
        <img src="@/assets/logo/kakao.svg" />
        <b> 카카오톡으로 회원가입하기</b>
      </button>
      <button id="loginBtn" class="external-item" type="button">
        <img src="@/assets/logo/google.svg" />
        <b style="margin-right: 32px"> 구글로 회원가입하기</b>
      </button>
      <button class="external-item" type="button">
        <img src="@/assets/logo/facebook.svg" />
        <b> 페이스북으로 회원가입하기</b>
      </button>
    </div>
    <hr />
    <footer>
      <span @click="moveToPage('login')">로그인하기</span>
      <span>|</span>
      <span>비밀번호찾기</span>
    </footer>
  </div>
</template>

<script>
import { validateEmail, validatePwd } from '@/utils/validation';
import { registerUser } from '@/api/auth';
export default {
  data() {
    return {
      userData: {
        email: '',
        password1: '',
        password2: '',
        nickname: '',
      },
      validationMsg: {
        email: '※ 비밀번호 찾기를 위해서, 이메일을 신중히 입력해주세요',
        password1: '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다',
        password2: '※ 실수 방지를 위해 비밀번호를 다시 입력해주세요',
      },
      validationClass: {
        email: '',
        password1: '',
        password2: '',
      },
    };
  },
  computed: {
    btnDisabled() {
      return this.validationClass.email === 'success-msg' &&
        this.validationClass.password1 === 'success-msg' &&
        this.validationClass.password2 === 'success-msg' &&
        this.userData.nickname
        ? false
        : true;
    },
  },
  watch: {
    'userData.email': function (email) {
      // 이메일 검사
      if (email === '') {
        this.validationMsg.email = '※ 비밀번호 찾기를 위해서, 이메일을 신중히 입력해주세요';
        this.validationClass.email = '';
      } else if (validateEmail(email)) {
        this.validationMsg.email = '올바른 이메일 형식입니다';
        this.validationClass.email = 'success-msg';
      } else {
        this.validationMsg.email = '이메일 형식이 잘못되었습니다';
        this.validationClass.email = 'alert-msg';
      }
    },
    'userData.password1': function (pwd) {
      if (pwd === '') {
        this.validationMsg.password1 = '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다';
        this.validationClass.password1 = '';
      } else if (validatePwd(pwd)) {
        this.validationMsg.password1 = '올바른 비밀번호 형식입니다';
        this.validationClass.password1 = 'success-msg';
      } else {
        this.validationMsg.password1 = '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다';
        this.validationClass.password1 = 'alert-msg';
      }
    },
    'userData.password2': function (pwd) {
      if (pwd === '') {
        this.validationMsg.password2 = '※ 실수 방지를 위해 비밀번호를 다시 입력해주세요';
        this.validationClass.password2 = '';
      } else if (pwd === this.userData.password1) {
        this.validationMsg.password2 = '비밀번호가 일치합니다';
        this.validationClass.password2 = 'success-msg';
      } else {
        this.validationMsg.password2 = '비밀번호가 일치하지 않습니다';
        this.validationClass.password2 = 'alert-msg';
      }
    },
  },
  methods: {
    async submitForm() {
      try {
        const { data } = await registerUser({
          email: this.email,
          password: this.password1,
          nickname: this.nickname,
          social: null,
        });
      } catch (error) {
        alert(error);
      }
    },
    moveToPage(name) {
      this.$router.push({ name });
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  z-index: 1;
  padding: 20px;
  width: clamp(340px, 30%, 430px);
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
    .label {
      color: $gray600;
      font-size: 12px;
      position: relative;
      top: -15px;
      padding: 0 0 0 3px;
    }
    .submit-items {
      .label {
        transition: color 0.3s;
      }
      .success-msg {
        color: $green800;
      }
      .alert-msg {
        color: $red600;
      }
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
      transition: background-color 0.3s;
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
