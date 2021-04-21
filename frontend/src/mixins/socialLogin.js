export default {
  // 재사용할 컴포넌트 옵션
  methods: {
    kakaoLogin() {
      this.$_Kakao.login();
    },
    facebookLogin() {
      this.$_Facebook.login();
    },
    googleLogin() {
      this.$_Google.login();
    },
  },
};
