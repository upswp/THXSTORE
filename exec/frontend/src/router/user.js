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
        path: 'recent-reviews',
        name: 'userRecentReviews',
        component: () => import('@/components/user/UserRecentReviews.vue'),
      },
      {
        path: 'store-application',
        name: 'userStoreApplication',
        component: () => import('@/components/user/UserStoreApplication.vue'),
      },
    ],
  },
];
export default routesForUser;
