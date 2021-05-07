<template>
  <div class="store-enrollment-container">
    <waiting-modal v-if="showWaitingModal" @close="backToMain"> </waiting-modal>
    <return-Modal v-if="showReturnModal" @close="rewriteStoreEnrollment"></return-Modal>

    <header><h2>스토어 정보입력</h2></header>
    <div>
      <form @submit.prevent="submitForm">
        <ul>
          <li>
            <label for="">스토어 이름</label>
            <div class="input-content">
              <input v-model="storeName" type="text" />
            </div>
          </li>
          <li>
            <set-road-name v-if="loaded" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
            <label for="">스토어 주소</label>
            <div class="input-content">
              <input id="nomalAddress" v-model="nomalAddress" type="text" placeholder="주소" />
              <button id="addressButton" @click="loaded = true">주소 찾기</button>
              <input id="detailAddress" v-model="detailAddress" type="text" placeholder="상세주소" />
            </div>
          </li>
          <li>
            <label for="">전화번호</label>
            <div class="input-content">
              <input
                v-model="phoneNum"
                type="text"
                placeholder="하이픈기호(-) 없이 입력해주세요."
                @keyup="getPhoneNumber(phoneNum)"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록번호</label>
            <div class="input-content">
              <input
                v-model="comResNum"
                type="tel"
                name="phone"
                pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
                placeholder="사업자등록번호를 입력해주세요"
                @keyup="getComResNum(comResNum)"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록사본</label>
            <div class="input-content">
              <div class="file-flex">
                <input id="fileName" v-model="fileValue" type="text" class="file_input_textbox" readonly />
                <div class="file_input_div">
                  <label for="file_1">
                    <awesome id="faCloud" ref="cloud" icon="cloud-upload-alt" class="before-upload"></awesome>
                  </label>
                  <input id="file_1" type="file" name="file_1" class="file_input_hidden" @change="insertedFile" />
                </div>
              </div>
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
import SetRoadName from '@/components/common/SetRoadName.vue';
import WaitingModal from '@/components/common/WaitingModal.vue';
import ReturnModal from '@/components/common/ReturnModal.vue';
import { registerStore, getCheckOfStore, deletePreStoreEnrollment } from '@/api/seller';
import { handleException } from '@/utils/handler.js';

