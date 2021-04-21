export default {
  // 재사용할 컴포넌트 옵션
  methods: {
    kakaoSignup() {
      this.$_Kakao.signup();
    },
    facebookSignup() {
      this.$_Facebook.signup();
    },
    googleSignup() {
      this.$_Google.signup();
    },
  },
};
