<template>
  <div class="password-reset-container">
    <div class="page-title">비밀번호 변경</div>
    <div class="password-reset-contents">
      <transition name="fade" mode="out-in">
        <div v-if="!confirm" key="first" class="contents-wrapper">
          <p class="contents-header">사용자 확인을 위해서 비밀번호를 입력해주세요</p>
          <input v-model="curPassword" type="password" class="input-password" />
          <button :disabled="!curPassword" class="confirm-button" @click="checkPassword">확인</button>
        </div>
        <div v-else key="second" class="contents-wrapper">
          <p class="contents-header">비밀번호를 입력해주세요</p>
          <input v-model="userData.password1" type="password" class="input-password" />
          <div ref="password1" class="input-label" :class="validationClass.password1">
            {{ validationMsg.password1 }}
          </div>
          <p class="contents-header">비밀번호를 다시 한번 입력해주세요</p>
          <input v-model="userData.password2" type="password" class="input-password" />
          <div ref="password2" class="input-label" :class="validationClass.password2">
            {{ validationMsg.password2 }}
          </div>
          <button :disabled="!btnDisabled" class="confirm-button" @click="changePassword">확인</button>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import { validatePwd } from '@/utils/validation';
import { loginUser, resetPwd } from '@/api/auth';
import { mapGetters, mapMutations } from 'vuex';
export default {
  data() {
    return {
      confirm: false,
      curPassword: '',
      userData: {
        password1: '',
        password2: '',
      },
      validationMsg: {
        password1: '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다',
        password2: '※ 실수 방지를 위해 비밀번호를 다시 입력해주세요',
      },
      validationClass: {
        password1: '',
        password2: '',
      },
    };
  },
  computed: {
    btnDisabled() {
      return this.validationClass.password1 === 'success-msg' && this.validationClass.password2 === 'success-msg';
    },
    ...mapGetters(['getUserInfo']),
  },
  watch: {
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
    ...mapMutations(['setSpinnerState']),
    async checkPassword() {
      if (!this.curPassword) return;
      try {
        this.setSpinnerState(true);
        await loginUser({
          email: this.getUserInfo.email,
          password: this.curPassword,
        });
        this.confirm = true;
      } catch (error) {
        console.log(error);
        alert('비밀번호가 맞지 않습니다. 다시 한번 입력해주세요.');
      } finally {
        this.setSpinnerState(false);
      }
    },
    async changePassword() {
      try {
        this.setSpinnerState(true);
        await resetPwd(this.password1);
        this.setSpinnerState(false);
        if (alert('비밀번호 변경에 성공했습니다. 메인페이지로 이동합니다')) {
          this.$router.push({ name: 'userProfile' });
        }
      } catch (error) {
        this.setSpinnerState(false);
        alert('비밀번호 변경에 문제가 생겼습니다. 로그아웃 후에 새롭게 로그인을 하신 뒤 시도해주세요.');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.password-reset-container {
  width: clamp(320px, 100%, 600px);
  padding: 10px;
}
.page-title {
  @include xl-font;
  text-align: center;
  font-weight: bolder;
  margin-bottom: 20px;
}
.password-reset-contents {
  width: 100%;
  @include flexbox;
  @include justify-content(center);
  border: 1px solid $gray100;
  border-radius: 5px;
  background: white;
  @include shadow1;
  padding: 20px;
  padding-top: 30px;
}
@include fade-transition(0.3s);
.contents-header {
  font-weight: bold;
  margin-bottom: 20px;
  @include flexbox;
  @include align-items(center);
  &::before {
    display: block;
    content: '';
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: $blue400;
    margin-right: 5px;
  }
}
.contents-wrapper {
  width: 100%;
}
.input-password {
  width: 100%;
  background-color: $gray000;
  border: 2.5px solid #dfe1e6;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 5px;
}
.confirm-button {
  width: 100%;
  border: none;
  padding: 10px 0;
  color: $white;
  background-color: $blue400;
  margin: {
    bottom: 30px;
    top: 10px;
  }
  @include box-shadow;
  &:disabled {
    background-color: $gray400;
  }
  &:hover:enabled {
    background-color: $blue600;
  }
}
.input-label {
  color: $gray600;
  @include xs-font;
  padding: 0 0 0 3px;
  margin-bottom: 20px;
}
.success-msg {
  color: $green800;
}
.alert-msg {
  color: $red600;
}
</style>
