import axios from 'axios';

const instance = axios.create({
  baseURL: 'https://dapi.kakao.com/v2/local/search/address.json',
});

const findAddressAPI = addr =>
  instance.get('', {
    headers: {
      Authorization: `KakaoAK ${process.env.VUE_APP_KAKAO_REST_API_KEY}`,
    },
    params: {
      query: addr,
    },
  });
export { findAddressAPI };
