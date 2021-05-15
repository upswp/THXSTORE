import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/order/reservation/');
const privateAPI = createInstanceWithToken('api/order/reservation/');
/**
 * 주문 단편 정보
 * @typedef {object} Order
 * @property {string} price - 품목 가격
 * @property {string} count - 주문 수량
 * @property {string} orderTime - 주문 요청 시간
 * @property {string} productName - 품목 이름
 * @property {string} reservationStatus - 주문 상황 // DEFAULT, ACCEPT, STAND_BY, FINISH
 */
/**
 * 주문자 정보
 * @typedef {object} OrderDetail
 * @property {string} userId - 주문자 id
 * @property {Order} order - 주문 정보
 */
/**
 * 판매자 입장에서 타임딜 조회
 * @typedef {function} getOrderList
 * @property {string} storeId - 가게 id
 * @returns {Promise<Array<OrderDetail>>} orderList
 */
const getOrderList = storeId => publicAPI.get(`store/${storeId}`);
// 토큰이 안들어가도 되는건가?
