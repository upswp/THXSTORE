import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/user/');
const privateAPI = createInstanceWithToken('api/user/');

/**
 * partial update를 고려해서 아래 항목 전체가 들어갈 필요는 없음
 * @typedef {object} UpdateData
 * @property {number} id - 사용자 id (필수)
 * @property {string} nickname - 별명
 * @property {string} profileImage - 프로필 이미지
 * @property {string} location - 사용자 위치
 * @property {string} phone - 사용자 핸드폰
 * @property {float} lat - 경도
 * @property {float} lon - 위도
 */
/**
 * 회원정보수정
 * @typedef {function} updateProfile
 * @param {UpdateData} updateData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateProfile = userData => privateAPI.patch('', userData);

export { updateProfile };
