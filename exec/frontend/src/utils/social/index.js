import store from '@/store';
import router from '@/router';
import { socialCheck } from '@/api/auth';

const socialLogin = async req => {
  store.commit('setTempUserInfo', req);
  router.push({ name: 'extraInfoLogin' });
};

const socialSignup = async req => {
  try {
    // 서버에 이미 가입되어있는지 확인
    store.commit('setTempUserInfo', req);
    store.commit('setSpinnerState', true);
    const { data } = await socialCheck(req.userId, req.social);
    store.commit('setSpinnerState', false);
    if (data.check) socialLogin(req);
    else router.push({ name: 'extraInfoSignup' });
  } catch (error) {
    // 이미 가입이 되어 있는 경우
    console.log(error);
    store.commit('setSpinnerState', false);
  }
};

export { socialLogin, socialSignup };
