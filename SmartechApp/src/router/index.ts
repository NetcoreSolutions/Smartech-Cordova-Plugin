import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import HomePage from '@/views/Home.vue';
import LoginPage from '@/views/Login.vue';
import AppInboxPage from '@/views/AppInboxPage.vue';
import EventsPage from '@/views/EventsPage.vue';
import SettingsPage from '@/views/SettingsPage.vue';
import RegistrationPage from '@/views/RegistrationPage.vue'
import CustomPayloadPage from '@/views/CustomPayloadPage.vue'
import SDKGridPage from '@/views/SDKGridPage.vue'
import SmartechBase from '@/views/SmartechBasePage.vue'
import SmartechPush from '@/views/SmartechPushPage.vue'
import SmartechAppInbox from '@/views/SmartechAppInboxPage.vue'
import SmartechAppInboxMessagePage from '@/views/SMTAppInboxMessagesPage.vue'
import DeepLinkPage from '@/views/DeepLinkPage.vue';
import SmartechAppInboxByApi from '@/views/SmartechAppInboxApiCallPage.vue'

import { Preferences } from '@capacitor/preferences';

import {  isLoggedInAsGuestFlag } from '@/main';

const checkAuthentication = async () => {
  const { value } = await Preferences.get({ key: 'isLoggedIn' });
  //const isLoggedInAsGuest = await Preferences.get({ key: 'isLoggeinAsGuest' });

  let isAuthenticated = false;

  if (value === 'true' || isLoggedInAsGuestFlag.value === true) {
    isAuthenticated = true;
  } else {
    isAuthenticated = false;
  }


  //isAuthenticated = value === 'true' || false; // Default to false if value is null
  return isAuthenticated;
};


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home'
  },
  // {
  //   path: '/',
  //   redirect: '/SDKGrid'
  // },
  {
    path: '/SmartechBase',
    name: 'SmartechBase',
    component: SmartechBase
  },

  {
    path: '/SmartechPush',
    name: 'SmartechPush',
    component: SmartechPush
  },
  {
    path: '/SmartechAppInbox',
    name: 'SmartechAppInbox',
    component: SmartechAppInboxMessagePage
  },
  {
    path: '/SmartechAppInboxMessage',
    name: 'SmartechAppInboxMessage',
    component: SmartechAppInboxMessagePage
  },
  {
    path: '/SmartechAppInboxByApi',
    name: 'SmartechAppInboxByApi',
    component: SmartechAppInboxByApi
  },
  {
    path: '/SDKGrid',
    name: 'SDKGrid',
    component: SDKGridPage
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage
  },
  {
    path: '/registration',
    name: 'Registration',
    component: RegistrationPage
  },
  {
    path: '/customPayload',
    name: 'CustomPayload',
    component: CustomPayloadPage
  },
  {
    path: '/deeplink/:data', // Define the route with a parameter for data
    name: 'DeepLinkPage',
    component: DeepLinkPage
  },
  {
    path: '/home',
    name: 'Home',
    component: HomePage,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/events',
      },
      {
        path: '/events',
        component: EventsPage,
      },
      {
        path: '/settings',
        component: SettingsPage,
      },
      // {
      //   path: '/appInbox',
      //   component: AppInboxPage,
      // }
    ],
  },
]



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from, next) => {
  const isAuthenticated = await checkAuthentication();

  if (to.meta.requiresAuth && !isAuthenticated) {
    // Redirect unauthenticated users to the login page
    next({ name: 'Login' });
  } else if (to.name === 'Login' && isAuthenticated) {
    // If user is already logged in, redirect to the dashboard
    next('/home');
  } else {
    next();
  }
});

export default router
