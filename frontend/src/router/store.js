const routesForStore = [
  {
    path: '/store',
    name: 'store',
    component: () => import('@/views/StorePage.vue'),
    redirect: '/store/info',
    children: [
      {
        path: 'info',
        name: 'storeInfo',
        component: () => import('@/components/store/StoreInfo.vue'),
      },
      {
        path: 'product',
        name: 'storeProduct',
        component: () => import('@/components/store/StoreProduct.vue'),
      },
      {
        path: 'time-deal',
        name: 'storeTimeDeal',
        component: () => import('@/components/store/StoreTimeDeal.vue'),
      },
      {
        path: 'live',
        name: 'storeLive',
        component: () => import('@/components/store/StoreLive.vue'),
      },
    ],
  },
];
export default routesForStore;
