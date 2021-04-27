<template>
  <div class="store-enrollment-container">
    <header><h1>스토어 정보입력</h1></header>
    <div>
      <form @submit.prevent>
        <ul>
          <li>
            <label for="">스토어 이름</label>
            <div class="input-content">
              <input v-model="storeName" type="text" />
            </div>
          </li>
          <li>
            <label for="">스토어 주소</label>
            <div class="input-content">
              <input id="nomalAddress" v-model="nomalAddress" type="text" placeholder="주소" />
              <button id="addressButton" @click="getAddress">주소 찾기</button>
              <div
                id="layer"
                style="display: none; position: fixed; overflow: hidden; z-index: 1; -webkit-overflow-scrolling: touch"
              >
                <img
                  id="btnCloseLayer"
                  src="//t1.daumcdn.net/postcode/resource/images/close.png"
                  style="cursor: pointer; position: absolute; right: -3px; top: -3px; z-index: 1"
                  alt="닫기 버튼"
                  @click="closeDaumPostcode"
                />
              </div>
              <input id="detailAddress" v-model="detailAddress" type="text" placeholder="상세주소" />
            </div>
          </li>
          <li>
            <label for="">전화번호</label>
            <div class="input-content">
              <input
                v-model="phoneNum"
                type="tel"
                name="phone"
                pattern="[0,1]{3}-[0-9]{4}-[0-9]{4}"
                placeholder="010-○○○○-○○○○"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록 번호</label>
            <div class="input-content">
              <input
                v-model="comResNum"
                type="tel"
                name="phone"
                pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
                placeholder="○○○-○○-○○○○○"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록사본</label>
            <div class="input-content">
              <input id="fileButton" type="file" accept=".png, .jpg, .jpeg" />
            </div>
            <div class="submit-button">
              <button type="submit">
                <b>신청 완료</b>
              </button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      storeName: '',
      zip: '',
      nomalAddress: '',
      detailAddress: '',
      phoneNum: '',
      comResNum: '',
    };
  },
  methods: {
    initLayerPosition() {
      var element_layer = document.getElementById('layer');
      var width = 400; //우편번호서비스가 들어갈 element의 width
      var height = 500; //우편번호서비스가 들어갈 element의 height
      var borderWidth = 3; //샘플에서 사용하는 border의 두께

      // 위에서 선언한 값들을 실제 element에 넣는다.
      element_layer.style.width = width + 'px';
      element_layer.style.height = height + 'px';
      element_layer.style.border = borderWidth + 'px solid';
      // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
      element_layer.style.left =
        ((window.innerWidth || document.documentElement.clientWidth) - width) / 2 - borderWidth + 60 + 'px';
      element_layer.style.top =
        ((window.innerHeight || document.documentElement.clientHeight) - height) / 2 - borderWidth + 'px';
    },
    closeDaumPostcode() {
      // iframe을 넣은 element를 안보이게 한다.
      var element_layer = document.getElementById('layer');
      element_layer.style.display = 'none';
    },
    getAddress() {
      var element_layer = document.getElementById('layer');
      new window.daum.Postcode({
        oncomplete: data => {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ''; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') {
            // 사용자가 도로명 주소를 선택했을 경우
            addr = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            addr = data.jibunAddress;
          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          this.zip = data.zonecode;
          this.nomalAddress = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById('detailAddress').focus();
          element_layer.style.display = 'none';
        },
        width: '100%',
        height: '100%',
        maxSuggestItems: 5,
      }).embed(element_layer);
      element_layer.style.display = 'block';
      this.initLayerPosition();
    },
  },
};
</script>

<style lang="scss" scoped>
.store-enrollment-container {
  padding: 5%;
  width: 100%;

  form {
    background-color: white;
    padding: 2%;
    margin: auto;
    max-width: 1024px;
  }

  header {
    @include flexbox;
    margin: 0% 5% 2% 5%;
    justify-content: center;
  }

  label {
    @include body1;
    width: 125px;
    margin-left: 3%;
    padding: 3px 5px 7px;
    float: left;
  }
  #nomalAddress {
    width: 40%;
  }
  .input-content {
    padding-left: 25%;
  }
  #fileButton {
    padding: 0.5em 1em;
    margin: 0 0.15em 0.7em 0;
    border: 1px solid #ccc;
    border-color: #dbdbdb #d2d2d2 #b2b2b2 #d2d2d3;
    cursor: pointer;
    color: #464646;
    border-radius: 0.2em;
    // vertical-align: middle;
    font-size: 1em;
    line-height: 1.25em;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#f2f2f2));
  }
  #addressButton {
    padding: 0.5em 1em;
    margin: 0.4em 0.15em 0.7em 0.15em;
    border: 1px solid #ccc;
    border-color: #dbdbdb #d2d2d2 #b2b2b2 #d2d2d3;
    cursor: pointer;
    color: #464646;
    border-radius: 0.2em;
    vertical-align: middle;
    font-size: 1em;
    line-height: 1.25em;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#f2f2f2));
  }
  input {
    display: inline-block;
    width: 100%;
    margin-bottom: 10px;
    background-color: $gray000;
    border: 2.5px solid #dfe1e6;
    padding: 10px 15px;
    border-radius: 3px;
  }
  .submit-button {
    @include flexbox;
    @include justify-content(flex-end);
    button {
      @include box-shadow;
      background-color: $blue400;
      width: 30%;
      color: $white;
      border: none;
      &:hover:enabled {
        background-color: $blue600;
      }
    }
  }
  @include mobile {
    .StoreEnrollment-container {
      padding: 1px;
    }
    label {
      width: 100%;
      float: left;
    }
    .input-content {
      width: 90%;
      padding: 1%;
    }
  }
}
</style>
