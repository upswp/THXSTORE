import { createInstance, createInstanceWithToken } from '@/api/index.js';
const publicAPI = createInstance('api/order/');
const privateAPI = createInstanceWithToken('api/order/');

// 사용자 입장에서 주문 내역 전체 조회
const getUserOrders = () => privateAPI.get('reservation/member/');

// 사용자 입장에서 리뷰 작성
const registerReview = formData => publicAPI.post('reservation/review/', formData);

// 사용자 입장에서 리뷰 조회
const getRegisterReview = userId => publicAPI.get(`reservation/review/${userId}`);

export { getUserOrders, registerReview, getRegisterReview };
