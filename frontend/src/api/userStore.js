import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

const getStoreInfo = storeId =>
  publicAPI.get('info/', {
    params: {
      storeId: storeId,
    },
  });

const getStoreMenu = storeId =>
  publicAPI.get('user/product/', {
    params: {
      storeId: storeId,
    },
  });
export { getStoreInfo, getStoreMenu };
