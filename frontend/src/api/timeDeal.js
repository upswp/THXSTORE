import { createInstance, createInstanceWithToken } from '@/api';
const privateAPIForUser = createInstanceWithToken('api/store/user/');
const privateAPIForStore = createInstanceWithToken('api/store/timedeal/');
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
const getTimeDeal = storeId => privateAPIForStore.get(`${storeId}`);

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
const registerTimeDeal = timeDealData => privateAPIForStore.post('', timeDealData);

/**
 * 타임딜 아이템 정보
 * @typedef {object} TimeDealItem
 * @property {number} id - 상품 아이디
 * @property {string} name - 상품명
 * @property {number} price - 정가
 * @property {string} productImg - 상품 이미지
 * @property {string} amount - 판매 단위
 * @property {string} rate - 할인율
 * @property {string} stock - 재고
 * @property {string} introduce - 상품 소개
 */
/**
 * 타임딜 주도 가게 정보
 * @typedef {object} StoreInfo
 * @property {number} id - 가게 아이디
 * @property {string} storeCategory - 가게 카테고리
 * @property {number} phoneNumber - 가게 전화번호
 * @property {string} mainAddress - 가게 메인주소
 * @property {string} subAddress - 가게 상세주소
 * @property {string} openTime - 영업 시작 시간
 * @property {string} closeTime - 영업 종료 시간
 * @property {string} closeDay - 휴무일
 * @property {string} introduce - 가게 소개
 * @property {string} thumbImg - 가게 썸네일
 * @property {string} logo - 가게 로고
 * @property {float} distance - 사용자와 가게 간의 거리
 * @property {string} timeDealStart - 타임딜 시작시간
 * @property {Array<TimeDealItem>} timeDealList - 타임딜 등록 아이템들
 */
/**
 * 사용자 입장에서 타임딜 주도 가게 정보 조회
 * @typedef {function} getTimeDealFeed
 * @property {number} distance - 거리
 * @returns {Promise<TimeDeal>}
 */
const getTimeDealFeed = distance =>
  privateAPIForUser.get('', {
    params: {
      distance,
    },
  });

export { getTimeDeal, registerTimeDeal, getTimeDealFeed };
