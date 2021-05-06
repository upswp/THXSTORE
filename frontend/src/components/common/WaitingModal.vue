<template>
  <div>
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">
            <div class="modal-header">
              <slot name="header"> <h3>서류심사 중</h3> </slot>
            </div>
            <div class="modal-body">
              <slot name="body">
                <p>안녕하세요! 땡스토어에 관심가져 주셔서 감사합니다.</p>
                <br />
                <p>현재 관리자가 판매자 신청 정보를 검토중 입니다.</p>
                <br />
                <p>신청일 기준 1일 내로 결과 안내가 됩니다.</p>
                <p>감사합니다.</p>
              </slot>
            </div>
            <div class="modal-footer">
              <slot name="footer">
                <button v-click-outside="closeModal" class="modal-default-button" @click="closeModal">확인</button>
              </slot>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
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
.modal-container {
  border: 1px black solid;
  width: 500px;
  min-height: 300px;
  margin: 0%;
  @include xs-mobile {
    width: 310px;
  }
  h3 {
    font-size: 2em;
    color: red;
  }
  .modal-body {
    font-size: 1.6em;
    min-height: 180px;
    text-align: left;
    overflow: hidden !important;
    word-break: keep-all;
  }
  .modal-footer {
    font-size: 1.3em;
  }
}
</style>
