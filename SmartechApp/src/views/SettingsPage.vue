<!-- MyPage.vue -->

<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ pageTitle }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <!-- Your page content goes here -->
      <ion-item-group>
        <ion-item-divider>
          <ion-label>General App Settings</ion-label>
        </ion-item-divider>

        <ion-item @click="handleLogout" v-if="!isLoggedInAsGuestFlag.value">
          <ion-icon slot="start" :icon="logOut"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">LogOut</ion-label>
        </ion-item>

        <ion-item @click="handleLogoutAndClearIdentity" v-if="!isLoggedInAsGuestFlag.value">
          <ion-icon slot="start" :icon="logOut"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">LogOut and Clear Identity</ion-label>
        </ion-item>

        <ion-item v-if="!isLoggedInAsGuestFlag.value">
          <ion-icon slot="start" :icon="person"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Update My Profile</ion-label>
        </ion-item>

        <ion-item v-if="isLoggedInAsGuestFlag.value" @click="showLogin">
          <ion-icon slot="start" :icon="person"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Login</ion-label>
        </ion-item>
<!-- 
        <ion-item>
          <ion-icon slot="start" :icon="person"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Custom Profile Payload</ion-label>
        </ion-item> -->

        <ion-item @click="handleCustomPayload">
          <ion-icon slot="start" :icon="person"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Custom Payload</ion-label>
        </ion-item>
      </ion-item-group>

      <ion-item-group>
        <ion-item-divider>
          <ion-label>GDPR Settings</ion-label>
        </ion-item-divider>

        <ion-item>
          <ion-icon slot="start" :icon="notifications"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Opt Push Notification</ion-label>
          <ion-toggle v-model="optPushNotificationValue" slot="end" @ionChange="toggleOptPushNotificaion"></ion-toggle>
        </ion-item>

        <ion-item>
          <ion-icon slot="start" :icon="disc"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Opt In App Messages</ion-label>
          <ion-toggle v-model="optInAppTrackingValue" slot="end" @ionChange="toggleOptInAppMessage"></ion-toggle>
        </ion-item>

        <ion-item>
          <ion-icon slot="start" :icon="analytics"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">Opt Event Tracking</ion-label>
          <ion-toggle v-model="optEventTrackingValue" slot="end" @ionChange="toggleOptEventTracking"></ion-toggle>
        </ion-item>
      </ion-item-group>

      <!-- Features-->
      <ion-item-group>
        <ion-item-divider>
          <ion-label>Features</ion-label>
        </ion-item-divider>
        <ion-item @click="loadAppInbox">
          <ion-icon slot="start" :icon="cube"></ion-icon>
          <ion-label class="ion-text-center ion-text-start">SmartechAppInbox</ion-label>
        </ion-item>
      </ion-item-group>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonItem, IonItemDivider, IonItemGroup, IonLabel, IonToggle, IonIcon } from '@ionic/vue';
import { defineComponent, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Preferences } from '@capacitor/preferences';
import {  isLoggedInAsGuestFlag } from '@/main';
import { analytics, cloud, cloudUpload, cube, disc, glasses, laptop, logOut, notifications, person, settings } from 'ionicons/icons';

import SmartechBaseCordova from 'smartech-base-cordova';
import SmartechPushCordova from 'smartech-push-cordova';

export default defineComponent({

  components: { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonItem, IonItemDivider, IonItemGroup, IonLabel, IonToggle, IonIcon },

  data() {
    return {
      pageTitle: 'Settings', logOut, person, notifications, analytics, disc, cube, cloudUpload, cloud, glasses, laptop, settings
    };
  },

  setup() {

    const router = useRouter();
    let optPushNotificationValue = ref<boolean>(false);
    let optEventTrackingValue = ref<boolean>(false);
    let optInAppTrackingValue = ref<boolean>(false);
    let isLoggedInGuest = ref(false);

    const handleLogout = async () => {
      await Preferences.set({
        key: 'isLoggedIn',
        value: 'false',
      });

      await Preferences.set({
        key: 'isLoggeinAsGuest',
        value: 'false',
      });

      router.push('/home');
    }

    const handleItemClick = () => {
      // Handle item click logic here
      console.log('Item Clicked!');
    }

    const handleLogoutAndClearIdentity = async () => {
      // Handle item click logic here
      SmartechBaseCordova.logoutAndClearUserIdentity(true)

      await Preferences.set({
        key: 'isLoggedIn',
        value: 'false',
      });

      await Preferences.set({
        key: 'isLoggeinAsGuest',
        value: 'false',
      });

      router.push('/home');
    }

    const handleCustomPayload = () => {
      router.push('/customPayload')
    }

    const loadAppInbox = () => {
      router.push('/SmartechAppInbox')
    }

    const toggleOptPushNotificaion = async (event) => {
      try {
        optPushNotificationValue.value = event.detail.checked
        SmartechPushCordova.optPushNotification(event.detail.checked);
      } catch (error) {
        console.error("Opt push notification", error)
      }
    }

    const toggleOptEventTracking = async (event) => {
      try {
        optEventTrackingValue.value = event.detail.checked
        SmartechBaseCordova.optTracking(event.detail.checked);

      } catch (error) {
        console.error("Opt push notification", error)
      }
    }

    const toggleOptInAppMessage = async (event) => {
      try {
        optInAppTrackingValue.value = event.detail.checked
        SmartechBaseCordova.optInAppMessage(event.detail.checked);

      } catch (error) {
        console.error("Opt push notification", error)
      }
    }

    const showLogin = () => {
      isLoggedInAsGuestFlag.value = false;
      router.push('/login');
    }

    const loadToggleState = async () => {

      try {

        SmartechBaseCordova.hasOptedTracking(async (result) => {
          console.log("smartech tracking value is " + result);
          optEventTrackingValue.value = result === true;
        })

        SmartechBaseCordova.hasOptedInAppMessage(async (result) => {
          console.log("smartech InApp tracking value is " + result);
          optInAppTrackingValue.value = result === true;
        })

        SmartechPushCordova.hasOptedPushNotification(async (result) => {
          console.log("smartechpush notification tracking value is " + result);
          optPushNotificationValue.value = result === true;
        })


      } catch (error) {
        console.error('Error loading toggle state', error);
      }

    };

    onMounted(() => {
      loadToggleState()
    })

    return { handleLogout, handleItemClick, handleLogoutAndClearIdentity, handleCustomPayload, toggleOptPushNotificaion, optPushNotificationValue, toggleOptEventTracking, optEventTrackingValue, loadAppInbox, toggleOptInAppMessage, optInAppTrackingValue, isLoggedInAsGuestFlag, showLogin }
  }
});
</script>

<style scoped>
/* Add your custom styles here */
</style>
