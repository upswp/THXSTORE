const routerForAdmin = [
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/views/AdminPage.vue'),
  },
];
export default routerForAdmin;
