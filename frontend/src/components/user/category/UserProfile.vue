<template>
  <div class="user-account-container">
    <div class="page-title">프로필 설정</div>
    <div class="user-account-contents">
      <div class="user-profile-image">
        <img
          src="https://images.unsplash.com/photo-1521572267360-ee0c2909d518?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fHByb2ZpbGV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=400&q=60"
        />
      </div>
      <div class="profile-change-button">
        <awesome icon="cloud-upload-alt"></awesome>
      </div>
      <div class="user-profile-info">
        <div class="simple-info">
          <div class="col">
            <div class="email">
              <div class="item-header">이메일</div>
              <div class="item-label">a@a.com</div>
            </div>
            <div class="social">
              <div class="item-header">소셜 가입</div>
              <div class="item-label">일반 회원</div>
            </div>
          </div>
          <div class="col">
            <div class="nickname">
              <div class="item-header">닉네임</div>
              <transition name="fade" mode="out-in">
                <div v-if="!change.nickname" key="non-editable" class="item-display">
                  <div class="item-label">{{ nickname }}</div>
                  <awesome icon="pen-square" @click="change.nickname = true"></awesome>
                </div>
                <div v-else key="editable" class="item-input">
                  <input v-model="nickname" type="text" maxlength="10" />
                  <awesome :icon="['far', 'check-circle']" @click="change.nickname = false"></awesome>
                </div>
              </transition>
            </div>
            <div class="phone">
              <div class="item-header">핸드폰 번호</div>
              <transition name="fade" mode="out-in">
                <div v-if="!change.phone" key="non-editable" class="item-display">
                  <div class="item-label">{{ phone }}</div>
                  <awesome icon="pen-square" @click="change.phone = true"></awesome>
                </div>
                <div v-else key="editable" class="item-input">
                  <input v-model="phone" type="tel" />
                  <awesome :icon="['far', 'check-circle']" @click="change.phone = false"></awesome>
                </div>
              </transition>
            </div>
          </div>
        </div>
        <div class="location">
          <div class="item-header">위치 설정</div>
          <div class="item-display">
            <div class="item-label">{{ location }}</div>
            <awesome icon="pen-square" @click="loaded = true"></awesome>
          </div>
          <kakao-map :location="newLocation"></kakao-map>
        </div>
      </div>
    </div>
    <set-road-name v-if="loaded" @newAddress="setLocationByRoadName"></set-road-name>
  </div>
</template>

<script>
import KakaoMap from '@/components/common/KakaoMap';
import SetRoadName from '@/components/common/SetRoadName';
export default {
  components: {
    KakaoMap,
    SetRoadName,
  },
  data() {
    return {
      nickname: 'daep93',
      phone: '010-8388-7260',
      location: '대전시 유성구 서울산로 3길',
      newLocation: '대전시 유성구 서울산로 3길',
      change: {
        nickname: false,
        phone: false,
        location: false,
      },
      loaded: true,
    };
  },
  methods: {
    setLocation() {
      this.change.location = false;
      this.newLocation = this.location;
    },
    setLocationByRoadName(data) {
      this.loaded = false;
      console.log(data);
    },
  },
};
</script>

<style lang="scss" scoped>
.user-account-container {
  width: 100%;
}
.page-title {
  font-size: 24px;
  text-align: center;
  font-weight: 600;
  margin-bottom: 20px;
  @include mobile() {
    font-size: 18px;
  }
  @include xs-mobile() {
    font-size: 16px;
  }
}
.user-account-contents {
  border: 1px solid $gray100;
  border-radius: 5px;
  background: white;
  @include shadow1;
  padding: 20px;
}
.user-profile-image {
  @include flexbox;
  @include justify-content(center);
  & > img {
    width: 160px;
    height: 160px;
    border-radius: 50%;
    object-fit: cover;
    padding: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    @include mobile {
      width: 140px;
      height: 140px;
    }
    @include xs-mobile {
      width: 110px;
      height: 110px;
    }
  }
}

.profile-change-button {
  @include flexbox;
  @include justify-content(center);
  & > svg {
    position: relative;
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
.item-header {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
  @include mobile() {
    font-size: 16px;
  }
  @include xs-mobile() {
    font-size: 14px;
  }
}
.item-display {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  svg {
    width: 20px;
    height: 20px;
    color: $blue300;
    cursor: pointer;
    &:hover {
      color: $blue600;
    }
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
.location {
  input {
    border: none;
    border-bottom: 1px solid $gray400;
    min-width: 200px;
    text-align: center;
    margin-right: 5px;
  }
}

.simple-info {
  @include flexbox;
  @include pc() {
    @include justify-content(space-around);
  }
  margin-bottom: 20px;
}
.col {
  width: 50%;
  @include lg-pc() {
    @include flexbox;
    @include justify-content(space-around);
  }
  @include pc() {
    text-align: center;
  }
}
.item-input {
  @include flexbox;
  @include align-items(center);
  @include justify-content(center);
  svg {
    color: $gray600;
    width: 20px;
    height: 20px;
    cursor: pointer;
    &:hover {
      color: $green800;
    }
  }
}
.item-label {
  min-width: 130px;
  line-height: 20px;
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}

@include fade-transition(fade, 0.5s);
</style>
