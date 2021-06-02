<template>
  <div class="store-enrollment-container px-40">
    <waiting-modal v-if="showWaitingModal" @close="backToMain"> </waiting-modal>
    <rejected-modal v-if="showReturnModal" @close="rewriteStoreEnrollment"></rejected-modal>

    <header><h1 class="page-title">스토어 정보입력</h1></header>
    <!-- <div> -->
    <form class="form-section" @submit.prevent="submitForm">
      <ul>
        <li>
          <label class="store-baseInfo-label" for="store-name">스토어 이름</label>
          <div class="store-baseInfo-container">
            <input id="store-name" v-model="storeInfo.name" class="store-baseInfo-input" type="text" />
          </div>
        </li>
        <li>
          <set-road-name v-if="addressAPILoad" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
          <label class="store-baseInfo-label" for="nomal-address">스토어 주소</label>
          <div class="store-baseInfo-container">
            <input
              id="nomal-address"
              v-model="storeInfo.mainAddress"
              class="store-baseInfo-input"
              type="text"
              placeholder="주소"
            />
            <button id="address-button" type="reset" @click="addressAPILoad = true">주소 찾기</button>
            <input
              id="detailAddress"
              v-model="storeInfo.subAddress"
              class="store-baseInfo-input"
              type="text"
              placeholder="상세주소"
            />
          </div>
        </li>
        <li>
          <label class="store-baseInfo-label" for="phone-num">전화번호</label>
          <div class="store-baseInfo-container">
            <input
              id="phone-num"
              v-model="storeInfo.phoneNum"
              class="store-baseInfo-input"
              type="tel"
              placeholder="하이픈기호(-) 없이 입력해주세요."
              @keyup="getPhoneNumber(storeInfo.phoneNum)"
            />
          </div>
        </li>
        <li>
          <label class="store-baseInfo-label" for="comResNum">사업자등록번호</label>
          <div class="store-baseInfo-container">
            <input
              id="comResNum"
              v-model="storeInfo.license"
              class="store-baseInfo-input"
              type="tel"
              pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
              placeholder="사업자등록번호를 입력해주세요"
              @keyup="getComResNum(storeInfo.license)"
            />
          </div>
        </li>
        <li>
          <label class="store-baseInfo-label">사업자등록사본</label>
          <div class="store-baseInfo-container">
            <div class="file-flex">
              <label for="file-1" class="file-input-label">
                {{ fileValue }}
                <!-- <input id="fileName" v-model="fileValue" type="text" class="file-input-textbox" readonly /> -->
              </label>
              <div class="file-input-div">
                <label for="file-1">
                  <awesome ref="cloud" icon="cloud-upload-alt" class="before-upload"></awesome>
                </label>
                <input id="file-1" type="file" name="file-1" class="file-input-hidden" @change="insertedFile" />
              </div>
            </div>
          </div>
          <div v-if="modifyButtonLoad" class="submit-button">
            <button type="submit">
              <b>수정 완료</b>
            </button>
          </div>
          <div v-else class="submit-button">
            <button type="submit">
              <b>신청 완료</b>
            </button>
          </div>
        </li>
      </ul>
    </form>
    <!-- </div> -->
  </div>
</template>

<script>
import SetRoadName from '@/components/common/SetRoadName.vue';
import WaitingModal from '@/components/user/modal/ResponseWaitingModal.vue';
import RejectedModal from '@/components/user/modal/ResponseRejectedModal.vue';
import {
  registerStore,
  getCheckOfStore,
  deletePreStoreEnrollment,
  modifyStoreBaseInfo,
  deletePreStoreModification,
} from '@/api/seller';
import { validationPhoneNumber, validationComResNum } from '@/utils/validation';
import { mapMutations } from 'vuex';
import { handleException } from '@/utils/handler.js';
import { findAddressAPI } from '@/api/map.js';

