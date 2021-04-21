import {
  getUserFromLocalStorage,
  getTokenFromLocalStorage,
}from '@/utils/webStorage';

export default {
  tempUserInfo: {},
  userInfo:Object.assign({
    id: '',
    nickname:'',
    email:'',
    profileImg:'',
  }, getUserFromLocalStorage()),
  token: getTokenFromLocalStorage() || '',
};
