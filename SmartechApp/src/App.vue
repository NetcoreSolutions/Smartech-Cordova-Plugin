<template>
  <ion-app>
    <ion-router-outlet />
  </ion-app>
</template>

<script setup lang="ts">
import { IonApp, IonRouterOutlet } from '@ionic/vue';
import Smartech from 'smartech-base-cordova';
import SmartechPush from 'smartech-push-cordova';
import SmartechAppInbox from 'smartech-appinbox-cordova';
import { onMounted, onUnmounted } from 'vue';
import { Browser } from '@capacitor/browser';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();


const initializeApp = () => {
  // Wait for the platform to be ready
  document.addEventListener('deviceready', onDeviceReady, false);
};

const onDeviceReady = () => {
  // Perform operations that depend on initialized plugins
};


const setUserIdentity = () => {
  console.log("NCSMT: Before Calling Smartech Base setIdentity JS Methods")
  //Smartech.setUserIdentity('Arjun');
}

const getUserIdentity = () => {
  console.log("NCSMT: Before Calling Smartech Base getUserIdentity JS Methods")
  Smartech.getUserIdentity(
    (result) => {
      console.log('NCSMT: Smartech Base getUserIdentity JS Code Success (App.vue) -', JSON.stringify(result));
    }
  );
}

function testSmartechPlugins() {
  // testSmartechBasePlugin();
  //testSmartechPushPlugin();
  // testSmartechAppInboxPlugin();
  setUserIdentity()
  getUserIdentity()
}

testSmartechPlugins();

function isURL(url: string): boolean {
  if (url.startsWith('http://') || url.startsWith('https://')) {
    return true
  } else {
    return false;
  }
}

async function pushNotificationHandler(payload: any) {
  try {
    console.log("received notification from sdk " + JSON.stringify(payload));
    router.push({ name: 'DeepLinkPage', params: { data: JSON.stringify(payload) } });
  } catch (error) {

  }
}

async function notificationReceived(payload: any) {
  try {
    console.log("Smartech Notification payload in detail" + typeof payload + "=====>" + JSON.stringify(payload) + " ====>" + payload.deeplink);
    console.log("Smartech Notification payload received" + payload);
  } catch (error) {
    console.log("Smartech Notification payloadk error " + error);
  }
}

// async function handleDeeplink(payload: string) {
//   try {
//     const objNotificationPayload = JSON.parse(payload);
//     const deeplink = objNotificationPayload.deeplink;

//     if (isURL(deeplink)) {
//       await Browser.open({ url: deeplink });
//     } else {
//       if (deeplink === "events") {
//         useRouter().push('/events')
//       }
//     }
//   } catch (error) {
//     console.log("Smartech Notification deeplink error " + error);
//   }
// }

// function handleAppInbox(data: Event) {
//   try {
//     const customEvent = data as CustomEvent<any>;
//     const objPayload = JSON.parse(JSON.stringify(customEvent.detail));
//    // handleDeeplink(JSON.stringify(objPayload));
//   } catch (error) {
//     console.log("Error for AppInbox deeplink.")
//   }
// }


onUnmounted(() => {
  console.log("KnightFury smartech listerner unregistered.")
  SmartechPush.removeListener(SmartechPush.SmartechNotificationReceived, notificationReceived);
  Smartech.removeListener(Smartech.SmartechDeeplink, pushNotificationHandler);


})

onMounted(() => {
  console.log("KnightFury smartech listerner registered." + Smartech.SmartechDeeplink)
  SmartechPush.addListener(SmartechPush.SmartechNotificationReceived, notificationReceived);
  Smartech.addListener(Smartech.SmartechDeeplink, pushNotificationHandler);

})

</script>
