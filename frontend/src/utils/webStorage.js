const USER_INFO = 'ThxStorage-V1-U';
const TOKEN = 'ThxStorage-V1-T';

// 인코딩, 디코딩 함수
const makeIncodeValue = (key, value) => {
  // _.*- 을 제외한 특수문자는 사용하지 않을 것이기 때문에 encodeURI는 사용하지 않는다.
  // const data = encodeURIComponent(JSON.stringify(value));

  const data = JSON.stringify(value);
  localStorage.setItem(key, btoa(data));
};

const returnDecodeValue = value => {
  const decode = atob(value);
  // const data = JSON.parse(decodeURIComponent(decode));
  const data = JSON.parse(decode);
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
  saveUserToLocalStorage,
  getUserFromLocalStorage,
  saveTokenToLocalStorage,
  getTokenFromLocalStorage,
  saveSessionStorage,
  getSessionStorage,
  deleteSessionStorage,
};
