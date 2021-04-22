import store from '@/store';
import router from '@/router';
import { socialCheck } from '@/api/auth';

const socialLogin = async (req, isSignup) => {
  try {
    await store.dispatch('LOGIN', req);
    if (isSignup === 'afterSignup') {
      alert('회원가입 완료! 메인 페이지로 이동합니다.');
    }
    router.push('/main');
  } catch (error) {
    // 로그인에 실패할 경우
    console.log(error);
    if (confirm('아직 가입되지 않은 회원입니다. \n회원가입 화면으로 이동하시겠습니까?')) {
      router.push({ name: 'signup' });
    }
  }
};

const socialSignup = async req => {
  try {
    // 서버에 이미 가입되어있는지 확인
    store.commit('setTempUserInfo', req);
    await socialCheck(req.uid, req.social);
    router.push({ name: 'extraInfo' });
  } catch (error) {
    // 이미 가입이 되어 있는 경우
    console.log(error);
    socialLogin(req, 'login');
  }
};

export { socialLogin, socialSignup };
