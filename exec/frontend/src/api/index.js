import axios from 'axios';
import { setInterceptors } from '@/api/common/interceptors';

const createInstance = url =>
  axios.create({
    baseURL: `${process.env.VUE_APP_SERVER_API_URL}${url}`,
  });
const createInstanceWithToken = url => setInterceptors(createInstance(url));

export { createInstance, createInstanceWithToken };
