<template>
  <transition name="modal">
    <div class="modal-mask">
      <div v-click-outside="closeModal" class="modal-wrapper">
        <div class="modal-header">서류심사 반려</div>
        <div class="modal-body">
          <p class="mb-10">
            땡스토어에 관심가져 주셔서 감사합니다. 죄송하지만, 귀하의 사업자등록증과 판매자 입력정보 불일치로 신청이
            반려되었습니다.
          </p>
          <p class="mb-30">문의 사항이 있으실 경우 아래 연락처로 전화 주세요.</p>
        </div>
        <div class="close-button-wrapper">
          <p>📞010-9265-5275</p>
          <button class="close-button" @click="closeModal">확인</button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  methods: {
    async closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/modal/modal.scss';
.modal-header {
  color: rgb(226, 1, 1);
}
</style>
