export default {
  setTempUserInfo(state, tmpUserInfo) {
    state.tempUserInfo = Object.assign(state.tempUserInfo, tmpUserInfo);
  },
  setUserInfo(state, userInfo) {
    state.userInfo = Object.assign(state.userInfo, userInfo);
  },
  clearUserInfo(state) {
    state.userInfo = {};
  },
  setToken(state, token) {
    state.token = token;
  },
  toggleMainDrawerOpen(state) {
    state.mainDrawerOpen = !state.mainDrawerOpen;
  },
  setSpinnerState(state, status) {
    state.spinnerOn = status;
  },
  setStoreId(state, storeId) {
    state.storeId = storeId;
  },
  setRewriteBaseInfo(state, status) {
    state.rewriteBaseInfo = status;
  },
  setWatchedStore(state, store) {
    state.watchedStore = store;
  },
};
