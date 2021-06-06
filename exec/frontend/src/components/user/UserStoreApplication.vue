<template>
  <div class="store-application-container">
    <waiting-modal v-if="waitingModalLoaded" @close="backToMain"> </waiting-modal>
    <rejected-modal v-if="rejectedModalLoaded" @close="confirmRejection"></rejected-modal>

    <header class="page-title">판매자 신청/수정</header>
    <main class="main-container">
      <section class="application-form">
        <div class="form-item">
          <label class="store-baseInfo-label" for="store-name">가게명</label>
          <div class="store-baseInfo-container">
            <input id="store-name" v-model="storeInfo.name" class="store-baseInfo-input" type="text" />
          </div>
        </div>
        <div class="form-item">
          <set-road-name v-if="addressAPILoaded" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
          <label class="store-baseInfo-label" for="nomal-address">가게 주소</label>
          <div class="store-address-container">
            <input
              id="nomal-address"
              v-model="storeInfo.mainAddress"
              class="store-baseInfo-input"
              type="text"
              style="margin-bottom: 0; width: 60%"
              placeholder="주소"
              disabled
            />
            <button class="address-button" @click="addressAPILoaded = true">주소 찾기</button>
          </div>
          <input
            id="detailAddress"
            v-model="storeInfo.subAddress"
            class="store-baseInfo-input"
            type="text"
            placeholder="상세주소"
          />
        </div>
        <div class="form-item">
          <label class="store-baseInfo-label" for="phone-num">전화번호</label>
          <div class="store-baseInfo-container">
            <input
              id="phone-num"
              v-model="storeInfo.phoneNum"
              class="store-baseInfo-input"
              type="tel"
              placeholder="하이픈기호(-) 없이 입력해주세요."
              @keyup="inputPhoneNumber(storeInfo.phoneNum)"
            />
          </div>
        </div>
        <div class="form-item" style="margin-bottom: 30px">
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
        </div>
        <div v-if="modifyButtonLoad" class="submit-button">
          <button type="submit" :disabled="!validForm" @click="submitForm">
            <b>판매자 수정하기</b>
          </button>
        </div>
        <div v-else class="submit-button">
          <button type="submit" @click="submitForm">
            <b>판매자 등록하기</b>
          </button>
        </div>
      </section>
      <aside class="preview-image-container">
        <article v-if="uploadedImg" class="preview-image">
          <div class="upload-state-label">
            <span>사업자 등록증</span>
            <label for="upload-input">변경하기 </label>
          </div>
          <input id="upload-input" type="file" class="upload-input" @change="uploadFile" />
          <img :src="uploadedImg" />
        </article>
        <article v-else class="preview-image-none">
          <div style="text-align: center">
            <div class="upload-guide">사업자 등록 사본을 업로드해주세요</div>
            <label for="upload-input" class="upload-label">
              <awesome ref="cloud" icon="cloud-upload-alt" class="upload-button"></awesome>
              <input id="upload-input" type="file" class="upload-input" @change="uploadFile" />
            </label>
          </div>
        </article>
      </aside>
    </main>
  </div>
</template>

