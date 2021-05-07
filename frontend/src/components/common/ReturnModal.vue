<template>
  <div>
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">
            <div class="modal-header">
              <slot name="header"> <h3>서류심사 반려</h3> </slot>
            </div>
            <div class="modal-body">
              <slot name="body">
                <p>안녕하세요! 땡스토어에 관심가져 주셔서 감사합니다.</p>
                <br />
                <p>귀하의 사업자등록증과 판매자 입력정보 불일치로 신청이 반려되었습니다.</p>
                <br />
                <p>확인 후 재신청 부탁드립니다.</p>
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
