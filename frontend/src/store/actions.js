import { loginUser } from '@/api/auth';
import { saveUserToLocalStorage, saveTokenToLocalStorage } from '@/utils/webStorage';
export default {
  async LOGIN({ commit }, loginData) {
    // 에러처리 : LoginForm.vue, SignupForm.vue
    const { data } = await loginUser(loginData);
    commit('setToken', data.accessToken);
    saveTokenToLocalStorage(data.accessToken);
    const userData = {
      address: data.address,
      email: data.email,
      id: data.id,
      lat: data.lat,
      lon: data.lon,
      nickname: data.nickname,
      phoneNumber: data.phoneNumber,
      profileImage: data.profileImage,
      role: data.role,
      social: data.social,
    };
    commit('setUserInfo', userData);
    saveUserToLocalStorage(userData);
    return data;
  },
};
