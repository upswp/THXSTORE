import store from '@/store';

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
    beforeEnter: (to, from, next) => {
      if (screen.width < 480) {
        store.commit('toggleMainDrawerOpen');
      }
      next();
    },
  },
];
export default routesForMain;