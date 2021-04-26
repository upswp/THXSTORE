const routesForMain = [
  {
    path: '/main',
    redirect: '/main/user',
    name: 'main',
    component: () => import('@/views/MainPage.vue'),
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/UserPage.vue'),
      },
    ],
  },
];
export default routesForMain;
