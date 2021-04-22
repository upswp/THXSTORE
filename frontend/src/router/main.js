const routesForMain = [
  {
    path: '/main',
    name: 'main',
    component: () => import('@/views/MainPage.vue'),
    children: [
      {
        path: 'profile',
        name: 'profile',
        component: () => import('@/views/profile/ProfilePage.vue'),
      },
      {
        path: 'profile-setting',
        name: 'profileSetting',
        component: () => import('@/views/profile/ProfileSettingPage.vue'),
      },
    ],
  },
];
export default routesForMain;
