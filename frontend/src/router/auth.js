const routesForAuth = [
  {
    path: '/auth',
    redirect: '/auth/login',
    component: () => import('@/views/AuthPage.vue'),
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import('@/components/auth/LoginForm.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'signup',
        name: 'signup',
        component: () => import('@/components/auth/SignupForm.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'extra-info',
        name: 'extraInfo',
        component: () => import('@/components/auth/ExtraInfoForm.vue'),
        meta: { transitionName: 'slide' },
      },
      // {
      //   path: 'findPassword',
      //   component: () => import('@/components/auth/FindPassword.vue'),
      // },
    ],
  },
  {
    path: '/social-login-test',
    component: () => import('@/views/LoginPage.vue'),
  },
];
export default routesForAuth;
