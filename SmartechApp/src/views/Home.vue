<template>
  <ion-page>
    <ion-tabs >
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="events" href="/events">
          <ion-icon :icon="radio" />
          <ion-label>Events</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="settings" href="/settings">
          <ion-icon :icon="library" />
          <ion-label>Settings</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script lang="ts">
import { App } from '@capacitor/app';

import { IonPage, IonTabs, IonRouterOutlet, IonTabBar, IonTabButton, IonLabel, IonIcon } from '@ionic/vue';

import { playCircle, radio, library, search } from 'ionicons/icons';

import { useRoute, useRouter, RouteLocationRaw } from 'vue-router';
import { onBeforeMount, onBeforeUnmount, onMounted, onUnmounted } from 'vue';


export default {
  components: { IonPage, IonTabs, IonRouterOutlet, IonTabBar, IonTabButton, IonLabel, IonIcon },
  setup() {


    const route = useRoute();
    const router = useRouter();

    const handleTabChange = (event: CustomEvent) => {
      // const selectedTab = (event.target as HTMLElement)?.getAttribute('tab');

      // // Prevent navigation to previous page when switching tabs
      // if (selectedTab !== null && route.path !== selectedTab) {
      //   router.replace(selectedTab as RouteLocationRaw);
      // }
    }

   

    const handlePhysicalBackButton = () => {
      console.log("handled device physical back button.");
      return true;
    }

    // Register the back button handler when the component is mounted
    // and unregister it when the component is unmounted
    onMounted(() => {
     // document.addEventListener('ionBackButton', handleBackButton);
      App.addListener('backButton', handlePhysicalBackButton);
    });

    onUnmounted(() => {
      //document.removeEventListener('ionBackButton', handleBackButton);
      App.removeAllListeners();
    });

    return {
      playCircle,
      radio,
      library,
      search,
      handleTabChange
    };
  },

};
</script>