<template>
  <section class="additional-info-container">
    <div class="additional-title">※ 추가 정보</div>
    <div class="additional-items">
      <div class="time-container">
        <div class="time-container-width">
          <div class="additional-item-label">시작 시간</div>
          <div class="time">
            <div class="toggleWrapper">
              <input id="am-pm-switch1" v-model="openMeridiem" type="checkbox" class="toggle-switch" />
              <label for="am-pm-switch1" class="toggle"><div class="toggle-handler"></div></label>
            </div>
            <select v-model="openHour" class="time-select">
              <option value="12">12</option>
              <option v-for="(i, index) in 11" :key="index" :value="timeStrConvert(i, 1)">
                {{ timeStrConvert(i, 1) }}
              </option>
            </select>
            <span>:</span>
            <select v-model="openMinute" class="time-select">
              <option v-for="(i, index) in 6" :key="index" :value="timeStrConvert(i - 1, 10)">
                {{ timeStrConvert(i - 1, 10) }}
              </option>
            </select>
          </div>
        </div>
        <div class="time-container-width">
          <div class="additional-item-label">종료 시간</div>
          <div class="time">
            <div class="toggleWrapper">
              <input id="am-pm-switch2" v-model="closeMeridiem" type="checkbox" class="toggle-switch" />
              <label for="am-pm-switch2" class="toggle"><div class="toggle-handler"></div></label>
            </div>
            <select v-model="closeHour" class="time-select">
              <option value="12">12</option>
              <option v-for="(i, index) in 11" :key="index" :value="timeStrConvert(i, 1)">
                {{ timeStrConvert(i, 1) }}
              </option>
            </select>
            <span>:</span>
            <select v-model="closeMinute" class="time-select">
              <option v-for="(i, index) in 6" :key="index" :value="timeStrConvert(i - 1, 10)">
                {{ timeStrConvert(i - 1, 10) }}
              </option>
            </select>
          </div>
        </div>
      </div>
      <div class="category-holiday-container">
        <div class="additional-item">
          <div class="additional-item-label">휴무일</div>
          <div class="flex align-center">
            <input id="mon" v-model="closeDay" type="checkbox" value="MON" class="check-input" />
            <label for="mon" class="checkbox">월</label>
            <input id="tue" v-model="closeDay" type="checkbox" value="TUE" class="check-input" />
            <label for="tue" class="checkbox">화</label>
            <input id="wed" v-model="closeDay" type="checkbox" value="WED" class="check-input" />
            <label for="wed" class="checkbox">수</label>
            <input id="thu" v-model="closeDay" type="checkbox" value="THU" class="check-input" />
            <label for="thu" class="checkbox">목</label>
            <input id="fri" v-model="closeDay" type="checkbox" value="FRI" class="check-input" />
            <label for="fri" class="checkbox">금</label>
            <input id="sat" v-model="closeDay" type="checkbox" value="SAT" class="check-input" />
            <label for="sat" class="checkbox">토</label>
            <input id="sun" v-model="closeDay" type="checkbox" value="SUN" class="check-input" />
            <label for="sun" class="checkbox">일</label>
          </div>
        </div>
        <div class="additional-item">
          <div class="additional-item-label">카테고리</div>
          <div>
            <select v-model="category" class="store-category">
              <option value="DEFAULT">선택</option>
              <option value="KR_FOOD">한식</option>
              <option value="CN_FOOD">중식</option>
              <option value="JP_FOOD">일식</option>
            </select>
          </div>
        </div>
      </div>
      <div class="additional-item">
        <div class="additional-item-label">
          가게 소개
          <transition name="fade" mode="out-in">
            <awesome
              v-if="!introEdit"
              key="edit"
              icon="pen-square"
              class="edit-icon"
              @click="introEdit = true"
            ></awesome>
            <awesome
              v-else
              key="confirm"
              :icon="['far', 'check-circle']"
              class="confirm-icon"
              @click="submitIntroduction"
            ></awesome>
          </transition>
        </div>
        <div v-if="!introEdit" class="introduction-display">{{ introduction }}</div>
        <textarea v-else v-model="introduction" type="text" class="introduction-textarea" />
      </div>
    </div>
  </section>
</template>

