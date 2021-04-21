<template>
  <div class="container">
    <awesome icon="frown-open" class="fas fa-frown-open"></awesome>
    <h3>비밀번호 잊으셨나요?</h3>
    <p>
      걱정하지마세요! 성함과 Email을 입력하면 <br />
      인증코드를 가입한 메일로 보내드려요
    </p>

    <form @submit.prevent="submitForm">
      <div>
        <input type="text" placeholder="가입한 이메일을 입력해주세요!" />
        <button type="submit">
          <b>이메일 인증코드 전송</b>
        </button>
      </div>
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
export default {};
</script>

<style></style>
