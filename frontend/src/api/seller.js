import { createInstance, createInstanceWithToken } from '@/api/index.js';

const publicAPI = createInstance('api/seller/');
const privateAPI = createInstanceWithToken('api/seller/');

// 판매자 등록
const registerStore = registerData => privateAPI.post('', registerData);

export { registerStore };
