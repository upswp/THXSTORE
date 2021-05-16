import store from '@/store';
import routesForStore from '@/router/store';
import routesForAdmin from '@/router/admin';
import routesForUserStore from '@/router/userStore';
const authGuard = (to, from, next) => {
  if (store.getters.getToken) {
    next();
    return;
  } else {
    alert('접근을 위해서 로그인 페이지로 이동합니다.');
    next('/auth/login');
  }
};
const routesForMain = [
  {
    path: '/main',
    redirect: '/main/user',
    name: 'main',
    component: () => import('@/views/MainPage.vue'),
    beforeEnter: authGuard,
    children: [
      {
        path: '/user',
        name: 'user',
        component: () => import('@/views/UserPage.vue'),
      },
      {
        path: '/feed',
        name: 'feed',
        component: () => import('@/views/TimeDealFeedPage.vue'),
      },
      ...routesForStore,
      ...routesForAdmin,
      ...routesForUserStore,
    ],
  },
];
export default routesForMain;
