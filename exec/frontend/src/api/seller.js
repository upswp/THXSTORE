import { createInstanceWithToken } from '@/api/index.js';

const privateAPI = createInstanceWithToken('api/store/');
/**
 * @typedef {object} BaseInfo
 * @property {number} storeId - 가게 고유 번호
 * @property {string} name - 가게 상호명
 * @property {string} mainAddress - 가게 등록지 주소
 * @property {string} subAddress - 상세 주소
 * @property {string} phoneNum - 가게 전화 번호
 * @property {string} license - 사업자 등록 번호
 * @property {string} licenseImg - 사업자 등록증 이미지 주소
 * @property {number} lat - 가게 등록지 위도
 * @property {number} lon - 가게 등록지 경도
 * @property {string} checkStore - 등록/수정 처리 진행상황 (progressStatus로 변경 예정)
 * @property {boolean} timeDealCheck - 타임 딜 진행 여부 (isTimeDealActive로 변경 예정)
 */
/**
 * @typedef {object} StoreInfo
 * @property {string} openTime - 영업 시작 시간 (hh:mm)
 * @property {string} closeTime - 영업 종료 시간 (hh:mm)
 * @property {string} closeDay - 휴무일 (Mon|Tue|... 요일 구분은 |로 한다) (dayOff로 변경 예정)
 * @property {string} storeCategory - 가게 카테고리
 * @property {string} logo - 가게 로고 이미지 주소 (logoImg로 변경 예정)
 * @property {string} thumbImg - 썸네일 이미지 주소
 * @property {string} introduce - 가게 소개 문구
 */
/**
 * @typedef {object} StoreInfo
 * @property {BaseInfo} baseInfo - 가게 고유 정보, 관리자 승인을 받아야 변경이 가능
 * @property {string} openTime - 가게 부가 정보, 관리자 승인 없이 변경 가능
 */
/**
 * @typedef {object} DataForRegistration
 * @property {string} name - 가게 상호명
 * @property {string} mainAddress - 가게 등록지 주소
 * @property {string} subAddress - 상세 주소
 * @property {string} phoneNum - 가게 전화 번호
 * @property {string} license - 사업자 등록 번호
 * @property {file} licenseImg - 사업자 등록증 이미지 파일
 * @property {number} lat - 가게 등록지 위도
 * @property {number} lon - 가게 등록지 경도
 */
/**
 * @typedef {object} DataForModification
 * @property {number} storeId - 가게 고유 번호
 * @property {string} name - 가게 상호명
 * @property {string} mainAddress - 가게 등록지 주소
 * @property {string} subAddress - 상세 주소
 * @property {string} phoneNum - 가게 전화 번호
 * @property {string} license - 사업자 등록 번호
 * @property {file} licenseImg - 사업자 등록증 이미지 파일
 * @property {number} lat - 가게 등록지 위도
 * @property {number} lon - 가게 등록지 경도
 */

/**
 * 판매자의 가게 정보 조회
 * @typedef {function} getMyStoreInfo
 * @returns {Promise<StoreInfo>} - 가게 정보
 */
const getMyStoreInfo = () => privateAPI.get('');

/**
 * 판매자가 가게 등록 요청
 * @typedef {function} registerStore
 * @param {DataForRegistration} dataForRegistration
 * @returns {Promise<Boolean>} - 가게 등록 신청 성공 여부
 */
const registerStore = dataForRegistration => privateAPI.post('', dataForRegistration);

/**
 * 판매자가 가게 기본 정보 수정 요청
 * @typedef {function} modifyStoreInfo
 * @param {DataForModification} dataForModification
 * @returns {Promise<Boolean>} - 가게 등록 신청 성공 여부
 */
const modifyStoreInfo = dataForModification => privateAPI.put('', dataForModification);

// 관리자가 판매자 신청내역 확인
const getStoreApplicationList = () => privateAPI.get('application/');

// 관리자가 판매자 수정내역 확인
const getStoreModificationlist = () => privateAPI.get('modify/');

// 관리자가 판매자 등록을 승인 또는 거절
const answerStoreAplication = (storeId, response) => privateAPI.post(`application/${response}/`, { storeId });

// 관리자가 판매자 수정을 승인 또는 거절
const answerStoreModification = (tempStoreId, response) => privateAPI.post(`modify/${response}/`, { tempStoreId });

/**
 * 스토어 등록이 반려되었을 때 신청자가 이를 확인 (이후, 스토어 등록 정보 삭제)
 * @typedef {function} acceptApplicationRejected
 * @returns {Promise<Boolean>} - 반려 확인을 정상 확인했는지 여부
 */
const acceptApplicationRejected = () => privateAPI.post('application/confirm/');

/**
 * 스토어 수정이 반려되었을 때 신청자가 이를 확인 (이후, 스토어 수정 정보 삭제)
 * @typedef {function} acceptModificationRejected
 * @returns {Promise<Boolean>} - 반려 확인을 정상 확인했는지 여부
 */
const acceptModificationRejected = () => privateAPI.post('modify/confirm/');

export {
  registerStore,
  getStoreApplicationList,
  answerStoreAplication,
  getMyStoreInfo,
  acceptApplicationRejected,
  modifyStoreInfo,
  getStoreModificationlist,
  answerStoreModification,
  acceptModificationRejected,
};
