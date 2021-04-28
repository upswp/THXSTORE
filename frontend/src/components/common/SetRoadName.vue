<template>
  <div>
    <div class="location--overlay"></div>
    <section v-click-outside="closeModal" class="modal--container">
      <header class="modal--head">
        <awesome icon="times" @click="closeModal"></awesome>
        <div><slot>현재 위치 설정</slot></div>
      </header>
      <hr />
      <article class="daum--post-wrapper">
        <div ref="embed" class="embed--search"></div>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addr: '',
    };
  },
  watch: {
    addr(newValue) {
      this.sendNewAddress(newValue);
    },
  },
  mounted() {
    new window.daum.Postcode({
      oncomplete: data => {
        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
        // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        let fullRoadAddr = data.roadAddress;
        // 도로명 주소 변수
        let extraRoadAddr = '';
        // 도로명 조합형 주소 변수
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraRoadAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraRoadAddr += extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName;
        }
        // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if (extraRoadAddr !== '') {
          extraRoadAddr = ' (' + extraRoadAddr + ')';
        }
        // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
        if (fullRoadAddr !== '') {
          fullRoadAddr += extraRoadAddr;
        }
        // 우편번호와 주소 정보를 해당 필드에 넣는다.
        // this.zip = data.zonecode;
        //5자리 새우편번호 사용
        this.addr = fullRoadAddr;
      },
      width: '100%',
    }).embed(this.$refs.embed);
  },
  methods: {
    closeModal() {
      this.$emit('newAddress', '');
    },
    sendNewAddress(value) {
      this.$emit('newAddress', value);
    },
  },
};
</script>

<style lang="scss" scoped>
.location--overlay {
  position: fixed;
  z-index: 10;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  transition: opacity 0.3s ease;
}
.modal--container {
  position: fixed;
  z-index: 20;
  width: clamp(320px, 50%, 500px);
  margin: 0 auto;
  background-color: white;
  padding: 10px 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-sizing: border-box;
}
.modal--head {
  position: relative;
  svg {
    position: absolute;
    top: 0px;
    right: 2px;
  }
  div {
    text-align: center;
  }
}
.daum--post--wrapper {
  border: 1px solid;
  width: 100%;
  height: 300px;
  margin: 5px 0px;
  position: relative;
}
</style>
