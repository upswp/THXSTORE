import Vue from 'vue';
import VueRouter from 'vue-router';
import routesForAuth from '@/router/auth';
import routesForMain from '@/router/main';
import routesForStore from '@/router/store';
import routesForAdmin from '@/router/admin';
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/store',
  },
  ...routesForMain,
  ...routesForAuth,
  ...routesForStore,
  ...routesForAdmin,
];

const router = new VueRouter({
  mode: 'history',
  routes,
});

export default router;
