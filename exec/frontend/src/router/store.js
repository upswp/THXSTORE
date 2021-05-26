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
        path: 'deal',
        name: 'storeTimeDeal',
        component: () => import('@/components/store/StoreTimeDeal.vue'),
      },
      {
        path: 'live',
        name: 'storeLive',
        component: () => import('@/components/store/StoreLive.vue'),
      },
      {
        path: 'reservation',
        name: 'storeReservation',
        component: () => import('@/components/store/StoreReservation.vue'),
      },
      {
        path: 'review',
        name: 'storeReview',
        component: () => import('@/components/store/StoreReview.vue'),
      },
    ],
  },
];
export default routesForStore;
