<template>
  <div class="container">
    <header>
      <div class="signup-header">Sign up for Thx!Store</div>
    </header>
    <div class="profile-img">
      <img :src="userData.profile" alt="profile" />
    </div>
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
    <hr />
    <footer>
      <span @click="moveToPage('login')">로그인하기</span>
      <span>|</span>
      <span>비밀번호찾기</span>
    </footer>
  </div>
</template>

<script>
import ValidationMixin from '@/mixins/auth/validation';
import { registerUser } from '@/api/auth';
export default {
  mixins: [ValidationMixin],
  data() {
    return {
      userData: Object.assign(
        {
          email: '',
          password1: '',
          password2: '',
        },
        this.$store.state.tempUserInfo,
      ),
    };
  },
  methods: {
    async submitForm() {
      try {
        const userData = {
          uid: this.userData.uid,
          email: this.email,
          password: this.password1,
          nickname: this.nickname,
          social: this.userData.social,
          profileImage: this.userData.profileImage,
        };
        await registerUser(userData);
        await this.$store.dispatch('LOGIN', userData);
        this.$router.push({ name: 'main' });
      } catch (error) {
        alert('회원가입에 문제가 생겼습니다. 다시 시도해주세요.');
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
  .profile-img {
    @include flexbox;
    @include justify-content(center);
    img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      border: 5px solid $red100;
      object-fit: cover;
    }
    margin-bottom: 10px;
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
