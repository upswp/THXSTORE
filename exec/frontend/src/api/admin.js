import { createInstanceWithToken } from '@/api/index.js';

const privateAPI = createInstanceWithToken('api/store/');
/**
 * @typedef {object} StoreInfo
 * @property {string} checkStore - 등록/수정 처리 진행상황 (progressStatus로 변경 예정)
 * @property {string} closeDay - 휴무일 (Mon|Tue|... 요일 구분은 |로 한다) (dayOff로 변경 예정)
 * @property {string} closeTime - 영업 종료 시간 (hh:mm)
 * @property {number} id - 가게 고유 번호 (storeId로 변경 예정)
 * @property {string} introduce - 가게 소개 문구
 * @property {number} lat - 가게 등록지 위도
 * @property {string} license - 사업자 등록 번호
 * @property {string} licenseImg - 사업자 등록증 이미지 주소
 * @property {string} logo - 가게 로고 이미지 주소 (logoImg로 변경 예정)
 * @property {number} lon - 가게 등록지 경도
 * @property {string} mainAddress - 가게 등록지 주소
 * @property {string} name - 가게 상호명
 * @property {string} openTime - 영업 시작 시간 (hh:mm)
 * @property {string} phoneNum - 가게 전화 번호
 * @property {string} subAddress - 상세 주소
 * @property {boolean} timeDealCheck - 타임 딜 진행 여부 (isTimeDealActive로 변경 예정)
 * @property {string} storeCategory - 가게 카테고리
 * @property {string} thumbImg - 썸네일 이미지 주소
 */

/**
 * 관리자가 판매자 등록 요청 목록 확인
 * @typedef {function} getStoreApplicationList
 * @returns {Promise<StoreInfo>} - 등록 요청 목록
 */
const getStoreApplicationList = () => privateAPI.get('application/');

/**
 * 관리자가 판매자 수정 요청 목록 확인
 * @typedef {function} getStoreModificationlist
 * @returns {Promise<StoreInfo>} - 수정 요청 목록
 */
const getStoreModificationlist = () => privateAPI.get('modify/');

/**
 * 관리자가 판매자 등록을 승인 또는 거절
 * @typedef {function} answerStoreAplication
 * @param {number} storeId - 가게 고유 번호
 * @param {string} response - 승인 (success) 또는 거절 (fail)
 * @returns {Promise<Boolean>} - api 요청 성공 여부
 */
const answerStoreAplication = (storeId, response) => privateAPI.post(`application/${response}/`, { storeId });

/**
 * 관리자가 판매자 수정을 승인 또는 거절
 * @typedef {function} answerStoreModification
 * @param {number} storeId - 가게 고유 번호
 * @param {string} response - 승인 (success) 또는 거절 (fail)
 * @returns {Promise<Boolean>} - 가게 등록 신청 성공 여부
 */
const answerStoreModification = (tempStoreId, response) => privateAPI.post(`modify/${response}/`, { tempStoreId });

export { getStoreApplicationList, getStoreModificationlist, answerStoreAplication, answerStoreModification };
