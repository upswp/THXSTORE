import { createInstance, createInstanceWithToken } from '@/api/index.js';
const publicAPI = createInstance('api/order/');
const privateAPI = createInstanceWithToken('api/order/');

// 사용자 입장에서 주문 내역 전체 조회
const getUserOrders = () => privateAPI.get('reservation/member/');

export { getUserOrders };
