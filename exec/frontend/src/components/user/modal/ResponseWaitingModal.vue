<template>
  <transition name="modal">
    <div class="modal-mask">
      <div v-click-outside="closeModal" class="modal-wrapper">
        <div class="modal-header">서류심사 중</div>
        <div class="modal-body">
          <p class="mb-10">현재 관리자가 판매자 신청 정보를 검토중 입니다. 신청일 기준 1일 내로 결과 안내가 됩니다.</p>
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
    closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/modal/modal.scss';
.modal-header {
  color: rgb(255, 174, 0);
}
</style>
