import { createInstance, createInstanceWithToken } from '@/api';

const menuGroupAPI = createInstanceWithToken('api/store/group/product/');
const menuGroupListAPI = createInstanceWithToken('api/store/group/productlist/');
const menuListAPI = createInstanceWithToken('api/store/product/');
/**
 * 메뉴 정보
 * @typedef {object} Menu
 * @property {number} productId - 메뉴 아이디
 * @property {string} name - 메뉴 이름
 * @property {string} price - 메뉴 가격
 * @property {string} introduce - 메뉴 소개
 * @property {string} productImg - 메뉴 사진
 * @property {string} amount - 메뉴 단위
 */
/**
 * 메뉴 등록 정보
 * @typedef {object} AddMenu
 * @property {number} groupId - 메뉴 아이디
 * @property {string} name - 메뉴 이름
 * @property {string} price - 메뉴 가격
 *  * @property {string} introduce - 메뉴 소개
 * @property {string} productImg - 메뉴 사진
 * @property {string} amount - 메뉴 단위
 */
/**
 * 그룹 별 메뉴 목록 불러오기
 * @typedef {function} getMenuListByGroup
 * @param {number} groupId
 * @returns {Promise<Array<Menu>>} totalMenuGroup
 */
const getMenuListByGroup = groupId => menuGroupListAPI.get(`${groupId}`);
/**
 * 메뉴 상세 정보 불러오기
 * @typedef {function} getMenu
 * @param {number} productId
 * @returns {Promise<Menu>} menu
 */
const getMenu = productId => menuGroupAPI.get(`${productId}`);
/**
 * 그룹 별 메뉴 생성하기
 * @typedef {function} registerMenuByGroup
 * @param {AddMenu} menuData
 * @returns {Promise<Boolean>} isRegistered
 */
const registerMenuByGroup = menuData => menuGroupAPI.post('', menuData);

/**
 * 메뉴 수정하기
 * @typedef {function} updateMenu
 * @param {Menu} menuData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateMenu = menuData => menuGroupAPI.patch('', menuData);

/**
 * 메뉴 삭제하기
 * @typedef {function} deleteMenu
 * @param {number} productId
 * @returns {Promise<Boolean>} isDeleted
 */
const deleteMenu = productId =>
  menuGroupAPI.delete('', {
    params: {
      productId,
    },
  });
/**
 * 판매자 입장에서 메뉴 전체 조회
 * @typedef {function} getTotalMenu
 * @param {number} storeId
 * @returns {Promise<Menu>} menuItem
 */
const getTotalMenu = storeId => menuListAPI.get(`${storeId}`);

export { getMenuListByGroup, getMenu, registerMenuByGroup, updateMenu, deleteMenu, getTotalMenu };
