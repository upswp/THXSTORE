import { loginUser } from '@/api/auth';

export default {
  async LOGIN({ commit }, loginData) {
    // 에러처리 : LoginForm.vue, SignupForm.vue
    const { data } = await loginUser(loginData);
    commit('setUserToken', data.data.token);
    commit('setUser', data.data);
    return data;
  },
  // async KAKAO_LOGIN({ commit }){
  //   const data = await 
  // }
};