<script>
import { timeStrConvert, meridiemConvert } from '@/utils/filters';
import { updateStoreSideInfo } from '@/api/store';
export default {
  props: {
    info: {
      type: Object,
      default: () => {},
      require: true,
    },
    storeId: {
      type: Number,
      default: 0,
      require: true,
    },
  },
  data() {
    return {
      category: 'None',
      openMeridiem: undefined,
      openHour: '13',
      openMinute: '99',
      closeMeridiem: undefined,
      closeHour: '13',
      closeMinute: '99',
      closeDay: -1,
      introduction: '',
      introEdit: false,
    };
  },
  watch: {
    async openHour(newValue, oldValue) {
      // 초기 data를 로드할 때는 watch하면 안된다.
      if (oldValue === '13') return;

      try {
        const frm = new FormData();
        frm.append('openTime', this.hourBy24(this.openMeridiem, newValue) + ':' + this.openMinute);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('오픈 시간 변경에서 오류가 생겼습니다.');
      }
    },
    async openMinute(newValue, oldValue) {
      if (oldValue === '99') return;

      try {
        const frm = new FormData();
        frm.append('openTime', this.hourBy24(this.openMeridiem, this.openHour) + ':' + newValue);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('오픈 시간 변경에서 오류가 생겼습니다.');
      }
    },
    async closeHour(newValue, oldValue) {
      // 초기 data를 로드할 때는 watch하면 안된다.
      if (oldValue === '13') return;

      try {
        const frm = new FormData();
        frm.append('closeTime', this.hourBy24(this.closeMeridiem, newValue) + ':' + this.closeMinute);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('클로즈 시간 변경에서 오류가 생겼습니다.');
      }
    },
    async closeMinute(newValue, oldValue) {
      if (oldValue === '99') return;

      try {
        const frm = new FormData();
        frm.append('closeTime', this.hourBy24(this.closeMeridiem, this.closeHour) + ':' + newValue);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('클로즈 시간 변경에서 오류가 생겼습니다.');
      }
    },
    async openMeridiem(newValue, oldValue) {
      if (oldValue === undefined) return;
      try {
        const frm = new FormData();
        frm.append('openTime', this.hourBy24(newValue, this.openHour) + ':' + this.openMinute);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('오전/오후 변경에서 오류가 생겼습니다.');
      }
    },
    async closeMeridiem(newValue, oldValue) {
      if (oldValue === undefined) return;
      try {
        const frm = new FormData();
        frm.append('closeTime', this.hourBy24(newValue, this.closeHour) + ':' + this.closeMinute);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('오전/오후 변경에서 오류가 생겼습니다.');
      }
    },
    async closeDay(newValue, oldValue) {
      if (oldValue === -1) return;
      try {
        const frm = new FormData();
        frm.append('closeDay', newValue.join('|'));
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('오전/오후 변경에서 오류가 생겼습니다.');
      }
    },
    async category(newValue, oldValue) {
      if (newValue === 'DEFAULT' || oldValue === 'None') return;
      try {
        const frm = new FormData();
        frm.append('storeCategory', this.category);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
      } catch (error) {
        console.log(error);
        alert('카테고리 변경에서 오류가 생겼습니다.');
      }
    },
  },
  created() {
    this.loadOpenTime(this.info.openTime);
    this.loadCloseTime(this.info.closeTime);
    this.loadCloseDay(this.info.closeDay);
    this.category = this.info.storeCategory;
    this.introduction = this.info.introduce;
  },
  methods: {
    meridiemConvert,
    timeStrConvert,
    loadOpenTime(time) {
      if (!time) {
        this.openMeridiem = true;
        this.openHour = '03';
        this.openMinute = '00';
        return;
      }
      const openTime = time.split(':');
      const openSet = meridiemConvert(openTime[0]);
      this.openMeridiem = openSet[0];
      this.openHour = openSet[1];
      this.openMinute = openTime[1];
    },
    loadCloseTime(time) {
      if (!time) {
        this.closeMeridiem = true;
        this.closeHour = '11';
        this.closeMinute = '00';
        return;
      }
      const closeTime = time.split(':');
      const closeSet = meridiemConvert(closeTime[0]);
      this.closeMeridiem = closeSet[0];
      this.closeHour = closeSet[1];
      this.closeMinute = closeTime[1];
    },
    loadCloseDay(days) {
      this.closeDay = days ? days.split('|') : [];
    },
    hourBy24(mode, hour) {
      let newHour = parseInt(hour);
      if (mode) {
        if (newHour !== 12) newHour += 12;
      } else {
        if (newHour === 12) newHour = 0;
      }
      return timeStrConvert(newHour, 1);
    },
    async submitIntroduction() {
      try {
        const frm = new FormData();
        frm.append('introduce', this.introduction);
        frm.append('storeId', this.storeId);
        await updateStoreSideInfo(frm);
        this.introEdit = false;
      } catch (error) {
        console.log(error);
        alert('가게 소개 변경에서 오류가 발생했습니다.');
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.additional-info-container {
  padding: 5px 27px;
}
.additional-title {
  margin-bottom: 10px;
  font-weight: 600;
  font-size: 22px;
  @include pc {
    font-size: 18px;
  }
  @include mobile {
    font-size: 16px;
  }
  @include xs-mobile {
    font-size: 14px;
    width: 100%;
  }
}
.additional-items {
  width: 100%;
  @include flexbox;
  @include align-items(center);
  flex-wrap: wrap;
}
.category-holiday-container {
  width: 100%;
  margin-bottom: 10px;
  @include lg-pc {
    @include flexbox;
    @include justify-content(space-around);
    @include align-items(center);
  }
}
.additional-item {
  width: 100%;
  margin-bottom: 20px;
  font-size: 12px;
  @include lg-pc {
    font-size: 16px;
  }
}
.additional-item-label {
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 5px;
  @include lg-pc {
    font-size: 18px;
  }
}
.store-category {
  // border: 1px solid $gray200;
  border: none;
  width: 200px;
  padding: 5px;
  border: 1px solid $blue600;
  background: url('../../../assets/image/arrow.svg') no-repeat 95% 55%;
  background-size: 10px;
  border-radius: 0px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  &::-ms-expand {
    display: none;
  }
}
.time-container {
  font-size: 12px;
  width: 100%;
  @include lg-pc {
    font-size: 16px;
    @include flexbox;
    @include align-items(center);
  }
}
.time-container-width {
  width: 100%;
  margin-bottom: 10px;
  @include lg-pc {
    width: 50%;
  }
}
.time {
  @include flexbox;
  @include align-items(center);
}
.time-select {
  display: block;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  &::-ms-expand {
    display: none;
  }
  width: 60px;
  height: 32px;
  @include mobile {
    height: 28px;
  }
  @include xs-mobile {
    height: 28px;
  }
  border: 2px solid;
  border-color: $blue400;
  color: $gray800;
  padding: 0 20px 0 8px;
  border-radius: 4px;
  // line-height: var(--leading-tight);
  background: url('../../../assets/image/arrow.svg') no-repeat 90% 50%;
  background-size: 10px;
  text-indent: 0;
  cursor: pointer;
  padding: 5px;
}
////
.toggleWrapper {
  height: 30px;
  margin-right: 5px;
  @include mobile {
    height: 26px;
  }
  @include xs-mobile {
    height: 26px;
  }
}
.toggle-switch {
  display: none;
  &:checked + .toggle:before {
    opacity: 0.5;
  }
  &:checked + .toggle:after {
    opacity: 1;
  }
  &:checked + .toggle .toggle-handler {
    border-radius: 0px 3px 3px 0px;
    -webkit-transform: translateX(50px);
    transform: translateX(50px);
    @include mobile {
      -webkit-transform: translateX(40px);
      transform: translateX(40px);
    }
    @include xs-mobile {
      -webkit-transform: translateX(40px);
      transform: translateX(40px);
    }
    background-color: $navy800;
  }
}
.toggle {
  cursor: pointer;
  display: inline-block;
  position: relative;
  width: 100px;
  height: 28px;
  @include mobile {
    width: 80px;
    height: 24px;
  }
  @include xs-mobile {
    width: 80px;
    height: 24px;
  }
  background: $gray300;
  border-radius: 3px;
  -webkit-transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  &::before,
  &::after {
    position: absolute;
    line-height: 28px;
    font-size: 14px;
    @include mobile {
      line-height: 24px;
      font-size: 12px;
    }
    @include xs-mobile {
      line-height: 24px;
      font-size: 12px;
    }
    z-index: 2;
    -webkit-transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
    transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  }
  &::before {
    content: 'AM';
    font-weight: bold;
    left: 14px;
    @include mobile {
      left: 12px;
    }
    @include xs-mobile {
      left: 12px;
    }
    color: white;
    opacity: 1;
  }
  &::after {
    content: 'PM';
    font-weight: bold;
    right: 14px;
    @include mobile {
      right: 12px;
    }
    @include xs-mobile {
      right: 12px;
    }
    color: white;
    opacity: 0.5;
  }
}

.toggle-handler {
  position: absolute;
  z-index: 1;
  background: $blue400;
  width: 50px;
  height: 28px;
  // top:2px;
  // left:2px;
  @include mobile {
    width: 40px;
    height: 24px;
  }
  @include xs-mobile {
    width: 40px;
    height: 24px;
  }
  border-radius: 3px 0px 0px 3px;
  -webkit-transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  transition: all 200ms cubic-bezier(0.445, 0.05, 0.55, 0.95);
  // -webkit-transform: translateX(50px);
  // transform: translateX(50px);
}
.start-time,
.end-time {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
}
.check-input {
  display: none;
  &:checked + .checkbox::before {
    content: '\2713';
    font-weight: bold;
    background-color: $blue600;
    color: white;
    border: none;
  }
}
.checkbox {
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  margin-right: 5px;
  &::before {
    display: inline-block;
    margin-right: 1px;
    width: 20px;
    height: 20px;
    border: 1px solid $gray600;
    content: '';
    cursor: pointer;
    border-radius: 3px;
    @include flexbox;
    @include justify-content(center);
    @include align-items(center);
  }
}
.introduction-textarea {
  width: 100%;
  height: 200px;
  resize: none;
  padding: 5px;
  border: 1px solid $blue600;
}
.introduction-display {
  padding: 5px;
  width: 100%;
  height: 200px;
  overflow: auto;
  border: 1px solid $gray400;
}
.edit-icon {
  cursor: pointer;
  color: $gray400;
  @include transition(color 0.1s);
  &:hover {
    color: $blue600;
  }
}
.confirm-icon {
  cursor: pointer;
  color: $gray400;
  @include transition(color 0.2s);
  &:hover {
    color: $green600;
  }
}
@include fade-transition(fade, 0.2s);
</style>
