import { createInstance, createInstanceWithToken } from '@/api/index.js';
const publicAPI = createInstance('api/order/reservation/');
const privateAPI = createInstanceWithToken('api/order/reservation/');

// 사용자 입장에서 주문 내역 전체 조회
const getUserOrders = () => privateAPI.get('member/');

// 리뷰_ 사용자 입장

// 사용자 입장에서 리뷰 작성
const registerReview = formData => publicAPI.post('review/', formData);
// 사용자 입장에서 리뷰 조회
const getUserReview = userId => publicAPI.get(`review/${userId}`);

// 리뷰_ 스토어 입장

// 스토어 입장에서 리뷰 조회
const getStoreReview = storeId => publicAPI.get(`review/store/${storeId}`);
// 스토어 입장에서 리뷰 답글 작성
const registerStoreAnswer = formData => publicAPI.post('answer/', formData);
export { getUserOrders, registerReview, getUserReview, getStoreReview, registerStoreAnswer };
