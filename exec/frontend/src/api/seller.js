import { createInstance, createInstanceWithToken } from '@/api/index.js';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

/**
 * @typedef {object} sendResultOfAdminDecision
 * @property {string} storeId - 스토어 아이디
 */
/**
 * @typedef {object} registerData
 * @property {string} name - 가게이름
 * @property {string} mainAddress - 가게 주소
 * @property {string} subAddress - 가게 세부주소
 * @property {string} phoneNum - 전화번호
 * @property {string} license - 사업자번호
 * @property {string} licenseImg - 사업자번호 사본
 */

/**
 * @typedef {object} modifiedData
 * @property {string} name - 가게이름
 * @property {string} mainAddress - 가게 주소
 * @property {string} subAddress - 가게 세부주소
 * @property {string} phoneNum - 전화번호
 * @property {string} license - 사업자번호
 * @property {string} licenseImg - 사업자번호 사본
 */

// 판매자 상태 조회
const getMyStore = () => privateAPI.get('');

/**
 * 판매자 등록
 * @typedef {function} registerStore
 * @param {registerData} registerData
 * @returns {promise<Boolean>} isEnrollment
 */
const registerStore = registerData => privateAPI.post('', registerData);

// 관리자가 판매자 신청내역 확인
const getStoreEnrollmentList = () => privateAPI.get('application/');

/**
 * 관리자가 판매자 등록을 승인
 * @param {sendResultOfAdminDecision} pass
 * @returns {promise<Boolean>} ispass
 */
const approveStoreEnrollment = pass => privateAPI.post('application/success/', pass);

/**
 * 관리자가 판매자 등록을 거절
 * @param {sendResultOfAdminDecision} fail
 * @returns {promise<Boolean>} isfail
 */
const retireStoreEnrollment = fail => privateAPI.post('application/fail/', fail);
// 반려되었을 때 스토어 등록 정보 삭제
const confirmApplictaionRejected = () => privateAPI.post('application/confirm/');

// 판매자가 기본정보를 수정
/**
 * @typedef {function} modifyStoreBaseInfo
 * @param {registerData} modifiedData
 * @returns {Promise<Boolean>} isModified
 */
const modifyStoreBaseInfo = modifiedData => privateAPI.put('', modifiedData);
// 관리자가 판매자 수정내역 확인
const getStoreModifyList = () => privateAPI.get('modify/');
/**
 * 관리자가 판매자 수정을 승인
 * @param {sendResultOfAdminDecision} pass
 * @returns {Promise<Boolean>} ismodified
 */
const approveStoreModification = pass => privateAPI.post('modify/success/', pass);
/**
 * 관리자가 판매자 수정을 거절
 * @param {sendResultOfAdminDecision} fail
 * @returns {Promise<Boolean>} ismodified
 */
const retireStoreModification = fail => privateAPI.post('modify/fail/', fail);
// 반려되었을 때 스토어 수정 정보 삭제
const confirmModificationRejected = () => privateAPI.post('modify/confirm/');
export {
  registerStore,
  getStoreEnrollmentList,
  approveStoreEnrollment,
  retireStoreEnrollment,
  getMyStore,
  confirmApplictaionRejected,
  modifyStoreBaseInfo,
  getStoreModifyList,
  approveStoreModification,
  retireStoreModification,
  confirmModificationRejected,
};
