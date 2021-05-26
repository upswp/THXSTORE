import store from '@/store';
const routerForAdmin = [
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/views/AdminPage.vue'),
    beforeEnter: (to, from, next) => {
      if (store.getters.getToken) {
        next();
        return;
      } else {
        alert('접근을 위해서 로그인 페이지로 이동합니다.');
        next('/auth/login');
      }
    },
  },
];
export default routerForAdmin;
