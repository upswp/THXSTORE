import { socialLogin, socialSignup } from '@/utils/social';
import router from '@/router';
// import { getUserFromLocalStorage } from '@/utils/webStorage';

const Google = {
  init() {
    window.gapi.load('auth2', async () => {
      const auth2 = await window.gapi.auth2.init({
        client_id: process.env.VUE_APP_GOOGLE_API_KEY,
        cookiepolicy: 'single_host_origin',
      });
      this.attachSignin(document.getElementById('loginBtn'), auth2);
    });
  },

  attachSignin(element, auth2) {
    auth2.attachClickHandler(
      element,
      {},
      async googleUser => {
        const profile = await googleUser.getBasicProfile();
        // if (getUserFromLocalStorage()) {
        //   alert('이미 로그인 되어 있습니다.');
        //   router.push('/main');
        //   return;
        // }
        const path = router.history.current.path;
        const req = {
          userId: profile.getId(),
          nickname: profile.getName(),
          email: profile.getEmail(),
          profileImage: profile.getImageUrl(),
          social: 'GOOGLE',
        };

        if (path.includes('login')) {
          socialLogin(req);
        } else {
          socialSignup(req);
        }
      },
      error => {
        alert(JSON.stringify(error, undefined, 2));
        console.log(JSON.stringify(error, undefined, 2));
      },
    );
  },
};

export default Google;
