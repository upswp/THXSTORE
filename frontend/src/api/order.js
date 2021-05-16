import { createInstance, createInstanceWithToken } from '@/api/index.js';
const publicAPI = createInstance('api/order/');
const privateAPI = createInstanceWithToken('api/order/');

/**
 * 품목 정보
 * @typedef {object} Product
 * @property {number} price - 정가
 * @property {number} count - 주문 갯수
 * @property {string} rate - 할인율
 * @property {string} productName - 품목 이름
 */

/**
 * 주문 정보
 * @typedef {object} Order
 * @property {number} userId - 주문자 id
 * @property {string} nickname - 주문자 닉네임
 * @property {number} storeId - 주문할 스토어 아이디
 * @property {string} orderTime - 주문 시각
 * @property {string} reservationStatus - 주문 상태
 * @property {string} amount - 메뉴 단위
 * @property {Array<Product>} reservationGroups - 주문된 품목 전체 정보
 */
/**
 * 판매자 입장에서 주문 내역 전체 조회
 * @typedef {function} getTotalOrders
 * @returns {Promise<Array<Order>>} totalOrders
 */
const getTotalOrders = () => privateAPI.get('reservation/store');

/**
 * 주문 상태 변경 정보
 * @typedef {object} OrderStatus
 * @property {number} userId - 주문자 id
 * @property {number} storeId - 가게 id
 * @property {string} reservationStatus - 주문 상태
 */
/**
/**
 * 판매자 입장에서 주문 상태 변경
 * @typedef {function} setReservationStatus
 * @param {OrderStatus} orderStatus
 * @returns {Promise<Boolean>} isChanged
 */
const setReservationStatus = orderStatus => privateAPI.put('reservation/status', orderStatus);
/**
 * 판매자 입장에서 주문 취소
 * @typedef {function} cancelOrder
 * @param {number} userId
 * @param {number} storeId
 * @returns {Promise<Boolean>} isCanceled
 */
const cancelOrder = userId =>
  privateAPI.delete('reservation/store', {
    params: {
      memberId: userId,
    },
  });

export { getTotalOrders, setReservationStatus, cancelOrder };
