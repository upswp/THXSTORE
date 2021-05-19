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
  getStoreId(state) {
    return state.storeId;
  },
};
