<template>
  <div class="user-account-container">
    <header class="page-title">프로필 설정</header>
    <main class="user-account-contents">
      <section class="section-part">
        <figure class="user-img">
          <div class="img-background"></div>
          <img :src="userData.profileImage" />
          <div class="profile-change-button">
            <label for="image-upload-button">
              <awesome icon="plus"></awesome>
            </label>
            <input id="image-upload-button" type="file" style="display: none" @change="changeImage" />
          </div>
        </figure>
        <aside class="profile-info">
          <div class="info-item">
            <div class="item-header">이메일</div>
            <div class="item-label">{{ userData.email }}</div>
          </div>
          <div class="info-item">
            <div class="item-header">소셜 가입</div>
            <div class="item-label">{{ userData.social === 'LOCAL' ? '일반 회원' : '소셜 회원' }}</div>
          </div>
          <div class="info-item">
            <div class="item-header">닉네임</div>
            <transition name="fade" mode="out-in">
              <div v-if="!change.nickname" key="non-editable" class="item-display">
                <div class="item-label">{{ userData.nickname }}</div>
                <awesome icon="pen-square" class="pen-icon" @click="change.nickname = true"></awesome>
              </div>
              <div v-else key="editable" class="item-input">
                <input v-model="userData.nickname" v-focus type="text" maxlength="10" @keydown.enter="setNickname" />
                <awesome :icon="['far', 'check-circle']" @click="setNickname"></awesome>
              </div>
            </transition>
          </div>
          <div class="info-item">
            <div class="item-header">전화번호</div>
            <transition name="fade" mode="out-in">
              <div v-if="!change.phone" key="non-editable" class="item-display">
                <div class="item-label">{{ userData.phoneNumber }}</div>
                <awesome icon="pen-square" class="pen-icon" @click="change.phone = true"></awesome>
              </div>
              <div v-else key="editable" class="item-input">
                <input v-model="userData.phoneNumber" v-focus type="tel" @keydown.enter="setPhoneNumber" />
                <awesome :icon="['far', 'check-circle']" @click="setPhoneNumber"></awesome>
              </div>
            </transition>
          </div>
          <div class="info-item">
            <div class="item-header">현재 위치</div>
            <div class="item-display">
              <div class="item-label address-elipsis">{{ userData.address }}</div>
              <awesome icon="pen-square" class="pen-icon" @click="loaded = true"></awesome>
            </div>
          </div>
        </aside>
      </section>
      <section class="section-part">
        <div class="kakao-map">
          <kakao-map :location="newLocation" @lat-update="latUpdate" @lon-update="lonUpdate"></kakao-map>
        </div>
      </section>
    </main>
    <set-road-name v-if="loaded" @newAddress="setAddress"></set-road-name>
  </div>
</template>

<script>
import KakaoMap from '@/components/common/KakaoMap';
import SetRoadName from '@/components/common/SetRoadName';
import { mapGetters, mapMutations } from 'vuex';

import { updateProfile } from '@/api/profile';
import { saveUserToLocalStorage } from '@/utils/webStorage';
export default {
  components: {
    KakaoMap,
    SetRoadName,
  },
  data() {
    return {
      userData: {},
      change: {
        nickname: false,
        phone: false,
        address: false,
      },
      loaded: false,
      newLocation: '대전 유성구 동서대로 98-39',
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
  },
  created() {
    this.setSpinnerState(false);
    this.userData = Object.assign({}, this.getUserInfo);
    this.newLocation = this.userData.address;
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    latUpdate(e) {
      this.userData.lat = e;
    },
    lonUpdate(e) {
      this.userData.lon = e;
    },
    async setNickname() {
      this.change.nickname = false;
      const frm = new FormData();
      frm.append('id', this.userData.id);
      frm.append('nickname', this.userData.nickname);
      try {
        await updateProfile(frm);
        saveUserToLocalStorage(this.userData);
        this.change.nickname = false;
      } catch (error) {
        alert('닉네임 변경 실패!');
      }
    },
    async setPhoneNumber() {
      this.change.phone = false;
      const frm = new FormData();
      frm.append('id', this.userData.id);
      frm.append('phoneNumber', this.userData.phoneNumber);
      try {
        await updateProfile(frm);
        saveUserToLocalStorage(this.userData);
        this.change.phone = false;
      } catch (error) {
        alert('핸드폰 번호 변경 실패!');
      }
    },
    async setAddress(addr) {
      this.loaded = false;
      if (!addr) return;
      const frm = new FormData();
      frm.append('id', this.userData.id);
      frm.append('address', addr);
      frm.append('lat', this.userData.lat);
      frm.append('lon', this.userData.lon);
      this.userData.address = addr;
      this.newLocation = addr;
      try {
        await updateProfile(frm);
        saveUserToLocalStorage(this.userData);
      } catch (error) {
        alert('위치 변경 실패!');
      }
    },
    async changeImage(e) {
      const file = e.target.files[0];
      try {
        const frm = new FormData();
        frm.append('id', this.userData.id);
        frm.append('profileImage', file);
        await updateProfile(frm);
        this.userData.profileImage = URL.createObjectURL(file);
      } catch (error) {
        alert('프로필 이미지 변경 실패!');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
