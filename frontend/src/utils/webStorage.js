const USER_INFO = 'ThxStorage-V1-U';
const TOKEN = 'ThxStorage-V1-T';
const STORE_ID = 'ThxStoreage-V1-S';
// 인코딩, 디코딩 함수
const makeIncodeValue = (key, value) => {
  const data = encodeURIComponent(JSON.stringify(value));
  console.log(data);
  localStorage.setItem(key, btoa(data));
};

const returnDecodeValue = value => {
  const decode = atob(value);
  const data = JSON.parse(decodeURIComponent(decode));
  return data;
};

// 로컬스토리지 관련 함수(user)
const saveUserToLocalStorage = user => {
  makeIncodeValue(USER_INFO, user);
};

const getUserFromLocalStorage = () => {
  if (localStorage.getItem(USER_INFO)) {
    return returnDecodeValue(localStorage.getItem(USER_INFO));
  }
  return null;
};

// 로컬스토리지 관련 함수(token)
const saveTokenToLocalStorage = token => {
  makeIncodeValue(TOKEN, token);
};

const getTokenFromLocalStorage = () => {
  if (localStorage.getItem(TOKEN)) {
    return returnDecodeValue(localStorage.getItem(TOKEN));
  }
  return null;
};

// 로컬스토리지 관련 함수(user)
const saveStoreIdToLocalStorage = storeId => {
  makeIncodeValue(STORE_ID, storeId);
};

const getStoreIdFromLocalStorage = () => {
  if (localStorage.getItem(STORE_ID)) {
    return returnDecodeValue(localStorage.getItem(STORE_ID));
  }
  return null;
};

const clearLocalStorageItem = item => {
  localStorage.setItem(item, '');
};

const saveSessionStorage = (key, value) => {
  sessionStorage.setItem(key, JSON.stringify(value));
};

const getSessionStorage = key => {
  return JSON.parse(sessionStorage.getItem(key));
};

const deleteSessionStorage = key => {
  sessionStorage.removeItem(key);
};

export {
  USER_INFO,
  TOKEN,
  saveUserToLocalStorage,
  getUserFromLocalStorage,
  saveTokenToLocalStorage,
  getTokenFromLocalStorage,
  saveStoreIdToLocalStorage,
  getStoreIdFromLocalStorage,
  saveSessionStorage,
  getSessionStorage,
  deleteSessionStorage,
  clearLocalStorageItem,
};