export default {
  components: {
    SetRoadName,
    WaitingModal,
    ReturnModal,
  },
  data() {
    return {
      // 모달
      showWaitingModal: false,
      showReturnModal: false,
      // 신청 정보
      storeName: '',
      nomalAddress: '',
      detailAddress: '',
      phoneNum: '',
      comResNum: '',
      licenseImg: '',
      // 그 외
      fileValue: '',
      loaded: false,
      checkStore: '',
    };
  },
  created() {
    this.decideModal();
    console.log('created 실행되고있는가?');
  },
  methods: {
    async decideModal() {
      try {
        console.log('dicideModal 함수 시작됨');
        const storeInfoArr = await getCheckOfStore('');
        console.log('storeInfoArr', storeInfoArr);
        // let checkStore = this.checkStore;
        // 왜 위에처럼 코드를 못쓰나?
        this.checkStore = storeInfoArr.data.checkStore;
        console.log('checkStore의 값은?', checkStore);
        console.log('this.checkStore의 값은?', this.checkStore);
        if (this.checkStore == 'APPLICATION_WAITING') {
          console.log('참이라고');
          this.showWaitingModal = true;
        } else if (this.checkStore === 'APPLICATION_FAILED') {
          this.showReturnModal = true;
        }
      } catch (error) {
        var checkStore = this.checkStore;
        this.checkStore = '일반고객';
      }
    },
    getComResNum(val) {
      let res = this.validationComResNum(val);
      console.log('서버넘어가는값', res);
      this.comResNum = res;
    },

    getPhoneNumber(val) {
      let res = this.validationPhoneNumber(val);
      console.log('서버넘어가는값', res);
      this.phoneNum = res;
    },
    validationComResNum(comResNum) {
      if (!comResNum) return comResNum;
      comResNum = comResNum.replace(/[^0-9]/g, '');
      console.log(comResNum);
      let res = '';
      if (comResNum.length < 4) {
        res = comResNum;
        console.log('res:', res);
      } else {
        if (comResNum.length == 4) {
          res = comResNum.substr(0, 3) + '-' + comResNum.substr(3, 4);
        } else if (comResNum.length == 5) {
          res = comResNum.substr(0, 3) + '-' + comResNum.substr(3);
        } else if (comResNum.length == 6) {
          res = comResNum.substr(0, 3) + '-' + comResNum.substr(3, 2) + '-' + comResNum.substr(5);
        } else if (comResNum.length >= 7) {
          res = comResNum.substr(0, 3) + '-' + comResNum.substr(3, 2) + '-' + comResNum.substr(5, 5);
        }
      }

      return res;
    },

    validationPhoneNumber(phoneNumber) {
      if (!phoneNumber) return phoneNumber;
      phoneNumber = phoneNumber.replace(/[^0-9]/g, '');

      let res = '';
      if (phoneNumber.length < 3) {
        res = phoneNumber;
      } else {
        if (phoneNumber.substr(0, 2) == '02') {
          if (phoneNumber.length <= 5) {
            //02-123-5678
            res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3);
          } else if (phoneNumber.length > 5 && phoneNumber.length <= 9) {
            //02-123-5678
            res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3) + '-' + phoneNumber.substr(5);
          } else if (phoneNumber.length > 9) {
            //02-1234-5678
            res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 4) + '-' + phoneNumber.substr(6);
          }
        } else {
          if (phoneNumber.length < 8) {
            res = phoneNumber;
          } else if (phoneNumber.length == 8) {
            res = phoneNumber.substr(0, 4) + '-' + phoneNumber.substr(4);
          } else if (phoneNumber.length == 9) {
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
          } else if (phoneNumber.length == 10) {
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
          } else if (phoneNumber.length > 10) {
            //010-1234-5678
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 4) + '-' + phoneNumber.substr(7, 4);
          }
        }
      }

      return res;
    },
    backToMain() {
      this.showWaitingModal = false;
      // this.$router.push({ path: 'user' });
      this.$emit('changeTab', 'UserProfile');
    },
    rewriteStoreEnrollment() {
      this.showReturnModal = false;
      this.checkStore = '';
      deletePreStoreEnrollment();
      console.log('삭제요청했어요.');
    },
    async submitForm() {
      try {
        if (
          this.storeName == '' ||
          this.nomalAddress == '' ||
          this.phoneNum == '' ||
          this.comResNum == '' ||
          this.licenseImg == ''
        ) {
          alert('항목을 모두 채워주세요');
        } else {
          const formdata = new FormData();
          formdata.append('name', this.storeName);
          formdata.append('mainAddress', this.nomalAddress);
          formdata.append('subAddress', this.detailAddress);
          formdata.append('phoneNum', this.phoneNum);
          formdata.append('license', this.comResNum);
          formdata.append('licenseImg', this.licenseImg);
          // const storeData = {
          //   storeName: this.storeName,
          //   nomalAddress: this.nomalAddress,
          //   detailAddress: this.detailAddress,
          //   phoneNum: this.phoneNum,
          //   comResNum: this.comResNum,
          // };
          const res = await registerStore(formdata);
          console.log(res);

          this.$emit('changeTab', 'UserProfile');
        }
      } catch (error) {
        console.log('에러표시', error);

        alert('스토어 등록에 문제가 생겼습니다. 다시 시도해주세요.');
      }
    },
    insertedFile(event) {
      const file = event.target.files[0];
      // this.licenseImg = URL.createObjectURL(file);
      this.licenseImg = file;
      console.log('라이센스이미지', this.licenseImg);
      const fileValue = file.name;
      this.fileValue = fileValue;

      if (file) {
        this.$refs.cloud.classList.add('after-upload');
      }
    },
    setLocationByRoadName(data) {
      this.loaded = false;
      this.nomalAddress = data;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-enrollment-container {
  padding: 2%;
  width: 100%;

  form {
    background-color: white;
    padding: 2%;
    margin: auto;
    max-width: 1024px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }

  header {
    @include flexbox;
    margin: 0% 5% 2% 5%;
    justify-content: center;
  }

  label {
    @include body1;
    width: 125px;
    margin-left: 2%;
    padding: 3px 5px 7px;
    float: left;
  }
  input {
    display: inline-block;
    width: 100%;
    margin-bottom: 10px;
    background-color: none;
    border: 2.5px solid #dfe1e6;
    padding: 10px 15px;
    border-radius: 3px;
    z-index: 0;
  }
  #nomalAddress {
    width: 60%;
  }
  .input-content {
    padding-left: 25%;
  }

  .file-flex {
    @include flexbox;
    background-color: none;
    // border: 2.5px solid #dfe1e6;
  }

  .file_input_textbox {
    display: inline-block;
    width: 90%;
    margin-bottom: 0px;
    margin-right: 1%;
    padding: 1px 1px;
    background-color: none;
    border: 2.5px solid #dfe1e6;
  }
  .file_input_div {
    position: relative;
    width: 70px;
    height: 36px;
    overflow: hidden;
  }
  .file_input_img_btn {
    padding-left: 5px;
  }
  .file_input_hidden {
    font-size: 20px;
    padding: 0;
    position: absolute;
    right: 0px;
    top: 0px;
    opacity: 0;
    filter: alpha(opacity=0);
    -ms-filter: alpha(opacity=0);
    cursor: pointer;
  }
  .before-upload {
    color: $blue400;
    font-size: 40px;
    cursor: pointer;
  }
  input::-webkit-file-upload-button {
    cursor: pointer;
  }

  .after-upload {
    transition: color 0.3s;
    color: $blue600;
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

  .submit-button {
    @include flexbox;
    @include justify-content(flex-end);
    button {
      @include box-shadow;
      background-color: $blue400;
      width: 30%;
      margin-top: 20px;
      color: $white;
      border: none;
      &:hover:enabled {
        background-color: $blue600;
      }
    }
  }
  @include mobile {
    .store-enrollment-container {
      padding: 0%;
      width: 20%;
    }
    header {
      font-size: 0.7rem;
    }
    label {
      width: 40%;
      float: left;
      font-size: 12px;
      padding-bottom: 0%;
      margin-left: 0px;
    }
    .input-content {
      width: 98%;
      padding: 1%;
      display: inline-block;
    }
    #nomalAddress {
      width: 70%;
    }
    #addressButton {
      padding-right: 0%;
      padding-left: 0%;
      margin-left: 1%;
    }
    button {
      margin-right: 4%;
    }
  }
  @include xs-mobile {
    header {
      font-size: 0.7rem;
    }
    label {
      width: 40%;
      float: left;
      font-size: 12px;
      padding-bottom: 0%;
      margin-left: 0px;
    }
    .before-upload {
      font-size: 30px;
    }
    input {
      padding: 5px 8px;
    }
    #addressButton {
      margin: 2%;
      padding: 5px 3px 5px 3px;
    }
    .input-content {
      width: 98%;
      padding: 1%;
      display: inline-block;
    }
    #nomalAddress {
      margin: 0px;
    }
  }
}
</style>
