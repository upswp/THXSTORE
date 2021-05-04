import Vue from 'vue';
import VueRouter from 'vue-router';
import routesForAuth from '@/router/auth';
import routesForMain from '@/router/main';
import routesForStore from '@/router/store';
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/store',
  },
  ...routesForMain,
  ...routesForAuth,
  ...routesForStore,
];

const router = new VueRouter({
  mode: 'history',
  routes,
});

export default router;
