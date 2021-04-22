import { getUserFromLocalStorage, getTokenFromLocalStorage } from '@/utils/webStorage';

export default {
  tempUserInfo: {},
  userInfo: Object.assign(
    {
      uid: '',
      email: '',
      nickname: '',
      social: '',
      profileImage: '',
    },
    getUserFromLocalStorage(),
  ),
  token: getTokenFromLocalStorage() || '',
};
