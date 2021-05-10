<template>
  <div class="store-enrollment-container">
    <waiting-modal v-if="showWaitingModal" @close="backToMain"> </waiting-modal>
    <return-Modal v-if="showReturnModal" @close="rewriteStoreEnrollment"></return-Modal>

    <header><h2>스토어 정보입력</h2></header>
    <div>
      <form @submit.prevent="submitForm">
        <ul>
          <li>
            <label for="storeName">스토어 이름</label>
            <div class="input-content">
              <input id="storeName" v-model="storeInfo.name" type="text" />
            </div>
          </li>
          <li>
            <set-road-name v-if="loaded" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
            <label for="nomalAddress">스토어 주소</label>
            <div class="input-content">
              <input id="nomalAddress" v-model="storeInfo.mainAddress" type="text" placeholder="주소" />
              <button id="addressButton" type="reset" @click="loaded = true">주소 찾기</button>
              <input id="detailAddress" v-model="storeInfo.subAddress" type="text" placeholder="상세주소" />
            </div>
          </li>
          <li>
            <label for="phoneNum">전화번호</label>
            <div class="input-content">
              <input
                id="phoneNum"
                v-model="storeInfo.phoneNum"
                type="tel"
                placeholder="하이픈기호(-) 없이 입력해주세요."
                @keyup="getPhoneNumber(storeInfo.phoneNum)"
              />
            </div>
          </li>
          <li>
            <label for="comResNum">사업자등록번호</label>
            <div class="input-content">
              <input
                id="comResNum"
                v-model="storeInfo.license"
                type="tel"
                pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
                placeholder="사업자등록번호를 입력해주세요"
                @keyup="getComResNum(storeInfo.license)"
              />
            </div>
          </li>
          <li>
            <label>사업자등록사본</label>
            <div class="input-content">
              <div class="file-flex">
                <label for="file_1" class="file_input_label">
                  {{ fileValue }}
                  <!-- <input id="fileName" v-model="fileValue" type="text" class="file_input_textbox" readonly /> -->
                </label>
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
import { validationPhoneNumber, validationComResNum } from '@/utils/validation';
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
      showReturnModal: true,
      // 신청 정보
      storeInfo: {
        name: '',
        mainAddress: '',
        subAddress: '',
        phoneNum: '',
        license: '',
        licenseImg: '',
      },
      // 그 외
      fileValue: '',
      loaded: false,
      checkStore: '',
    };
  },
  computed: {
    validForm() {
      return (
        this.storeInfo.name !== '' &&
        this.storeInfo.nomalAddress !== '' &&
        this.storeInfo.phoneNum !== '' &&
        this.storeInfo.license !== '' &&
        this.storeInfo.licenseImg !== ''
      );
    },
  },
  created() {
    this.decideModal();
    console.log('created 실행되고있는가?');
  },
  methods: {
    async decideModal() {
      try {
        console.log('dicideModal 함수 시작됨');
        const { data } = await getCheckOfStore('');
        console.log(data);

        console.log('data.checkStore:', data.checkStore);
        this.checkStore = data.checkStore;
        console.log('checkStore의 값은?', this.checkStore);
        console.log('this.checkStore의 값은?', this.checkStore);
        if (this.checkStore == 'APPLICATION_WAITING') {
          console.log('참이라고');
          this.showWaitingModal = true;
        } else if (this.checkStore === 'APPLICATION_FAILED') {
          this.showReturnModal = true;
        }
      } catch (error) {
        this.checkStore = '일반고객';
      }
    },
    getComResNum(comResNum) {
      if (!comResNum) return comResNum;
      let res = validationComResNum(comResNum);
      console.log('서버넘어가는값', res);
      this.storeInfo.license = res;
    },
    getPhoneNumber(phoneNumber) {
      if (!phoneNumber) return phoneNumber;
      let res = validationPhoneNumber(phoneNumber);
      console.log('서버넘어가는값', res);
      this.storeInfo.phoneNum = res;
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
        if (!this.validForm) {
          alert('항목을 모두 채워주세요');
        } else {
          const formData = new FormData();
          for (const key in this.storeInfo) {
            formData.append(key, this.storeInfo[key]);
          }
          const res = await registerStore(formData);
          console.log(res);

          this.$emit('changeTab', 'UserProfile');
        }
      } catch (error) {
        alert('스토어 등록에 문제가 생겼습니다. 다시 시도해주세요.');
      }
    },
    insertedFile(event) {
      const file = event.target.files[0];
      // this.licenseImg = URL.createObjectURL(file);
      this.storeInfo.licenseImg = file;
      console.log('라이센스이미지', this.licenseImg);
      const fileValue = file.name;
      this.fileValue = fileValue;

      if (file) {
        this.$refs.cloud.classList.add('after-upload');
      }
    },
    setLocationByRoadName(data) {
      this.loaded = false;
      this.storeInfo.mainAddress = data;
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
  .file_input_label {
    display: inline-block;
    width: 90%;
    margin-bottom: 0px;
    margin-left: 0%;
    padding: 5px 15px;
    border: 2.5px solid #dfe1e6;
    &:hover {
      cursor: pointer;
    }
  }

  .file_input_textbox {
    position: inherit;
    display: inline-block;
    width: 100%;
    margin-bottom: 0px;
    margin-right: 1%;
    padding: 1px 1px;
    background-color: none;
    border: 2.5px solid white;
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
    #storeName {
      margin: 0px 0px 5px 0px;
    }
    header {
      font-size: 0.8rem;
    }
    input {
      font-size: 13px;
      padding: 5px 8px;
    }
    label {
      width: 40%;
      float: left;
      font-size: 15px;
      padding-bottom: 0%;
      margin-bottom: 0.5%;
      margin-left: 0px;
    }
    .input-content {
      width: 98%;
      padding: 0px 1% 1% 1%;

      display: inline-block;
    }
    #nomalAddress {
      width: 70%;
      margin-bottom: 2px;
    }
    #addressButton {
      padding-right: 0%;
      padding-left: 0%;
      padding-bottom: 1.5%;
      margin-left: 1%;
      margin-bottom: 5px;
      font-size: 13px;
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
      font-size: 11px;
    }
    #addressButton {
      margin: 1%;
      padding: 5px 3px 5px 3px;
      font-size: 12px;
    }
    .input-content {
      width: 98%;
      padding: 1%;
      display: inline-block;
    }
    // .file_input_label {}
    #nomalAddress {
      margin: 0px;
    }
  }
}
</style>
