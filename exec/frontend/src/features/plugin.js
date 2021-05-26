// Private Plugin은 $_PascalCase 형식으로 적기

import $_Kakao from '@/utils/social/kakao';
import $_Facebook from '@/utils/social/facebook';
import $_Google from '@/utils/social/google';
import $_LoadScript from 'vue-plugin-load-script';
const usePlugin = Vue => {
  Vue.prototype.$_Kakao = $_Kakao;
  Vue.prototype.$_Facebook = $_Facebook;
  Vue.prototype.$_Google = $_Google;
  Vue.use($_LoadScript);
};
export { usePlugin };
