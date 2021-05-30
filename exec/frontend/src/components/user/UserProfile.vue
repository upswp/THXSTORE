<template>
  <div class="user-account-container">
    <header class="page-title">프로필 설정</header>
    <main class="user-account-contents">
      <!-- 사용자 단편 정보 -->
      <section class="section-part">
        <!-- 사용자 이미지 조회 및 수정 필드 -->
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
        <!-- 사용자 부가 정보 조회 및 수정 필드 -->
        <aside class="profile-info">
          <!-- 이메일 -->
          <div class="info-item">
            <div class="item-header">이메일</div>
            <div class="item-label">{{ userData.email }}</div>
          </div>
          <!-- 소셜 -->
          <div class="info-item">
            <div class="item-header">소셜 가입</div>
            <div class="item-label">{{ userData.social === 'LOCAL' ? '일반 회원' : '소셜 회원' }}</div>
          </div>
          <!-- 닉네임 -->
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
          <!-- 전화번호 -->
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
          <!-- 현재 위치 -->
          <div class="info-item">
            <div class="item-header">현재 위치</div>
            <div class="item-display">
              <div class="item-label address-elipsis">{{ userData.address }}</div>
              <awesome icon="pen-square" class="pen-icon" @click="loaded = true"></awesome>
            </div>
          </div>
        </aside>
      </section>
      <!-- 사용자 현재 위치 지도 표시 -->
      <section class="section-part">
        <div class="kakao-map">
          <kakao-map :location="newLocation" @lat-update="updateLat" @lon-update="updateLong"></kakao-map>
        </div>
      </section>
    </main>

    <!-- 위치 변경시 보이는 카카오 주소 찾기 컴포넌트 -->
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
      newLocation: '대전광역시 유성구 덕명동 124',
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
    updateLat(e) {
      this.userData.lat = e;
    },
    updateLong(e) {
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
      } catch (error) {
        alert('핸드폰 번호 변경 실패!');
      }
    },
    async setAddress(addr) {
      this.loaded = false;
      if (!addr) return;
      this.setSpinnerState(true);
      const frm = new FormData();
      frm.append('id', this.userData.id);
      frm.append('address', addr);
      frm.append('lat', this.userData.lat);
      frm.append('lon', this.userData.lon);
      try {
        await updateProfile(frm);
        this.userData.address = addr;
        this.newLocation = addr;
        saveUserToLocalStorage(this.userData);
      } catch (error) {
        console.log(error);
        alert('위치 변경 실패!');
      } finally {
        this.setSpinnerState(false);
      }
    },
    async changeImage(e) {
      const file = e.target.files[0];
      this.setSpinnerState(true);
      try {
        const frm = new FormData();
        frm.append('id', this.userData.id);
        frm.append('profileImage', file);
        await updateProfile(frm);
        this.userData.profileImage = URL.createObjectURL(file);
      } catch (error) {
        alert('프로필 이미지 변경 실패!');
      } finally {
        this.setSpinnerState(false);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.user-account-container {
  width: 100%;
  padding: 0 1%;
}
.page-title {
  @include xl-font;
  text-align: center;
  font-weight: bolder;
  margin-bottom: 20px;
}
.user-account-contents {
  width: clamp(320px, 100%, 1000px);
  margin: 0 auto;
  border: 1px solid $gray100;
  border-radius: 5px;
  background: white;
  @include shadow1;
  padding: clamp(5px, 2vw, 20px);
}
.section-part {
  border-radius: 10px;
  &:nth-child(1) {
    padding: 15px 10px;
    margin-bottom: 10px;
    width: 100%;
    flex-shrink: 0;
    background-color: $gray100;
    flex-grow: 1;
  }
  &:nth-child(2) {
    overflow-y: hidden;
  }
}
.user-img {
  position: relative;
  width: 100%;
  margin: 0 0 10px 0;
  text-align: center;
  padding: 10px 0;
  & > img {
    position: relative;
    z-index: 1;
    $gap: clamp(130px, 30vw, 160px);
    width: $gap;
    height: $gap;
    border-radius: 50%;
    object-fit: cover;
    object-position: center center;
    padding: 5px;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    @include transition(transform 0.5s);
    &:hover {
      transform: scale(1.2);
    }
  }
}
.img-background {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  width: 100%;
  background-color: $dark-green400;
  height: clamp(110px, 30vw, 140px);
}

.info-item {
  @include flexbox;
  @include align-items(center);
  margin-bottom: 10px;
  @include flexbox;
}
.item-header {
  flex-basis: 40%;
  flex-shrink: 0;
  font-weight: bold;
  &::before {
    display: inline-block;
    content: '';
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: $dark-green400;
    margin-right: 5px;
    transform: rotate(45deg);
  }
}
.item-label {
  flex-grow: 1;
  text-align: right;
}
.pen-icon {
  @include lg-font;
  margin-left: 10px;
  color: $blue300;
  cursor: pointer;
  @include transition(color 0.2s);
  &:hover {
    color: $blue600;
  }
}
.item-display {
  width: 100%;
  @include flexbox;
  @include justify-content(space-between);
}
.item-input {
  width: 100%;
  @include flexbox;
  @include justify-content(flex-end);
  @include align-items(center);
  & > input {
    width: 140px;
    border: none;
    background-color: transparent;
    padding: 0;
    color: $dark-green800;
    text-align: right;
  }
  svg {
    color: $gray600;
    margin-left: 10px;
    @include lg-font;
    cursor: pointer;
    @include transition(color 0.2s);
    &:hover {
      color: $green800;
    }
  }
}
.address-elipsis {
  width: 140px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.profile-change-button {
  @include flexbox;
  @include justify-content(center);
  & > label > svg {
    position: relative;
    z-index: 2;
    top: -50px;
    right: -70px;
    width: 30px;
    height: 30px;
    padding: 10px;
    border-radius: 50%;
    background-color: $gray600;
    color: white;
    box-sizing: content-box;
    cursor: pointer;
    @include transition(transform 0.5s);
    &:hover {
      transform: rotate(180deg);
    }
    @include mobile() {
      right: -60px;
      width: 28px;
      height: 28px;
    }
    @include xs-mobile() {
      top: -45px;
      right: -45px;
      width: 24px;
      height: 24px;
    }
  }
}
.email,
.social,
.nickname,
.phone {
  margin-bottom: 30px;
  text-align: center;
}
.item-display {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  &.address {
    margin-bottom: 20px;
  }
}

.nickname,
.phone {
  input {
    border: none;
    border-bottom: 1px solid $gray400;
    width: 130px;
    text-align: center;
    margin-right: 5px;
  }
}
.address {
  input {
    border: none;
    border-bottom: 1px solid $gray400;
    min-width: 200px;
    text-align: center;
    margin-right: 5px;
  }
}

.map-container {
  width: 100%;
}
@include fade-transition(0.5s);
</style>
