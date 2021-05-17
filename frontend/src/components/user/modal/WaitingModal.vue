<template>
  <div>
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div v-click-outside="closeModal" class="modal-container">
            <div class="close-emoji" @click="closeModal">
              <awesome icon="times"></awesome>
            </div>
            <div class="modal-header">
              <slot name="header"> <h3>서류심사 중</h3> </slot>
            </div>
            <div class="modal-body">
              <slot name="body">
                <p>현재 관리자가 판매자 신청 정보를 검토중 입니다.</p>
                <p>신청일 기준 1일 내로 결과 안내가 됩니다.</p>
                <br />
                <p>📞010-9265-5275</p>
                <div class="close-emoji">
                  <button class="close-button" @click="closeModal">확인</button>
                </div>
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
  border: 3px $gray400 solid;
  padding: 10px 15px;
  width: 500px;
  border-radius: 20px;
  min-height: 200px;
  margin: 0%;
  @include mobile {
    width: 400px;
  }
  @include xs-mobile {
    width: 290px;
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
  .close-button {
    color: white;
    background-color: $blue400;
    border: none;
    width: 20%;
    font-size: 15px;
    padding: 1.5% 1.5%;
    transition: background-color 0.3s;
    &:hover:enabled {
      background-color: $blue600;
    }
    @include mobile {
      width: 30%;
    }
    @include xs-mobile {
      width: 30%;
    }
  }

  h3 {
    font-size: 1.5em;
    color: $blue600;
    text-align: left;
    padding: 0px 10px 0px 10px;
    @include xs-mobile {
      font-size: 1.2em;
    }
  }
  p {
    font-size: 1.2rem;
    @include mobile {
      font-size: 1.1rem;
    }
    @include xs-mobile {
      font-size: 1rem;
    }
  }
  .modal-body {
    font-size: 1.4em;
    min-height: 120px;
    text-align: left;
    overflow: hidden !important;
    // word-break: keep-all;
    @include xs-mobile {
      min-height: 100px;
      margin-top: 1%;
      br {
        font-size: 10px;
        line-height: 10px;
      }
    }
  }
  .modal-footer {
    font-size: 1.3em;
  }
}
</style>
