import { loginUser } from '@/api/auth';
import { saveUserToLocalStorage, saveTokenToLocalStorage } from '@/utils/webStorage';
export default {
  async LOGIN({ commit }, loginData) {
    // 에러처리 : LoginForm.vue, SignupForm.vue
    const { data } = await loginUser(loginData);
    commit('setToken', data.accessToken);
    saveTokenToLocalStorage(data.accessToken);
    const userData = {
      id: data.id,
      email: data.email,
      location: data.location,
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
