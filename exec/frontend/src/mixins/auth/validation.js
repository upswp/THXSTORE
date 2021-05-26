import router from '@/router';
import { validateEmail, validatePwd } from '@/utils/validation';
import { emailCheck } from '@/api/auth';
import { debounce } from 'lodash';

export default {
  // 재사용할 컴포넌트 옵션
  data() {
    return {
      validationMsg: {
        email: '※ 비밀번호 찾기를 위해서, 이메일을 신중히 입력해주세요',
        password1: '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다',
        password2: '※ 실수 방지를 위해 비밀번호를 다시 입력해주세요',
      },
      validationClass: {
        email: '',
        password1: '',
        password2: '',
      },
    };
  },
  computed: {
    btnDisabled() {
      return this.validationClass.email === 'success-msg' &&
        this.validationClass.password1 === 'success-msg' &&
        this.validationClass.password2 === 'success-msg' &&
        this.userData.nickname
        ? false
        : true;
    },
  },
  watch: {
    'userData.email': {
      immediate: true,
      handler(email) {
        if (email === '') {
          this.validationMsg.email = '※ 비밀번호 찾기를 위해서, 이메일을 신중히 입력해주세요';
          this.validationClass.email = '';
        } else if (validateEmail(email)) {
          this.validationMsg.email = '올바른 이메일 형식입니다';
          this.validationClass.email = 'success-msg';
          // 이메일 중복 검사
          debounce(() => {
            emailCheck(email)
              .then(response => {
                if (response.data.check) {
                  this.validationMsg.email = '이미 해당 이메일이 존재합니다';
                  this.validationClass.email = 'alert-msg';
                }
              })
              .catch(error => {
                this.validationMsg.email = '이미 해당 이메일이 존재합니다';
                this.validationClass.email = 'alert-msg';
              });
          }, 750)();
        } else {
          this.validationMsg.email = '이메일 형식이 잘못되었습니다';
          this.validationClass.email = 'alert-msg';
        }
      },
    },
    'userData.password1': function (pwd) {
      if (pwd === '') {
        this.validationMsg.password1 = '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다';
        this.validationClass.password1 = '';
      } else if (validatePwd(pwd)) {
        this.validationMsg.password1 = '올바른 비밀번호 형식입니다';
        this.validationClass.password1 = 'success-msg';
      } else {
        this.validationMsg.password1 = '※ 6~12자 영문 대⋅소문자/숫자의 조합만 허용합니다';
        this.validationClass.password1 = 'alert-msg';
      }
    },
    'userData.password2': function (pwd) {
      if (pwd === '') {
        this.validationMsg.password2 = '※ 실수 방지를 위해 비밀번호를 다시 입력해주세요';
        this.validationClass.password2 = '';
      } else if (pwd === this.userData.password1) {
        this.validationMsg.password2 = '비밀번호가 일치합니다';
        this.validationClass.password2 = 'success-msg';
      } else {
        this.validationMsg.password2 = '비밀번호가 일치하지 않습니다';
        this.validationClass.password2 = 'alert-msg';
      }
    },
  },
  methods: {
    moveToPage(name) {
      router.push({ name });
    },
  },
};
