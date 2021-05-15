const routesForUserStore = [
  {
    path: '/user-store',
    name: 'userstore',
    redirect: 'user-store/user-store-menu-list',
    component: () => import('@/views/UserStorePage.vue'),
    children: [
      {
        path: 'menu-list/:storeId',
        name: 'menu-list',
        component: () => import('@/components/storeForUser/UserStoreMenuList.vue'),
      },
      {
        path: 'info/:storeId',
        name: 'info',
        component: () => import('@/components/storeForUser/UserStoreInfo.vue'),
      },
      {
        path: 'timedeal-list/:storeId',
        name: 'timedea-lList',
        component: () => import('@/components/storeForUser/TimedealList.vue'),
      },
      {
        path: 'live-commerce/:storeId',
        name: 'live-Commerce',
        component: () => import('@/components/storeForUser/UserStoreLiveCommerce.vue'),
      },
    ],
  },
];

export default routesForUserStore;
