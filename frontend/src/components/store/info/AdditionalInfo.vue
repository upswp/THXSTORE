<template>
  <section class="additional-info-container">
    <div class="additional-title">※ 추가 정보</div>
    <div class="additional-items">
      <div class="time-container">
        <div class="time-container-width">
          <div class="additional-item-label">시작 시간</div>
          <div class="time">
            <div class="toggleWrapper">
              <input id="am-pm-switch1" type="checkbox" class="toggle-switch" />
              <label for="am-pm-switch1" class="toggle"><div class="toggle-handler"></div></label>
            </div>
            <select class="time-select">
              <option v-for="(i, index) in 12" :key="index" :value="i - 1">{{ i - 1 === 0 ? '00' : i - 1 }}</option>
            </select>
            <span>:</span>
            <select class="time-select">
              <option v-for="(i, index) in 6" :key="index" :value="10 * (i - 1)">
                {{ i - 1 === 0 ? '00' : 10 * (i - 1) }}
              </option>
            </select>
          </div>
        </div>
        <div class="time-container-width">
          <div class="additional-item-label">종료 시간</div>
          <div class="time">
            <div class="toggleWrapper">
              <input id="am-pm-switch2" type="checkbox" class="toggle-switch" />
              <label for="am-pm-switch2" class="toggle"><div class="toggle-handler"></div></label>
            </div>
            <select class="time-select">
              <option v-for="(i, index) in 12" :key="index" :value="i - 1">{{ i - 1 === 0 ? '00' : i - 1 }}</option>
            </select>
            <span>:</span>
            <select class="time-select">
              <option v-for="(i, index) in 6" :key="index" :value="10 * (i - 1)">
                {{ i - 1 === 0 ? '00' : 10 * (i - 1) }}
              </option>
            </select>
          </div>
        </div>
      </div>
      <div class="category-holiday-container">
        <div class="additional-item">
          <div class="additional-item-label">공휴일</div>
          <div class="flex align-center">
            <input id="mon" v-model="holidays" type="checkbox" value="MON" class="check-input" />
            <label for="mon" class="checkbox">월</label>
            <input id="tue" v-model="holidays" type="checkbox" value="TUE" class="check-input" />
            <label for="tue" class="checkbox">화</label>
            <input id="wed" v-model="holidays" type="checkbox" value="WED" class="check-input" />
            <label for="wed" class="checkbox">수</label>
            <input id="thu" v-model="holidays" type="checkbox" value="THU" class="check-input" />
            <label for="thu" class="checkbox">목</label>
            <input id="fri" v-model="holidays" type="checkbox" value="FRI" class="check-input" />
            <label for="fri" class="checkbox">금</label>
            <input id="sat" v-model="holidays" type="checkbox" value="SAT" class="check-input" />
            <label for="sat" class="checkbox">토</label>
            <input id="sun" v-model="holidays" type="checkbox" value="SUN" class="check-input" />
            <label for="sun" class="checkbox">일</label>
          </div>
        </div>
        <div class="additional-item">
          <div class="additional-item-label">카테고리</div>
          <div>
            <select class="store-category">
              <option value="chineses">중식</option>
              <option value="japanese">일식</option>
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
              @click="introEdit = false"
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
export default {
  data() {
    return {
      holidays: ['MON', 'SAT'],
      introduction: '',
      introEdit: false,
    };
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
