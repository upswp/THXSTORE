import Vue from 'vue';
import VueRouter from 'vue-router';
import routesForAuth from '@/router/auth';
import routesForMain from '@/router/main';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/auth/login',
  },

  ...routesForAuth,
  ...routesForMain,
];

// NavigationDuplicated 오류만 console 창에 띄우지 않도록 함.
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') throw err;
  });
};

const router = new VueRouter({
  mode: 'history',
  routes,
});

export default router;
