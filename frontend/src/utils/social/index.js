import store from '@/store';
import router from '@/router';
// import {} from '';

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
    // await store.dispatch('IS_REGISTERED', req);
    store.commit('setTempUserInfo', req);
    router.push({ name: 'extraInfo' });

    // 성공시 서버에 사용자 등록 진행
    // await store.dispatch('SIGNUP', req);

    // 로그인도 같이 진행
    // socialLogin(req, 'afterSignup');
  } catch (error) {
    // 이미 가입이 되어 있는 경우
    console.log(error);

    if (confirm('이미 가입된 소셜 회원입니다. \n로그인 화면으로 이동하시겠습니까?')) {
      router.push({ name: 'login' });
    }
  }
};

export { socialLogin, socialSignup };
