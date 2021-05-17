import routesForStore from '@/router/store';
import routesForAdmin from '@/router/admin';
import routesForUserStore from '@/router/userStore';

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
      ...routesForUserStore,
    ],
  },
];
export default routesForMain;
