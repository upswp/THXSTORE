import { getUserFromLocalStorage, getTokenFromLocalStorage, getStoreIdFromLocalStorage } from '@/utils/webStorage';
const userInfo = Object.assign({}, getUserFromLocalStorage());
if (!userInfo.profileImage) userInfo.profileImage = require('@/assets/image/basic_profile.jpg');
if (!userInfo.address) userInfo.address = '대전광역시 유성구 덕명동 124';
if (!userInfo.lat) userInfo.lat = 36.354946759143;
if (!userInfo.lon) userInfo.lon = 127.29980994578;
if (!userInfo.phoneNumber) userInfo.phoneNumber = '-';

export default {
  tempUserInfo: {},
  userInfo,
  // userInfo: getUserFromLocalStorage() || {},
  token: getTokenFromLocalStorage() || '',
  mainDrawerOpen: false,
  spinnerOn: false,
  storeId: getStoreIdFromLocalStorage() || '',
  rewriteBaseInfo: false,
  watchedStore: null,
};
