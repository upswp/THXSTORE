import { createInstance, createInstanceWithToken } from '@/api';

const publicAPI = createInstance('api/store/');
const privateAPI = createInstanceWithToken('api/store/');

const getStoreInfo = storeId =>
  publicAPI.get('info/', {
    params: {
      storeId: storeId,
    },
  });

export { getStoreInfo };
