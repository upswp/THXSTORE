<template>
  <div class="container">
    <h1>
      <awesome :icon="['far', 'frown']" class="fa-3x"></awesome>
    </h1>
    <header>
      <h3>비밀번호 잊으셨나요?</h3>
    </header>
    <p>
      걱정하지마세요! 성함과 Email을 입력하면 <br />
      인증코드를 가입한 메일로 보내드려요
    </p>

    <form @submit.prevent="submitEmail">
      <div>
        <input v-model="getEmail" type="text" placeholder="가입한 이메일을 입력해주세요!" />
        <button type="submit">
          <b v-if="isCodesended">이메일 인증코드 재 발송</b>
          <b v-else>이메일 인증코드 전송</b>
        </button>
      </div>
    </form>
    <form v-if="isCodesended" @submit.prevent="submitCode">
      <div>
        <input type="text" placeholder="코드를 입력하세요!" />
        <button type="submit">
          <b>인증 받기</b>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      getEmail: '',
      isCodesended: false,
    };
  },
  methods: {
    submitEmail: function () {
      console.log('확인');
      this.isCodesended = true;
    },
    submitCode: function () {
      console.log('코드');
    },
  },
};
</script>

<style lang="scss" scoped>
h1 {
  text-align: center;
  margin-bottom: 1rem;
}
.container {
  z-index: 1;
  padding: 20px;
  width: clamp(360px, 30%, 430px);
  min-height: 500px;
  @include box-shadow;
  // background: #f9fafc;
  background: white;
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
p {
  margin-bottom: 30px;
  text-align: center;
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
  .none {
    display: none;
  }
}
</style>
