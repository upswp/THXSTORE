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
        path: 'extra-info-signup',
        name: 'extraInfoSignup',
        component: () => import('@/components/auth/ExtraInfoForSignup.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'extra-info-login',
        name: 'extraInfoLogin',
        component: () => import('@/components/auth/ExtraInfoForLogin.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'mailcode',
        name: 'mailcode',
        component: () => import('@/components/auth/MailCodeForm.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'find-password',
        name: 'findPassword',
        component: () => import('@/components/auth/FindPassword.vue'),
        meta: { transitionName: 'slide' },
      },
    ],
  },
];
export default routesForAuth;
