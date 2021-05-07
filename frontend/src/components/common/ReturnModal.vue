<template>
  <div>
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div v-click-outside="closeModal" class="modal-container">
            <div class="close-emoji" @click="closeModal">Close</div>
            <div class="modal-header">
              <slot name="header"> <h3>서류심사 반려</h3> </slot>
            </div>
            <div class="modal-body">
              <slot name="body">
                <p>땡스토어에 관심가져 주셔서 감사합니다.</p>
                <p>귀하의 사업자등록증과 판매자 입력정보 불일치로 신청이 반려되었습니다.</p>
                <br />
                <small style="color: lightgrey"> 문의전화</small>
                <p>📞010-9265-5275</p>
                <!-- <p>감사합니다.</p> -->
              </slot>
            </div>
            <div class="modal-footer">
              <slot name="footer">
                <!-- <button v-click-outside="closeModal" class="modal-default-button" @click="closeModal">확인</button> -->
              </slot>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { deletePreStoreEnrollment } from '@/api/seller.js';
export default {
  methods: {
    async closeModal() {
      this.$emit('close');
      // await deletePreStoreEnrollment();
      // console.log('삭제요청했어요.');
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/modal/modal.scss';
.modal-container {
  border: 3px $gray400 solid;
  padding: 10px 15px;
  width: 500px;
  border-radius: 20px;
  min-height: 200px;
  margin: 0%;
  @include xs-mobile {
    width: 310px;
  }

  .close-emoji {
    color: #aaa;
    line-height: 25px;
    font-size: 1.2em;
    padding-right: 3%;
    text-align: end;
    width: 100%;
    text-decoration: none;
    &:hover {
      color: black;
      cursor: pointer;
    }
  }

  h3 {
    font-size: 1.7em;
    color: $red600;
    text-align: left;
    padding: 5px 10px 0px 10px;
  }
  .modal-body {
    font-size: 1.4em;
    min-height: 150px;
    text-align: left;
    overflow: hidden !important;
    // word-break: keep-all;
  }
  .modal-footer {
    font-size: 1.3em;
  }
}
</style>
