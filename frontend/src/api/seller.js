import { createInstance, createInstanceWithToken } from '@/api/index.js';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

// 판매자 상태 조회
const getCheckOfStore = () => privateAPI.get('');

// 판매자 등록
const registerStore = registerData => privateAPI.post('', registerData);
// 관리자가 판매자 신청내역 확인
const getStoreEnrollmentList = () => privateAPI.get('application/');
// 관리자가 판매자 등록을 승인
const approveStoreEnrollment = pass => privateAPI.post('application/success/', pass);
// 관리자가 판매자 등록을 거절
const retireStoreEnrollment = fail => privateAPI.post('application/fail/', fail);
// 반려되었을 때 스토어 등록 정보 삭제
const deletePreStoreEnrollment = () => privateAPI.post('application/confirm/');

// 판매자가 기본정보를 수정
const modifyStoreBaseInfo = modifiedData => privateAPI.put('', modifiedData);
// 관리자가 판매자 수정내역 확인
const getStoreModifyList = () => privateAPI.get('modify/');
// 관리자가 판매자 수정을 승인
const approveStoreModification = pass => privateAPI.post('modify/success/', pass);
// 관리자가 판매자 수정을 거절
const retireStoreModification = fail => privateAPI.post('modify/fail/', fail);
// 반려되었을 때 스토어 수정 정보 삭제
const deletePreStoreModification = () => privateAPI.post('modify/confirm/');
export {
  registerStore,
  getStoreEnrollmentList,
  approveStoreEnrollment,
  retireStoreEnrollment,
  getCheckOfStore,
  deletePreStoreEnrollment,
  modifyStoreBaseInfo,
  getStoreModifyList,
  approveStoreModification,
  retireStoreModification,
  deletePreStoreModification,
};
