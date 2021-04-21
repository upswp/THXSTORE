export default {
  setTempUserInfo(state, tmpUserInfo) {
    state.tempUserInfo = Object.assign(state.tempUserInfo, tmpUserInfo);
  },
  setUserInfo(state, userInfo) {
    state.tempUserInfo = Object.assign(state.tempUserInfo, userInfo);
  },
  setToken(state, token) {
    state.token = token;
  },
};
