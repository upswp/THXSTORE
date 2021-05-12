import { createInstance, createInstanceWithToken } from '@/api';

const privateAPI = createInstanceWithToken('api/store/group/product/');

/**
 * 메뉴 정보
 * @typedef {object} Menu
 * @property {number} productId - 메뉴 아이디
 * @property {string} name - 메뉴 이름
 * @property {string} price - 메뉴 가격
 * @property {string} productImg - 메뉴 사진
 * @property {string} amount - 메뉴 단위
 */

/**
 * 그룹 별 메뉴 목록 불러오기
 * @typedef {function} getMenuListByGroup
 * @param {number} groupId
 * @returns {Promise<Array<Menu>>} totalMenuGroup
 */
const getMenuListByGroup = groupId => privateAPI.get(`productlist/${groupId}`);
/**
 * 그룹 별 메뉴 목록 불러오기
 * @typedef {function} getMenu
 * @param {number} productId
 * @returns {Promise<Menu>} menu
 */
const getMenu = productId => privateAPI.get(`${productId}`);
/**
 * 그룹 별 메뉴 생성하기
 * @typedef {function} registerMenuByGroup
 * @param {Menu} menuData (productId 제외)
 * @returns {Promise<Boolean>} isRegistered
 */
const registerMenuByGroup = menuData => privateAPI.post('', menuData);

/**
 * 메뉴 수정하기
 * @typedef {function} updateMenu
 * @param {Menu} menuData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateMenu = menuData => privateAPI.patch('', groupData);

/**
 * 메뉴 삭제하기
 * @typedef {function} deleteMenu
 * @param {number} productId
 * @returns {Promise<Boolean>} isDeleted
 */
const deleteMenu = productId =>
  privateAPI.delete('', {
    params: {
      productId,
    },
  });

export { getMenuListByGroup, getMenu, registerMenuByGroup, updateMenu, deleteMenu };
