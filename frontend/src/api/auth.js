import { instance, instanceAuth } from '@/api';

/**
 * @typedef {object} LoginData
 * @property {string} email - 로그인 이메일 (일반 유저)
 * @property {string} password - 로그인 비밀번호
 * @property {string} id - 로그인 아이디 (소셜 로그인)
 * @property {string} social - 소셜 구분 (소셜 로그인), 아닐 경우 공란
 */

/**
 * @typedef {object} RegisterData
 * @property {string} id - 회원가입 아이디
 * @property {string} password - 회원가입 비밀번호
 * @property {string} email - 회원가입 이메일
 * @property {string} nickname - 회원가입 별명
 * @property {string} social - 회원가입 소셜 가입 플랫폼
 */

/**
 * @typedef {object} User
 * @property {string} email - 이메일
 * @property {string} id - 아이디
 * @property {string} nickname - 별명
 * @property {string} profile - 프로필 이미지 경로
 * @property {string} social - 소셜 계정 여부. 소셜 가입이 아닐 때는 null
 * @property {string} token - 로그인 token
 */

/**
 * 회원가입
 * @typedef {function} registerUser
 * @param {RegisterData} registerData
 * @returns {Promise<Boolean>} isRegistered
 */
const registerUser = registerData => instance.post('', registerData);

/**
 * 로그인
 * @typedef {function} loginUser
 * @param {LoginData} loginData
 * @returns {Promise<User>} userData
 */
const loginUser = loginData => instance.post('', loginData);
const emailCheck = params => instance.get('', params);
const authWithEmailForPwd = userData => instance.post('', userData);
const resetPwd = userData => instanceAuth.put('', userData);

export { registerUser, loginUser, emailCheck, authWithEmailForPwd, resetPwd };
