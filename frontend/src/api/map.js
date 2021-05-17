import axios from 'axios';

const addrToPos = axios.create({
  baseURL: 'https://dapi.kakao.com/v2/local/search/address.json',
});
const posToAddr = axios.create({
  baseURL: 'https://dapi.kakao.com/v2/local/geo/coord2address.json',
});
// 도로명을 넣으면, 위치 좌표 반환
const findAddressAPI = addr =>
  addrToPos.get('', {
    headers: {
      Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_REST_API_KEY}`,
    },
    params: {
      query: addr,
    },
  });
// 위치 좌표를 넣으면, 도로명 반환
const findLocationAPI = (lat, lon) =>
  posToAddr.get('', {
    headers: {
      Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_REST_API_KEY}`,
    },
    params: {
      x: lon,
      y: lat,
    },
  });
export { findAddressAPI, findLocationAPI };
