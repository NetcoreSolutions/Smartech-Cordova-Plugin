<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>Custom Payload</ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content class="ion-padding">
            <!-- Label and Input Field -->
            <ion-item>
                <ion-label position="floating">Name</ion-label>
                <ion-input v-model="name" placeholder="Enter event name"></ion-input>
            </ion-item>

            <!-- Buttons in Horizontal Stack -->
            <ion-row class="ion-padding-top">
                <ion-col>
                    <ion-button color="primary" @click="handleCopy">Copy</ion-button>
                </ion-col>
                <ion-col>
                    <ion-button color="secondary" @click="handlePaste">Paste</ion-button>
                </ion-col>
                <ion-col>
                    <ion-button color="tertiary" @click="handleSend">Send</ion-button>
                </ion-col>
            </ion-row>

            <!-- Big Textarea -->
            <ion-textarea class="flex-grow" :auto-grow="true" v-model="description" placeholder="Enter description"></ion-textarea>
        </ion-content>
    </ion-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Clipboard } from '@capacitor/clipboard';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonInput, IonRow, IonCol, IonButton, IonTextarea, IonBackButton, alertController } from '@ionic/vue';
import Smartech from 'smartech-base-cordova';

// Reactive variables for input fields
const name = ref('');
const description = ref('');

// Button handlers
const handleCopy = () => {
    console.log('Name:', name.value);
    console.log('Description:', description.value);
};

const handlePaste = async () => {
    console.log('Name:', name.value);
    console.log('Description:', description.value);

    const { type, value } = await Clipboard.read();

    console.log(`Got ${type} from clipboard: ${value}`);
    description.value = value
};

const handleSend = async () => {
    console.log('Name:', name.value);
    console.log('Description:', description.value);

    if (validateJSONString(description.value)) {
        Smartech.trackEvent(name.value, JSON.parse(description.value));
    } else {
        const alert = await alertController.create({
            header: 'Error',
            message: 'Enter a valid JSON payload.',
            buttons: ['OK']
        });
        await alert.present();
    }


};

const validateJSONString = (data: string) => {
    if (typeof data !== "string") {
        return false;
    }
    try {
        JSON.parse(data);
        return true;
    } catch (error) {
        return false;
    }
}
</script>

<style scoped>
.flex-grow {
    flex-grow: 1;
}
</style>