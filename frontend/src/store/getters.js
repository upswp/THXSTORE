export default {
  getUserInfo(state) {
    return state.userInfo;
  },
  getTempUserInfo(state) {
    return state.tmpUserInfo;
  },
  getToken(state) {
    return state.token;
  },
  isMainDrawerOpend(state) {
    return state.mainDrawerOpen;
  },
};