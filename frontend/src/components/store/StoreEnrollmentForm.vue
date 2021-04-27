<template>
  <div class="StoreEnrollment-container">
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
                placeholder="010-7777-7777 형식으로 입력해 주세요"
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
                placeholder="○○○-○○-○○○○○ 형식으로 입력해주세요!"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록사본</label>
            <div class="input-content">
              <input type="file" accept=".png, .jpg, .jpeg" />
            </div>
            <input type="submit" />
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
    getAddress() {
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
        },
      }).open();
    },
  },
};
</script>

<style lang="scss" scoped>
.StoreEnrollment-container {
  padding: 5%;

  width: 100%;

  form {
    background-color: white;
    padding: 2%;
  }

  header {
    @include flexbox;
    margin: 0% 5% 2% 5%;
    justify-content: space-between;
  }

  label {
    @include body2;
    width: 15%;
    margin-left: 3%;
    padding: 3px 5px 7px;
    float: left;
    &::after {
      content: '*';
      color: red;
    }
  }
  #nomalAddress {
    width: 40%;
  }
  .input-content {
    padding-left: 25%;
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
}
</style>
