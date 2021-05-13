import { createInstance, createInstanceWithToken } from '@/api';
import { getStoreIdFromLocalStorage } from '@/utils/webStorage';

const privateAPI = createInstanceWithToken('api/store/group/');
const storeId = getStoreIdFromLocalStorage();

/**
 * 메뉴 그룹 정보
 * @typedef {object} MenuGroup
 * @property {number} groupId - 그룹 아이디
 * @property {string} name - 그룹 이름
 */

/**
 * 메뉴 그룹 목록 불러오기
 * @typedef {function} getMenuGroups
 * @returns {Promise<Array<MenuGroup>>} totalMenuGroup
 */
const getMenuGroups = () => privateAPI.get(`${storeId}`);

/**
 * 메뉴 그룹 생성하기
 * @typedef {function} registerMenuGroup
 * @param {string} name
 * @returns {Promise<Boolean>} isRegistered
 */
const registerMenuGroup = name =>
  privateAPI.post('', {
    storeId,
    name,
  });

/**
 * 메뉴 그룹 수정하기
 * @typedef {function} updateMenuGroup
 * @param {MenuGroup} groupData
 * @returns {Promise<Boolean>} isUpdated
 */
const updateMenuGroup = groupData => privateAPI.put('', groupData);

/**
 * 메뉴 그룹 삭제하기
 * @typedef {function} deleteMenuGroup
 * @param {number} groupId
 * @returns {Promise<Boolean>} isDeleted
 */
const deleteMenuGroup = groupId =>
  privateAPI.delete('', {
    params: {
      groupId,
    },
  });

export { getMenuGroups, registerMenuGroup, updateMenuGroup, deleteMenuGroup };
