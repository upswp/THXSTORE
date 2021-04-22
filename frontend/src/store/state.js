import { getUserFromLocalStorage, getTokenFromLocalStorage } from '@/utils/webStorage';

export default {
  tempUserInfo: {},
  userInfo: Object.assign(
    {
      userId: '',
      email: '',
      nickname: '',
      social: '',
      profileImage: '',
    },
    getUserFromLocalStorage(),
  ),
  token: getTokenFromLocalStorage() || '',
  mainDrawerOpen: true,
};
