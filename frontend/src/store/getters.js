export default {
  getUserInfo(state) {
    return state.userInfo;
  },
  getTempUserInfo(state) {
    return state.tempUserInfo;
  },
  getToken(state) {
    return state.token;
  },
  isMainDrawerOpend(state) {
    return state.mainDrawerOpen;
  },
  getSpinnerState(state) {
    return state.spinnerOn;
  },
  getWatchedStore(state) {
    return state.watchedStore;
  },
};
