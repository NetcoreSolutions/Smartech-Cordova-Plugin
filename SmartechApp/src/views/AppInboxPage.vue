<!-- MyPage.vue -->

<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>AppInbox</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <!-- Your page content goes here -->
      <p>AppInbox.</p>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent } from '@ionic/vue';
import { defineComponent } from 'vue';

export default defineComponent({
  components: { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent },
});
</script>

<style scoped>
/* Add your custom styles here */
</style>