export default {
  components: {
    SetRoadName,
    WaitingModal,
    RejectedModal,
  },
  data() {
    return {
      // 모달
      showWaitingModal: false,
      showReturnModal: false,
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
      addressAPILoad: false,
      modifyButtonLoad: false,
      checkStore: '',
      role: '',
    };
  },
  computed: {
    validForm() {
      return (
        this.storeInfo.name !== '' &&
        this.storeInfo.mainAddress !== '' &&
        this.storeInfo.phoneNum !== '' &&
        this.storeInfo.license !== '' &&
        this.storeInfo.licenseImg !== ''
      );
    },
  },
  activated() {
    this.checkRole();
  },
  methods: {
    ...mapMutations(['setSpinnerState', 'setUserInfo']),
    async checkRole() {
      try {
        this.setSpinnerState(true);
        const { data } = await getCheckOfStore('');
        const role = data.baseInfo.role;
        data.baseInfo.licenseImg = '';
        this.setSpinnerState(false);
        if (role == 'ROLE_MANAGER') {
          this.setUserInfo({ role: 'ROLE_MANAGER' });
          this.storeInfo = data.baseInfo;
          this.modifyButtonLoad = true;
          this.checkModificationModal();
        } else {
          this.checkApplicationModal();
        }
      } catch (error) {
        this.checkStore = '일반고객';
        this.setSpinnerState(false);
      }
    },

    async checkApplicationModal() {
      try {
        const { data } = await getCheckOfStore('');
        this.checkStore = data.baseInfo.checkStore;
        if (this.checkStore == 'APPLICATION_WAITING') {
          this.showWaitingModal = true;
        } else if (this.checkStore === 'APPLICATION_FAILED') {
          this.showReturnModal = true;
        }
        this.setSpinnerState(false);
      } catch (error) {
        this.setSpinnerState(false);
      }
    },
    async checkModificationModal() {
      try {
        this.setSpinnerState(true);
        const { data } = await getCheckOfStore('');
        this.checkStore = data.baseInfo.checkStore;
        if (this.checkStore == 'EDIT_WAITING') {
          this.showWaitingModal = true;
        } else if (this.checkStore === 'EDIT_FAILED') {
          this.showReturnModal = true;
        }
        this.setSpinnerState(false);
      } catch (error) {
        this.checkStore = '일반고객';
        this.setSpinnerState(false);
      }
    },
    getComResNum(comResNum) {
      if (!comResNum) return comResNum;
      let res = validationComResNum(comResNum);
      this.storeInfo.license = res;
    },
    getPhoneNumber(phoneNumber) {
      if (!phoneNumber) return phoneNumber;
      let res = validationPhoneNumber(phoneNumber);
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
      const role = this.storeInfo.role;
      if (role == 'ROLE_MANAGER') {
        deletePreStoreModification();
      } else {
        deletePreStoreEnrollment();
      }
    },
    async submitForm() {
      try {
        if (!this.validForm) {
          alert('항목을 모두 채워주세요');
        } else {
          this.setSpinnerState(true);
          const { data } = await findAddressAPI(this.storeInfo.mainAddress);
          this.storeInfo.lon = data.documents[0].x;
          this.storeInfo.lat = data.documents[0].y;
          const formData = new FormData();
          for (const key in this.storeInfo) {
            formData.append(key, this.storeInfo[key]);
          }
          if (this.modifyButtonLoad == true) {
            formData.delete('checkStore');
            formData.delete('role');
            await modifyStoreBaseInfo(formData);
          } else {
            await registerStore(formData);
            this.setSpinnerState(false);
          }
          this.setSpinnerState(false);
          this.$emit('changeTab', 'UserProfile');
        }
      } catch (error) {
        alert('스토어 등록에 문제가 생겼습니다. 다시 시도해주세요.');
        this.setSpinnerState(false);
      }
    },
    insertedFile(event) {
      const file = event.target.files[0];
      // this.licenseImg = URL.createObjectURL(file);
      this.storeInfo.licenseImg = file;
      const fileValue = file.name;
      this.fileValue = fileValue;

      if (file) {
        this.$refs.cloud.classList.add('after-upload');
      }
    },
    setLocationByRoadName(data) {
      this.addressAPILoad = false;
      this.storeInfo.mainAddress = data;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-enrollment-container {
  // padding: 2%;
  // border: 2px solid red;
  width: 100%;

  header {
    @include flexbox;
    margin: 0% 5% 2% 5%;
    justify-content: center;
    h1 {
      font-size: 24px;
    }
  }

  .form-section {
    background-color: white;
    padding: 2%;
    margin: auto;
    max-width: 1024px;
    box-shadow: 0px 0 15px rgba(0, 0, 0, 0.1);
    .store-baseInfo-label {
      @include body1;
      width: 125px;
      margin-left: 2%;
      padding: 3px 5px 7px;
      float: left;
    }
  }
  .store-baseInfo-input {
    display: inline-block;
    width: 100%;
    margin-bottom: 10px;
    padding: 10px 15px;
    border: 2.5px solid #dfe1e6;
    border-radius: 3px;
    background-color: none;
  }
  #nomal-address {
    width: 60%;
  }
  .store-baseInfo-container {
    padding-left: 25%;
  }

  .file-flex {
    @include flexbox;
    background-color: none;
    // border: 2.5px solid #dfe1e6;
  }
  .file-input-label {
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

  .file-input-textbox {
    position: inherit;
    display: inline-block;
    width: 100%;
    margin-right: 1%;
    margin-bottom: 0px;
    padding: 1px 1px;
    border: 2.5px solid white;
    background-color: none;
  }
  .file-input-div {
    position: relative;
    height: 36px;
    width: 70px;
    overflow: hidden;
  }
  .file-input-img-btn {
    padding-left: 5px;
  }
  .file-input-hidden {
    position: absolute;
    top: 0px;
    right: 0px;
    padding: 0;
    opacity: 0;
    font-size: 20px;
    filter: alpha(opacity=0);
    -ms-filter: alpha(opacity=0);
    cursor: pointer;
  }
  .before-upload {
    font-size: 40px;
    color: $blue400;
    cursor: pointer;
  }
  input::-webkit-file-upload-button {
    cursor: pointer;
  }

  .after-upload {
    transition: color 0.3s;
    color: $blue600;
  }

  #address-button {
    vertical-align: middle;
    margin: 0.4em 0.15em 0.7em;
    padding: 0.5em 1em;
    border: 1px solid #ccc;
    border-color: #dbdbdb #d2d2d2 #b2b2b2 #d2d2d3;
    border-radius: 0.2em;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#f2f2f2));
    line-height: 1.25em;
    font-size: 1em;
    color: #464646;
    cursor: pointer;
  }

  .submit-button {
    @include flexbox;
    @include justify-content(flex-end);
    button {
      @include box-shadow;
      margin-top: 20px;
      width: 30%;
      border: none;
      background-color: $blue400;
      color: $white;
      &:hover:enabled {
        background-color: $blue600;
      }
    }
  }
  @include mobile {
    .store-enrollment-container {
      width: 20%;
      padding: 0%;
    }
    #store-name {
      margin: 0px 0px 5px 0px;
    }
    header {
      h1 {
        font-size: 20px;
      }
    }
    .store-baseInfo-container {
      padding: 5px 8px;
      font-size: 13px;
    }
    .store-baseInfo-label {
      width: 40%;
      margin-bottom: 0.5%;
      margin-left: 0px;
      padding-bottom: 0%;
      float: left;
      font-size: 15px;
    }
    .store-baseInfo-container {
      width: 98%;
      padding: 0px 1% 1% 1%;

      display: inline-block;
    }
    #nomal-address {
      width: 70%;
      margin-bottom: 2px;
    }
    #address-button {
      padding: 0.5em 0 1.5% 0;
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
      h1 {
        font-size: 16px;
      }
    }
    .store-baseInfo-label {
      font-size: 12px;
      width: 40%;
      float: left;
      margin-left: 0px;
      padding-bottom: 0%;
    }
    .before-upload {
      font-size: 30px;
    }
    .store-baseInfo-input {
      padding: 5px 8px;
      font-size: 11px;
    }
    #address-button {
      margin: 1%;
      padding: 5px 3px 5px 3px;
      font-size: 12px;
    }
    .store-baseInfo-container {
      display: inline-block;
      width: 98%;
      padding: 1%;
    }
    // .file_input_label {}
    #nomal-address {
      margin: 0px;
    }
  }
}
</style>
