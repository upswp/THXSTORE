<template>
  <div class="store-application-container">
    <waiting-modal v-if="showWaitingModal" @close="backToMain"> </waiting-modal>
    <return-modal v-if="showReturnModal" @close="rewriteStoreEnrollment"></return-modal>

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
          <set-road-name v-if="addressAPILoad" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
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
            <button class="address-button" @click="addressAPILoad = true">주소 찾기</button>
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
          <button type="submit" @click="submitForm">
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
        <article v-if="licenseImg" class="preview-image">
          <div class="upload-state-label">
            <span>사업자 등록증</span>
            <label for="upload-input">변경하기 </label>
          </div>
          <input id="upload-input" type="file" class="upload-input" @change="insertedFile" />
          <img :src="licenseImg" />
        </article>
        <article v-else class="preview-image-none">
          <div style="text-align: center">
            <div class="upload-guide">사업자 등록 사본을 업로드해주세요</div>
            <label for="upload-input" class="upload-label">
              <awesome ref="cloud" icon="cloud-upload-alt" class="upload-button"></awesome>
              <input id="upload-input" type="file" class="upload-input" @change="insertedFile" />
            </label>
          </div>
        </article>
      </aside>
    </main>

    <!-- <div class="form-item">
        <label class="store-baseInfo-label">사업자등록사본</label>
        <div class="store-baseInfo-container">
          <div class="file-flex">
            <label for="file-1" class="file-input-label">
              {{ fileValue }}
            </label>
            <div class="file-input-div">
              <label for="file-1">
                <awesome ref="cloud" icon="cloud-upload-alt" class="before-upload"></awesome>
              </label>
              <input id="file-1" type="file" name="file-1" class="file-input-hidden" @change="insertedFile" />
            </div>
          </div>
        </div>
      </div> -->

    <!-- </div> -->
  </div>
</template>

<script>
import SetRoadName from '@/components/common/SetRoadName.vue';
import WaitingModal from '@/components/user/modal/WaitingModal.vue';
import ReturnModal from '@/components/user/modal/ReturnModal.vue';
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
    ReturnModal,
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
      licenseImg: '',
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
    inputPhoneNumber(phoneNumber) {
      if (phoneNumber === '') return;
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
      this.licenseImg = URL.createObjectURL(file);
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
@import '@/assets/scss/sample';
</style>
