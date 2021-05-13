import { getUserFromLocalStorage, getTokenFromLocalStorage } from '@/utils/webStorage';

export default {
  tempUserInfo: {},
  userInfo: Object.assign(
    {
      userId: '',
      email: '',
      nickname: '',
      social: '',
      profileImage: require('@/assets/image/basic_profile.jpg'),
    },
    getUserFromLocalStorage(),
  ),
  // userInfo: getUserFromLocalStorage() || {},
  token: getTokenFromLocalStorage() || '',
  mainDrawerOpen: false,
  spinnerOn: false,
  storeId: null,
  rewriteBaseInfo: false,
};
