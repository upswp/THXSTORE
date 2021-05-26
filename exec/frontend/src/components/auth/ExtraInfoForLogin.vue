<template>
  <div class="container">
     <header class="header-wrapper">
      <div class="login-header">Login for Thx!Store</div>
    </header>
    <div class="profile-img">
      <img :src="userData.profileImage" alt="profile" />
    </div>
      <div class="submit-items">
        <input v-model="userData.email" v-focus class="submit-item email" type="text" placeholder="이메일" />
        <input v-model="userData.password1" class="submit-item" type="password" placeholder="비밀번호" />
        <button class="submit-item btn" @click="submitForm" :disabled="btnDisabled">
          로그인
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
import { mapGetters, mapMutations } from 'vuex';
export default {
  data() {
    return {
      userData: '',
    };
  },
  computed: {
    ...mapGetters(['getTempUserInfo']),
    btnDisabled() {
      return this.userData.email !== '' && this.userData.password1 !== '' ? false : true;
    },
  },
  created() {
    this.userData = Object.assign(
      {
        email: '',
        password1: '',
      },
      this.getTempUserInfo,
    );
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    async submitForm() {
      try {
        const userData = {
          email: this.userData.email,
          password: this.userData.password1,
        };
        this.setSpinnerState(true);
        await this.$store.dispatch('LOGIN', userData);
        this.setSpinnerState(false);
        this.$router.push({ name: 'user' });
      } catch (error) {
        this.setSpinnerState(false);
        console.log(error);
        alert('로그인에 문제가 생겼습니다. 다시 시도해주세요.');
      }
    },
    moveToPage(toward) {
      this.$router.push({ name: toward });
    },
  },
};
</script>

<style lang="scss" scoped>
  .profile-img {
    @include flexbox;
    @include justify-content(center);
    img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      border: 5px solid $gray400;
      object-fit: cover;
    }
    margin-bottom: 10px;
  }

.container {
  z-index: 1;
  width: clamp(320px, 80%, 430px);
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
    @include transition(background-color 0.3s);
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
