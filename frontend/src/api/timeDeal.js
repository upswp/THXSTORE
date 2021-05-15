import { createInstance, createInstanceWithToken } from '@/api';
const privateAPI = createInstanceWithToken('api/store/timedeal/');
/**
 * 품목 정보
 * @typedef {object} Product
 * @property {number} productId - 품목 id
 * @property {string} name - 품목 이름
 * @property {string} price - 품목 가격
 * @property {string} productImg - 품목 이미지
 * @property {string} amount - 판매 단위
 * @property {string} introduce - 품목 소개
 * @property {string} rate - 할인율
 * @property {string} stock - 재고
 */
/**
 * 타임딜 정보
 * @typedef {object} TimeDeal
 * @property {string} status - 타임딜 상태 : NORMAL, RESERVATION, PROGRESS, COMPLETE
 * @property {string} startTime - 타임딜 시작 시간
 * @property {Array<Product>} timeDeal - 타임딜 품목 리스트
 */
/**
 * 판매자 입장에서 타임딜 조회
 * @typedef {function} getTimeDeal
 * @property {number} storeId - 가게 id
 * @returns {Promise<TimeDeal>}
 */
const getTimeDeal = storeId => privateAPI.get(`${storeId}`);

/**
 * 타임딜 등록 정보
 * @typedef {object} TimeDealForRegister
 * @property {string} startTime - 타임딜 시작 시간
 * @property {Array<Product>} timeDealList - 타임딜 품목 리스트
 */
/**
 * 타임딜 등록
 * @typedef {function} registerTimeDeal
 * @param {TimeDealForRegister} timeDealData
 * @returns {Promise<Boolean>} isRegistered
 */
const registerTimeDeal = timeDealData => privateAPI.post('', timeDealData);

export { getTimeDeal, registerTimeDeal };
