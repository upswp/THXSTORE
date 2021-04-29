import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/member/');
const privateAPI = createInstanceWithToken('api/member/');

/**
 * partial update를 고려해서 아래 항목 전체가 들어갈 필요는 없음
 * @typedef {object} UpdateData
 * @property {string} nickname - 별명
 * @property {string} profileImage - 프로필 이미지
 * @property {string} location - 사용자 위치
 * @property {string} phone - 사용자 핸드폰
 */
/**
 * 회원정보수정
 * @typedef {function} updateProfile
 * @param {UpdateData} updateData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateProfile = userData => privateAPI.post('not yet', userData);

export { updateProfile };
