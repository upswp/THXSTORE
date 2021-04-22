import Vue from 'vue';
import VueRouter from 'vue-router';
import routesForAuth from '@/router/auth';
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/main',
  },
  {
    path: '/main',
    name: 'main',
    component: () => import('@/views/MainPage.vue'),
  },
  ...routesForAuth,
];

const router = new VueRouter({
  mode: 'history',
  routes,
});

export default router;
