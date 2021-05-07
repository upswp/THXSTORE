import { createInstance, createInstanceWithToken } from '@/api/index.js';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

// 판매자 상태 조회
const getCheckOfStore = () => privateAPI.get('');

// 판매자 등록
const registerStore = registerData => privateAPI.post('', registerData);
// 관리자가 판매자 내역 확인
const getStoreEnrollmentList = () => privateAPI.get('application/');
// 관리자가 판매자 등록을 승인
const approveStoreEnrollment = pass => privateAPI.post('application/success/', pass);
// 관리자가 판매자 등록을 거절
const retireStoreEnrollment = fail => privateAPI.post('application/fail/', fail);
// 반려되었을 때 스토어 등록 정보 삭제
const deletePreStoreEnrollment = () => privateAPI.post('application/confirm/');
export {
  registerStore,
  getStoreEnrollmentList,
  approveStoreEnrollment,
  retireStoreEnrollment,
  getCheckOfStore,
  deletePreStoreEnrollment,
};
