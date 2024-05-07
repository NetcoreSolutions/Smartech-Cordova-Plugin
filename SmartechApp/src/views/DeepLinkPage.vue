<!-- src/views/DeepLinkPage.vue -->

<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>Deep Link Page</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content class="ion-padding">
            <ion-textarea :auto-grow="true"  v-model="displayData" readonly ></ion-textarea>
            <ion-button expand="block" @click="handleBrowser" v-if="showBrowserButton">Browser</ion-button>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { IonPage, IonContent, IonHeader, IonToolbar, IonTitle, IonTextarea, IonButton, IonButtons, IonBackButton } from '@ionic/vue';
import { defineComponent, ref, computed, onMounted } from 'vue';
import { RouteLocationNormalizedLoaded } from 'vue-router';
import { useRoute, useRouter } from 'vue-router';
import { Browser } from '@capacitor/browser';

export default defineComponent({
    components: { IonPage, IonContent, IonHeader, IonToolbar, IonTitle, IonTextarea, IonButton, IonButtons, IonBackButton },

    setup() {
        const router = useRouter(); // Use useRouter to access the router instance
        const showBrowserButton = ref(false);

        const route = router.currentRoute.value;
        const data: string = Array.isArray(route.params.data) ? route.params.data[0] : route.params.data; // Ensure data is always a string
        const parsedData = JSON.parse(data);
        // Display the data in the text area
        
        const displayData = ref(JSON.stringify(parsedData, null, 2));


        const deeplink = parsedData.smtDeeplink;

        showBrowserButton.value = deeplink.startsWith('http://') || deeplink.startsWith('https://');

        const handleBrowser = async () => {
            await Browser.open({ url: deeplink });
        }

        return { displayData, showBrowserButton, handleBrowser };
    }
});
</script>