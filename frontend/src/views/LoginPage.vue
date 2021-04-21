<template>
  <div class="d-container">
    <!-- 참조: https://pozafly.github.io/tripllo/(7)%EB%A1%9C%EA%B7%B8%EC%9D%B84-vue%20%EC%86%8C%EC%85%9C%EB%A1%9C%EA%B7%B8%EC%9D%B8/ -->
    <!-- <button class="external-item" type="button" @click="kakaoLogin"> -->
    <!-- <img src="@/assets/logo/kakao.png" /> -->
    <!-- <b> Continue with KakaoTalk</b> -->
    <!-- </button> -->
    <!-- <button @click="facebookLogin">facebook 로그인</button> -->
    <!-- <button id="loginBtn" @click="googleLogin">google 로그인</button> -->
    <!-- <input v-model="test" v-focus v-click-outside="testMethod" type="text" /> -->
    <div type="text" class="input-class">div</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      test: '',
    };
  },
  async created() {
    // await this.kakaoLoad();
    // await this.facebookLoad();
    // await this.googleLoad();
  },
  methods: {
    // async kakaoLoad() {
    //   try {
    //     await this.$loadScript(`https://developers.kakao.com/sdk/js/kakao.js`);
    //     if (!window.Kakao.isInitialized()) {
    //       this.$_Kakao.init();
    //     }
    //   } catch (error) {
    //     console.log(error);
    //     alert('카카오 API 키를 다시 한번 확인해주세요');
    //   }
    // },
    // kakaoLogin() {
    //   this.$_Kakao.login();
    // },
    // facebookLoad() {
    //   this.$_Facebook.init();
    // },
    // facebookLogin() {
    //   this.$_Facebook.login();
    // },
    // async googleLoad() {
    //   try {
    //     await this.$loadScript(`https://apis.google.com/js/api:client.js`);
    //     this.$_Google.init();
    //   } catch (error) {
    //     console.log(error);
    //     alert('구글 클라이언트 API 키를 다시 한번 확인해주세요');
    //   }
    // },
    // googleLogin() {
    //   this.$_Google.login();
    // },
  },
};
</script>

<style lang="scss" scoped>
.input-class {
  &::before {
    content: 'SALE!';
    font-size: small;
    padding: 3px 6px;
    margin: 0 5px;
    border-radius: 4px;
    color: #fff;
    background: #f55;
  }
}
</style>
