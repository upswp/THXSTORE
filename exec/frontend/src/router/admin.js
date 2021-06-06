import store from '@/store';
const beforeEnter = (to, from, next) => {
  if (store.getters.getToken) {
    next();
    return;
  } else {
    alert('접근을 위해서 로그인 페이지로 이동합니다.');
    next('/auth/login');
  }
};
const routerForAdmin = [
  {
    path: '/admin/store-application',
    name: 'storeApplication',
    component: () => import('@/views/ManageApplicationPage.vue'),
    beforeEnter,
  },
  {
    path: '/admin/store-modification',
    name: 'storeModification',
    component: () => import('@/views/ManageModificationPage.vue'),
    beforeEnter,
  },
];
export default routerForAdmin;
