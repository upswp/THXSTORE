import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { usePlugin } from '@/features/plugin';
import { useDirective } from '@/features/directive';
import '@/assets/scss/base/global.scss';
import '@/utils/fontAwesomeIcon.js';
import VueMasonry from 'vue-masonry-css';

Vue.use(VueMasonry);
Vue.config.productionTip = false;
Vue.use(usePlugin);
Vue.use(useDirective);
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
