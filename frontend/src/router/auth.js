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
      {
        path: 'mailcode',
        name: 'mailcode',
        component: () => import('@/components/auth/MailCodeForm.vue'),
        meta: { transitionName: 'slide' },
      },
      {
        path: 'findpassword',
        component: () => import('@/components/auth/FindPassword.vue'),
        meta: { transitionName: 'slide' },
      },
    ],
  },
];
export default routesForAuth;
