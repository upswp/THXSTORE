import { loginUser } from '@/api/auth';
import { saveUserToLocalStorage, saveTokenToLocalStorage, clearLocalStorageItem } from '@/utils/webStorage';
export default {
  async LOGIN({ commit }, loginData) {
    // 에러처리 : LoginForm.vue, SignupForm.vue
    const { data } = await loginUser(loginData);
    commit('setToken', data.accessToken);
    clearLocalStorageItem('ThxStorage-V1-U');
    clearLocalStorageItem('ThxStoreage-V1-S');
    clearLocalStorageItem('ThxStoreage-V1-T');
    commit('setStoreId', '');
    saveTokenToLocalStorage(data.accessToken);
    const userData = {
      address: data.address ? data.address : '대전광역시 유성구 덕명동 124',
      email: data.email,
      id: data.id,
      lat: data.lat ? data.lat : 36.354946759143,
      lon: data.lon ? data.lon : 127.29980994578,
      nickname: data.nickname,
      phoneNumber: data.phoneNumber ? data.phoneNumber : '-',
      profileImage: data.profileImage ? data.profileImage : require('@/assets/image/basic_profile.jpg'),
      role: data.role,
      social: data.social,
    };
    commit('setUserInfo', userData);
    saveUserToLocalStorage(userData);
    return data;
  },
};
