import { socialLogin, socialSignup } from '@/utils/social';

const Facebook = {
  init() {
    window.fbAsyncInit = function () {
      window.FB.init({
        appId: process.env.VUE_APP_FACEBOOK_API_KEY,
        cookie: true,
        xfbml: true,
        version: 'v9.0',
      });
      window.FB.AppEvents.logPageView();
    };
    (function (d, s, id) {
      var js,
        fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) {
        return;
      }
      js = d.createElement(s);
      js.id = id;
      js.src = 'https://connect.facebook.net/en_US/sdk.js';
      fjs.parentNode.insertBefore(js, fjs);
    })(document, 'script', 'facebook-jssdk');
  },

  async login() {
    const req = await this.getInfo();
    await socialLogin(req);
  },

  async signup() {
    const req = await this.getInfo();
    await socialSignup(req);
  },

  getInfo() {
    return new Promise((resolve, reject) => {
      window.FB.getLoginStatus(() => {
        // 첫 시도
        window.FB.login(
          response => {
            if (response.status === 'connected') {
              // const accessToken = response.authResponse.accessToken;
              window.FB.api('/me', { fields: 'id, name, email, picture' }, res => {
                if (!res) {
                  this.LoginFailure();
                }
                const req_body = {
                  userId: res.id,
                  email: res.email,
                  nickname: res.name,
                  profileImage: res.picture.data.url,
                  social: 'FACEBOOK',
                };
                resolve(req_body);
              });
            } else {
              this.LoginFailure();
            }
          },
          { scope: 'public_profile, email' },
        );
      });
    });
  },
  LoginFailure() {
    alert('페이스북 로그인 실패');
  },
};

export default Facebook;
