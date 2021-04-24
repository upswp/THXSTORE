<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header"><h2>개인정보처리방침</h2></slot>
          </div>

          <div class="modal-body">
            <slot name="body">
              <h3>1. 개인정보의 처리 목적</h3>
              <p>
                땡스토어(thxstore)는 다음의 목적을 위하여 개인정보를 처리하고 있으며, 다음의 목적 이외의 용도로는
                이용하지 않습니다.
              </p>
              <p class="sub-p">고객 가입의사 확인, 고객에 대한 서비스 제공에 따른 본인 식별·인증, 회원자격</p>
              <h3>2. 개인정보의 처리 및 보유 기간 작성</h3>
              <p>
                땡스토어(thxstore)는 정보주체로부터 개인정보를 수집할 때 동의 받은 개인정보 보유·이용기간 또는 법령에
                따른 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.
              </p>
              <p>구체적인 개인정보 처리 및 보유 기간은 다음과 같습니다.</p>
              <p class="sub-p">고객 가입 및 관리 : 서비스 이용계약 또는 회원가입 해지시까지</p>
              <h3>3. 정보주체와 법정대리인의 권리·의무 및 그 행사방법</h3>
              <p>
                정보주체는 땡스토어(thxstore)에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.
              </p>
              <p class="sub-p">가. 개인정보 열람요구</p>
              <p class="sub-p">나. 오류 등이 있을 경우 정정 요구</p>
              <p class="sub-p">다. 삭제요구</p>
              <p class="sub-p">라. 처리정지 요구</p>
              <h3>4. 처리하는 개인정보 항목</h3>
              <p>
                개인정보 처리업무: 홈페이지 회원가입 및 관리, 민원사무 처리, 재화 또는 서비스 제공, 마케팅 및 광고에의
                활용
              </p>
              <p class="sub-p">필수항목: 로그인ID, 비밀번호, 서비스 이용 기록, 접속 로그, 쿠키, 접속 IP 정보</p>
              <p class="sub-p">선택항목: 이메일, 닉네임</p>
              <h3>5. 개인정보의 파기</h3>
              <p>파기절차</p>
              <p class="sub-p">
                이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련
                법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가
                아니고서는 다른 목적으로 이용되지 않습니다.
              </p>
              <br />
              <p>파기 기한</p>
              <p class="sub-p">
                이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의
                처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의
                처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.
              </p>
              <h3>6. 개인정보 자동 수집 장치의 설치·운영 및 그 거부에 관한 사항</h3>
              <p>
                1. 땡스토어(thxstore)는 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는
                ‘쿠키(cookie)’를 사용합니다.
              </p>
              <br />
              <p>
                2. 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의
                정보이며 이용자들의 컴퓨터 내의 하드디스크에 저장되기도 합니다
              </p>
              <p class="sub-p">
                가. 쿠키의 사용 목적 : 이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문 및 이용형태, 인기 검색어,
                보안접속 여부등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.
              </p>
              <p class="sub-p">
                나. 쿠키의 설치·운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키
                저장을 거부 할 수 있습니다.
              </p>
              <p class="sub-p">다. 쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.</p>
              <h3>7. 개인정보 보호책임자</h3>
              <p>이름: 성정욱</p>
              <p>소속: 개발팀</p>
              <p>전화: 010-9256-5275</p>
              <p>이메일: tjdwjddnr2968@gmail.com</p>
              <h3>8. 개인정보의 안전성 확보 조치</h3>
              <p>개인정보의 암호화</p>
              <p class="sub-p">
                이용자의 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및
                전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.
              </p>
              <h3>9. 개인정보처리방침의 변경</h3>
              <p>
                이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는
                경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.
              </p>
              <p>공고일자 : 2021년 4월 23일</p>
            </slot>
          </div>
          <div class="modal-footer">
            <slot name="footer">
              <button class="modal-default-button" @click="$emit('close')">닫기</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 300px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
  /* 내가 추가한 것 */
  max-height: 600px;
  overflow: clip;
}

.modal-header h2 {
  @include headline4;
  margin-top: 0;
  color: $blue600;
  text-align: center;
}

.modal-body {
  max-height: 470px;
  overflow-y: scroll !important;
  margin: 10px 0;
  h3 {
    @include headline6;
    margin-top: 5%;
    margin-bottom: 2%;
    text-align: center;
    word-break: keep-all;
  }
  p {
    // @include body2;
    margin-top: 1%;
    font-size: 0.9em;
  }
  .sub-p {
    font-size: 0.8em;
    padding-left: 3%;
    margin-top: 2%;
  }
}
.modal-footer {
  .modal-default-button {
    background-color: $blue600;
    color: white;
    display: block;
    margin: 0 auto;
    border: none;
  }
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
