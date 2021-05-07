import { createInstance, createInstanceWithToken } from '@/api/index.js';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

// 판매자 등록
const registerStore = registerData => privateAPI.post('', registerData);
// 관리자가 판매자 내역 확인
const getStoreEnrollmentList = () => privateAPI.get('application/');

export { registerStore, getStoreEnrollmentList };
