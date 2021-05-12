import routesForStore from '@/router/store';
import routesForAdmin from '@/router/admin';

const routesForMain = [
  {
    path: '/main',
    redirect: '/main/user',
    name: 'main',
    component: () => import('@/views/MainPage.vue'),
    children: [
      {
        path: '/user',
        name: 'user',
        component: () => import('@/views/UserPage.vue'),
      },
      ...routesForStore,
      ...routesForAdmin,
    ],
  },
];
export default routesForMain;
