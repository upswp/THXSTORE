<template>
  <div class="container">
    <h1>
      <awesome :icon="['far', 'smile-wink']" class="fa-3x"></awesome>
    </h1>
    <header>
      <h3>새 비밀번호를 설정하세요</h3>
    </header>
    <form @submit.prevent="submitForm">
      <div class="submit-items">
        <input v-model="userData.password1" type="password" placeholder="새로운 비밀번호를 입력해 주세요" />
        <div ref="password1" class="label" :class="validationClass.password1">
          {{ validationMsg.password1 }}
        </div>
        <input v-model="userData.password2" type="password" placeholder="새로운 비밀번호를 확인해 주세요" />
        <div ref="password2" class="label" :class="validationClass.password2">{{ validationMsg.password2 }}</div>
<<<<<<< HEAD
<<<<<<< HEAD
        <button type="submit" :disabled="btnDisabled" @click="submitForm">
=======
        <button type="submit" :disabled="btnDisabled">
>>>>>>> a2fc403 ([S04P31B202-111] feat: 메일인증 및 비밀번호 재설정)
=======
        <button type="submit" :disabled="btnDisabled" @click="resetPwd">
>>>>>>> 778f33d ([S04P31B202-110] feat : resetPWD 함수 연결)
          <b> 비밀번호 재설정 </b>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import ValidationMixin from '@/mixins/auth/validation';
export default {
  mixins: [ValidationMixin],
  data() {
    return {
      userData: {
        email: 'e',
        password1: '',
        password2: '',
        nickname: 'e',
      },
    };
  },
  created() {
    this.validationClass.email = 'success-msg';
  },
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 778f33d ([S04P31B202-110] feat : resetPWD 함수 연결)
  methods: {
    async submitForm() {
      try {
        const userData = {};
        await resetPwd(userData);
      } catch (error) {
        console.log(error);
      }
    },
  },
<<<<<<< HEAD
=======
>>>>>>> a2fc403 ([S04P31B202-111] feat: 메일인증 및 비밀번호 재설정)
=======
>>>>>>> 778f33d ([S04P31B202-110] feat : resetPWD 함수 연결)
};
</script>

<style lang="scss" scoped>
<<<<<<< HEAD
=======
h1 {
  text-align: center;
  margin-bottom: 2rem;
}
>>>>>>> a2fc403 ([S04P31B202-111] feat: 메일인증 및 비밀번호 재설정)
.container {
  z-index: 1;
  padding: 20px;
  width: clamp(360px, 30%, 430px);
  min-height: 500px;
  @include box-shadow;
  background: white;
<<<<<<< HEAD
  h1 {
    text-align: center;
    margin-bottom: 2rem;
  }

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
=======
}
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
.container {
  z-index: 1;
  padding: 20px;
  width: clamp(360px, 30%, 430px);
  min-height: 500px;
  @include box-shadow;
  background: white;
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
>>>>>>> a2fc403 ([S04P31B202-111] feat: 메일인증 및 비밀번호 재설정)
    }
  }
}
</style>