<script>
import SetRoadName from '@/components/common/SetRoadName.vue';
import WaitingModal from '@/components/user/modal/ResponseWaitingModal.vue';
import RejectedModal from '@/components/user/modal/ResponseRejectedModal.vue';
import {
  registerStore,
  getMyStoreInfo,
  acceptApplicationRejected,
  modifyStoreInfo,
  acceptModificationRejected,
} from '@/api/seller';
import { validationPhoneNumber, validationComResNum } from '@/utils/validation';
import { mapMutations } from 'vuex';
import { handleException } from '@/utils/handler.js';
import { findAddressAPI } from '@/api/map.js';
import { getUserFromLocalStorage, saveUserToLocalStorage } from '@/utils/webStorage';
export default {
  components: {
    SetRoadName,
    WaitingModal,
    RejectedModal,
  },
  data() {
    return {
      // 모달
      waitingModalLoaded: false,
      rejectedModalLoaded: false,
      // 신청 정보
      storeInfo: {
        name: '',
        mainAddress: '',
        subAddress: '',
        phoneNum: '',
        license: '',
        licenseImg: '',
      },
      uploadedImg: '',
      // 그 외
      addressAPILoaded: false,
      modifyButtonLoad: false,
    };
  },
  computed: {
    validForm() {
      return (
        this.storeInfo.name !== '' &&
        this.storeInfo.mainAddress !== '' &&
        this.storeInfo.phoneNum !== '' &&
        this.storeInfo.license !== '' &&
        this.storeInfo.licenseImg instanceof File
      );
    },
  },
  watch: {
    'storeInfo.mainAddress': async function (newValue) {
      try {
        const { data } = await findAddressAPI(this.storeInfo.mainAddress);
        this.storeInfo.lon = data.documents[0].x;
        this.storeInfo.lat = data.documents[0].y;
      } catch (error) {
        console.log(error);
        alert('주소에 대응하는 위치 좌표를 찾을 수 없습니다. 다시 주소를 변경해주세요.');
      }
    },
  },
  created() {
    this.amIManager();
  },
  methods: {
    ...mapMutations(['setSpinnerState', 'setUserInfo']),
    async amIManager() {
      try {
        this.setSpinnerState(true);
        const { data } = await getMyStoreInfo();
        this.setSpinnerState(false);

        const role = data.baseInfo.role;
        if (role === 'ROLE_MANAGER') {
          // 현재 사용자가 사업자인 경우
          this.storeInfo = data.baseInfo;
          this.modifyButtonLoad = true;

          // 새롭게 로그인을 하지 않는 유저들을 위해 role에 대한 정보를 로그인하지 않더라도 갱신해준다.
          const userInfo = getUserFromLocalStorage();
          userInfo.role = 'ROLE_MANAGER';
          saveUserToLocalStorage(userInfo);

          // 마찬가지로 새롭게 로그인을 하지 않는 유저들을 위해서 vuex 레벨에서 role을 변경해준다.
          this.setUserInfo({ role: 'ROLE_MANAGER' });

          // 이전에 사업자 정보 수정을 요청하였는지 확인한다.
          this.checkModificationStatus();
        } else if (role === 'ROLE_USER') {
          // 아직 사업자가 아닌 경우, 이전에 사업자 등록을 신청하였는지 확인한다.
          this.checkApplicationStatus();
        }
      } catch (error) {
        console.log('Still User');
        this.setSpinnerState(false);
      }
    },
    checkApplicationStatus() {
      // 판매자 신청을 했는지, 했다면 현재 진행 정도가 어느 정도인지 확인
      const applicationStatus = this.storeInfo.checkStore;
      if (applicationStatus === 'APPLICATION_WAITING') {
        this.waitingModalLoaded = true;
      } else if (applicationStatus === 'APPLICATION_FAILED') {
        this.rejectedModalLoaded = true;
      }
    },
    checkModificationStatus() {
      // 판매자 정보 수정 요청을 했는지, 했다면 현재 진행 정도가 어느 정도인지 확인
      const modificationStatus = this.storeInfo.checkStore;
      if (modificationStatus === 'EDIT_WAITING') {
        this.waitingModalLoaded = true;
      } else if (modificationStatus === 'EDIT_FAILED') {
        this.rejectedModalLoaded = true;
      }
    },
    backToMain() {
      this.$router.push({ name: 'userProfile' });
    },
    confirmRejection() {
      this.rejectedModalLoaded = false;
      if (this.storeInfo.role === 'ROLE_MANAGER') {
        acceptModificationRejected();
      } else {
        acceptApplicationRejected();
      }
    },

    getComResNum(comResNum) {
      if (!comResNum) return comResNum;
      let res = validationComResNum(comResNum);
      this.storeInfo.license = res;
    },
    inputPhoneNumber(phoneNumber) {
      if (phoneNumber === '') return;

      let res = validationPhoneNumber(phoneNumber);
      this.storeInfo.phoneNum = res;
    },

    async submitForm() {
      try {
        this.setSpinnerState(true);
        const formData = new FormData();
        for (const key in this.storeInfo) {
          formData.append(key, this.storeInfo[key]);
        }
        if (this.modifyButtonLoad == true) {
          formData.delete('checkStore');
          formData.delete('role');
          await modifyStoreInfo(formData);
        } else {
          await registerStore(formData);
        }
        this.setSpinnerState(false);
        this.backToMain();
      } catch (error) {
        alert('스토어 등록/수정에 문제가 생겼습니다. 다시 시도해주세요.');
        this.setSpinnerState(false);
      }
    },
    uploadFile(event) {
      const file = event.target.files[0];
      this.uploadedImg = URL.createObjectURL(file);
      this.storeInfo.licenseImg = file;
    },
    setLocationByRoadName(data) {
      this.addressAPILoaded = false;
      this.storeInfo.mainAddress = data;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-application-container {
  width: 100%;
  padding: 0 2%;
}
.page-title {
  @include xl-font;
  text-align: center;
  font-weight: bolder;
  margin-bottom: 20px;
}
.main-container {
  @include flexbox;
  flex-wrap: wrap;
  width: clamp(310px, 100%, 1200px);
  margin: 0 auto;
  @include shadow1;
}
.application-form {
  position: relative;
  flex-grow: 1;
  margin: 0 auto;
  border: 1px solid $gray100;
  border-radius: 5px;
  background: white;
  padding: 20px 10px;
}

.form-item {
  margin-bottom: 15px;
}
.store-baseInfo-label {
  font-weight: bold;
  @include flexbox;
  @include align-items(center);
  margin-bottom: 5px;
  &::before {
    display: inline-block;
    content: '';
    width: 8px;
    height: 8px;
    margin-right: 5px;
    background-color: $yellow800;
  }
}
.store-baseInfo-input {
  display: inline-block;
  width: 100%;
  margin-bottom: 10px;
  padding: 10px 13px;
  border: 1px solid $gray250;
  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
  border-radius: 3px;
  background-color: none;
}
.store-address-container {
  @include flexbox;
  margin-bottom: 10px;
}

.address-button {
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 5px 0px 5px 10px;
  padding: 0 10px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.2);
  @include transition(all 0.1s);
  &:hover {
    color: white;
    background-color: $blue400;
  }
}
.submit-button {
  position: absolute;
  bottom: 15px;
  width: calc(100% - 20px);
  button {
    width: 100%;
    padding: 10px;
    border: none;
    @include transition(all 0.2s);
    &:enabled {
      background-color: $blue400;
      color: white;
      &:hover {
        background-color: $blue600;
      }
    }
  }
}
.preview-image-container {
  flex-grow: 2;
  width: 310px;
  height: 700px;
  padding: 10px;
  border: 2px dashed $gray400;
  margin: 10px;

  @include mobile {
    height: 600px;
  }
  @include xs-mobile {
    height: 500px;
  }
}
.preview-image {
  overflow: auto;
  height: 100%;
  img {
    width: 100%;
  }
}
.preview-image-none {
  width: 100%;
  height: 100%;
  @include cross-middle;
  position: relative;
  padding-bottom: 50px;
}
.upload-button {
  position: relative;
  font-size: 30px;
  color: $blue400;
  cursor: pointer;
  animation: bouncing 0.5s infinite alternate;
  @include transition(transform 0.5s);
  &:hover {
    animation-play-state: paused;
    @include transform(scale(1.5));
  }
}

@keyframes bouncing {
  0% {
    bottom: 0;
  }
  100% {
    bottom: 15px;
  }
}
.upload-input {
  display: none;
}
.upload-guide {
  margin-bottom: 30px;
  @include lg-font;
}
.upload-state-label {
  @include flexbox;
  @include justify-content(space-between);
  margin-top: 10px;
  span {
    padding-left: 0;
    font-weight: bold;
    @include flexbox;
    @include align-items(center);
    &::before {
      display: inline-block;
      content: '';
      width: 8px;
      height: 8px;
      background-color: $yellow800;
      margin-right: 5px;
    }
  }
  label {
    background-color: $gray100;
    padding: 5px 10px;
    margin-right: 5px;
    box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);
    cursor: pointer;
    @include transition(all 0.2s);
    &:hover {
      background-color: $blue400;
      color: white;
    }
  }
  margin-bottom: 20px;
}
</style>
