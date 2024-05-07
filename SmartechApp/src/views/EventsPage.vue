<!-- MyPage.vue -->

<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ pageTitle }}</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-padding">
      <ion-list>
        <template v-for="eventItem in dynamicArray">
          <ion-item :key="eventItem.id" v-if="shouldRenderItem(eventItem)" @click="handleEventClick(eventItem)">
            <ion-label>
              <h2 style="text-align: start;">{{ eventItem.name }}</h2>
            </ion-label>
            <ion-button fill="clear" @click="handlePayload(eventItem)" slot="end" class="custom-button ">View
              Payload</ion-button>
          </ion-item>
        </template>
      </ion-list>
    </ion-content>

  </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonButton, IonBackButton, IonTitle, IonContent, IonList, IonItem, IonLabel, alertController } from '@ionic/vue';
import { onMounted, ref } from 'vue';
import { SMTEvents, SMTEvent } from '@/interfaces/SMTEvents'
import eventData from '@/assets/events.json';
import { isPlatform } from '@ionic/vue';
import { Clipboard } from '@capacitor/clipboard';

const isAndroid = isPlatform('android');
const isIOS = isPlatform('ios');

import Smartech from 'smartech-base-cordova'


export default {

  components: { IonPage, IonHeader, IonToolbar, IonButton, IonBackButton, IonTitle, IonContent, IonList, IonItem, IonLabel },

  // data() {
  //   return { dynamicArray: [] as SMTEvent[] }
  // },
  setup() {

    //let dataEvents: SMTEvent[] =  [];
    const dynamicArray = ref([] as SMTEvent[]);

    const shouldRenderItem = (item: SMTEvent) => {
      return (item.isVisibleToUser == 1) && (item.isSpecialPayload == 0)

    }

    const handleEventClick =(item: SMTEvent) => {
      Smartech.trackEvent(item.name, item.payload);
    }


    const handlePayload = (item: SMTEvent) => {
      presentAlert(item)
    }

    const presentAlert = async (item: SMTEvent) => {
      const alert = await alertController.create({
        header: "Smartech",
        message: JSON.stringify(item.payload),
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              console.log("alert cancelled")
            },
          },
          {
            text: 'Copy',
            role: 'confirm',
            handler: async () => {
              await Clipboard.write({
                string: JSON.stringify(item.payload)
              });
              console.log("alert confirmed")
            },
          },
        ],
      });

      await alert.present();

    };


    onMounted(async () => {

      //  working code
      try {

        if (isAndroid || isIOS) {
          const myObject: unknown = eventData
          const eventNode: SMTEvents = myObject as SMTEvents
          // dynamicArray.value = eventNode.events.slice(0, 100)
          dynamicArray.value = eventNode.events;
          console.log(dynamicArray)

        } else {
          const response = await fetch('src/assets/events.json'); // Adjust the path based on your project structure
          const data = await response.json() as SMTEvents;
          dynamicArray.value = data.events.slice(0, 100)
          console.log(dynamicArray)
        }

      } catch (error) {
        console.error("error is parsing")
        console.error('Error loading JSON data:', error);
      }
    })

    return { pageTitle: "Events", dynamicArray, handlePayload, shouldRenderItem, handleEventClick }

  }

}
</script>

<style scoped>
/* Add your custom styles here */
.custom-button {
  --color: white;
  padding-right: 10px;
  --background: #FF7F50;
  --ripple-color: deeppink;
  --box-shadow: 0 2px 6px 0 rgb(0, 0, 0, 0.25);
}
</style>
