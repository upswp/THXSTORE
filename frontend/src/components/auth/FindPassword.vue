<template>
  <div>
    <header>
      아이콘 들어갈 자리
      <h1>새 비밀번호를 설정하세요</h1>
    </header>
    <form @submit.prevent="submitForm">
      <input type="text" placeholder="새로운 비밀번호를 입력해 주세요" />
      <input type="text" placeholder="새로운 비밀번호를 확인해 주세요" />
      <button type="submit">
        <b> 비밀번호 재설정 </b>
      </button>
    </form>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
