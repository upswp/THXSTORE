const routesForUser = [
  {
    path: '/user',
    name: 'user',
    component: () => import('@/views/UserPage.vue'),
    redirect: '/user/profile',
    children: [
      {
        path: 'profile',
        name: 'userProfile',
        component: () => import('@/components/user/UserProfile.vue'),
      },
      {
        path: 'password-reset',
        name: 'userPasswordReset',
        component: () => import('@/components/user/UserPasswordReset.vue'),
      },
      {
        path: 'order-list',
        name: 'userOrderList',
        component: () => import('@/components/user/UserOrderList.vue'),
      },
      {
        path: 'reviews',
        name: 'userReviews',
        component: () => import('@/components/user/UserReviews.vue'),
      },
      {
        path: 'store-application',
        name: 'userStoreApplication',
        component: () => import('@/components/user/UserStoreRequest.vue'),
      },
    ],
  },
];
export default routesForUser;
