import { loginUser } from '@/api/auth';

export default {
  async LOGIN({ commit }, loginData) {
    // 에러처리 : LoginForm.vue, SignupForm.vue
    const { data } = await loginUser(loginData);
    commit('setToken', data.accessToken);
    // commit('setUserInfo', data.data);
    return data;
  },
};
