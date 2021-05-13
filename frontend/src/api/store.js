import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

/**
 * partial update를 고려해서 아래 항목 전체가 들어갈 필요는 없음 (단, storeId는 반드시 들어가야함)
 * @typedef {object} StoreSideData
 * @property {number} storeId - 대상이되는 가게의 아이디
 * @property {string} storeCategory - 가게 카테고리
 * @property {string} openTime - 영업 시작 시간
 * @property {string} closeTime - 영업 종료 시간
 * @property {string} closeDay - 휴무일
 * @property {string} introduce - 가게 소개
 * @property {file} thumbnailImg - 가게 썸네일 이미지
 * @property {file} logo - 가게 로고
 */

/**
 * 가게의 기본 정보
 * @typedef {object} StoreBaseData
 * @property {number} storeId - 대상이되는 가게의 아이디
 * @property {string} name - 가게 이름
 * @property {string} role - 현재 사용자의 역할
 * @property {string} mainAddress - 메인 주소
 * @property {string} subAddress - 부 주소
 * @property {number} lat - 위도
 * @property {number} lon - 경도
 * @property {string} phoneNumber - 가게 전화 번호
 * @property {string} license - 사업자 등록 번호
 * @property {file} licenseImg - 사업자 등록증 이미지
 */

/**
 * 가게 전체 정보
 * @typedef {object} StoreTotalData
 * @property {StoreBaseData} baseInfo - 가게 기본 정보
 * @property {StoreSideData} sideInfo - 가게 사이드 정보
 */

/**
 * 가게 사이드 정보 수정
 * @typedef {function} updateStoreSideInfo
 * @param {StoreSideData} storeData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateStoreSideInfo = storeSideData => privateAPI.patch('', storeSideData);

/**
 * 가게 정보 불러오기
 * @typedef {function} getStoreInfo
 * @returns {Promise<StoreTotalData>} storeTotalData
 */
const getStoreInfo = () => privateAPI.get('');

/**
 * 가게 아이디 불러오기
 * @typedef {function} getStoreId
 * @returns {Promise<Number>} storeId
 */
const getStoreId = () => privateAPI.get('id/');

export { updateStoreSideInfo, getStoreInfo, getStoreId };
