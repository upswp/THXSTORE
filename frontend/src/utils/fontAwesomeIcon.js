import Vue from 'vue';
// 참조 : https://blog.logrocket.com/full-guide-to-using-font-awesome-icons-in-vue-js-apps-5574c74d9b2d/
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import {
  faStore,
  faBars,
  faUserCircle,
  faMeteor,
  faFrown,
  faClipboardList,
  faPlus,
  faCamera,
} from '@fortawesome/free-solid-svg-icons';
library.add(faStore, faBars, faUserCircle, faMeteor, faFrown, faClipboardList, faPlus, faCamera);

import {
  faClock as farClock,
  faClipboard as farClipboard,
  faCheckSquare as farCheckSquare,
  faUser as farUser,
  faCheckCircle as farCheckCircle,
  faMeh as farMeh,
  faHeart as farHeart,
  faFrown as farFrown,
  faSmileWink as farSmileWink,
} from '@fortawesome/free-regular-svg-icons';
library.add(farClock, farClipboard, farCheckSquare, farUser, farCheckCircle, farMeh, farHeart, farFrown, farSmileWink);

Vue.component('awesome', FontAwesomeIcon);
