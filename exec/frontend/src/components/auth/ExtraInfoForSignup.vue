<template>
  <div class="container">
    <header class="header-wrapper">
      <div class="signup-header">Sign up for Thx!Store</div>
    </header>
    <div class="profile-img">
      <img :src="userData.profileImage" alt="profile" />
    </div>
    <div class="submit-items">
      <input v-model="userData.email" v-focus class="submit-item email" type="text" placeholder="이메일" />
      <div ref="email" class="label" :class="validationClass.email">{{ validationMsg.email }}</div>
      <input v-model="userData.password1" class="submit-item" type="password" placeholder="비밀번호" />
      <div ref="password1" class="label" :class="validationClass.password1">
        {{ validationMsg.password1 }}
      </div>
      <input v-model="userData.password2" class="submit-item" type="password" placeholder="비밀번호 재입력" />
      <div ref="password2" class="label" :class="validationClass.password2">{{ validationMsg.password2 }}</div>
      <input v-model="userData.nickname" class="submit-item" type="text" placeholder="닉네임" />
      <button class="submit-item btn" :disabled="btnDisabled" @click="submitForm">회원가입</button>
    </div>
    <hr class="footer-division" />
    <footer class="footer-container">
      <span @click="moveToPage('login')">로그인하기</span>
      <span>|</span>
      <span @click="moveToPage('mailcode')">비밀번호찾기</span>
    </footer>
  </div>
</template>

<script>
import ValidationMixin from '@/mixins/auth/validation';
import { registerUser } from '@/api/auth';
import { mapGetters, mapMutations } from 'vuex';
import { findLocationAPI } from '@/api/map';
export default {
  mixins: [ValidationMixin],
  data() {
    return {
      userData: {},
    };
  },
  computed: {
    ...mapGetters(['getTempUserInfo']),
  },
  created() {
    this.userData = Object.assign(
      {
        email: '',
        password1: '',
        password2: '',
        lat: 33.450701,
        lon: 126.570667,
        address: '대전광역시 유성구 덕명동 124',
      },
      this.getTempUserInfo,
    );
    this.getLatLong();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    async submitForm() {
      try {
        const userData = {
          userId: this.userData.userId,
          email: this.userData.email,
          password: this.userData.password1,
          nickname: this.userData.nickname,
          social: this.userData.social,
          profileImage: this.userData.profileImage,
          lat: this.userData.lat,
          lon: this.userData.lon,
          address: this.userData.address,
        };
        this.setSpinnerState(true);
        // 현재 위치 들어갈 자리
        await registerUser(userData);
        await this.$store.dispatch('LOGIN', {
          email: this.userData.email,
          password: this.userData.password1,
        });
        this.setSpinnerState(false);
        this.$router.push({ name: 'userProfile' });
      } catch (error) {
        this.setSpinnerState(false);
        alert('회원가입에 문제가 생겼습니다. 다시 시도해주세요.');
      }
    },
    moveToPage(toward) {
      this.$router.push({ name: toward });
    },
    getLatLong() {
      if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(position => {
          let geocoder = new kakao.maps.services.Geocoder();
          let coord = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
          this.userData.lat = position.coords.latitude;
          this.userData.lon = position.coords.longitude;
          findLocationAPI(this.userData.lat, this.userData.lon).then(response => {
            this.userData.address = response.data.documents[0].address.address_name;
          });
        });
      } else {
        /* 위치정보 사용 불가능 */
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.profile-img {
  @include flexbox;
  @include justify-content(center);
  img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    border: 5px solid $gray400;
    object-fit: cover;
  }
  margin-bottom: 10px;
}
.container {
  z-index: 1;
  width: clamp(320px, 80%, 430px);
  padding: 20px;
  box-shadow: 1px 10px 20px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  background-color: rgba(255, 255, 255, 0.7);
}
.line-division {
  @include flexbox;
  @include align-items(center);
  @include flex-basis(100%);
  color: $blue300;
  margin-bottom: 30px;
  &::before,
  &::after {
    content: '';
    @include flex-grow(1);
    background: $blue300;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
    margin: 0px 16px;
  }
}

.submit-items {
  .label {
    color: $gray600;
    font-size: 12px;
    position: relative;
    top: -15px;
    padding: 0 0 0 3px;
    transition: color 0.3s;
  }
  .success-msg {
    color: $green800;
  }
  .alert-msg {
    color: $red600;
  }
}
.submit-item {
  @include sm-font;
  width: 100%;
  background-color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 3px;
  margin-bottom: 20px;
  &.btn {
    color: $white;
    border: none;
    background-color: $blue400;
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    @include box-shadow;
    margin-bottom: 30px;
    transition: background-color 0.3s;
    &:disabled {
      background-color: $gray400;
    }
    &:hover:enabled {
      background-color: $blue600;
    }
  }
}
.footer-division {
  border: none;
  @include flexbox;
  @include flex-basis(100%);
  &::after {
    content: '';
    @include flex-grow(1);
    background: $blue300;
    height: 1px;
    font-size: 0px;
    line-height: 0px;
  }
  margin-bottom: 20px;
}
.footer-container {
  font-size: 14px;
  @include flexbox;
  @include justify-content(center);
  span {
    color: $gray600;
    cursor: pointer;
    &:nth-child(2) {
      margin: 0 10px;
      color: $gray400 !important;
    }
    &:hover {
      color: $blue600;
    }
  }
}
.external-item {
  width: 100%;
  @include cross-middle;
  @include box-shadow;
  margin-bottom: 20px;
  background-color: white;
  border: none;
  // border: 1px solid $gray100;
  color: $gray600;
  font-size: 13px;
  &:hover {
    background-color: rgba(0, 0, 0, 0.1);
    color: $gray800;
  }
  &:active {
    border: 1px solid $blue600;
    color: $blue600;
  }
  img {
    width: 2rem;
    height: 2rem;
    margin-right: 5px;
  }
}
.header-wrapper {
  @include flexbox;
  @include justify-content(center);
  margin-bottom: 20px;
  font: {
    size: 18px;
    weight: 600;
  }
  color: $navy800;
}
</style>
