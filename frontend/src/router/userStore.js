const routesForUserStore = [
  {
    path: '/deal-for-u',
    name: 'userstore',
    component: () => import('@/views/UserStorePage.vue'),
    children: [
      {
        path: 'menu/:storeId',
        name: 'menu',
        component: () => import('@/components/storeForUser/UserStoreMenuList.vue'),
      },
      {
        path: 'info/:storeId',
        name: 'info',
        component: () => import('@/components/storeForUser/UserStoreInfo.vue'),
      },
      {
        path: 'timedeal/:storeId',
        name: 'timedeal',
        component: () => import('@/components/storeForUser/TimedealList.vue'),
      },
      {
        path: 'live/:storeId',
        name: 'live',
        component: () => import('@/components/storeForUser/UserStoreLiveCommerce.vue'),
      },
    ],
  },
];

export default routesForUserStore;
