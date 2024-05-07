<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>Smartech Push</ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content class="ion-padding">
            <ion-item-group>
                <ion-item-divider>
                    <ion-label>Tracking</ion-label>
                </ion-item-divider>

                <ion-item>
                    <ion-label class="ion-text-center ion-text-start">Opt Push Tracking</ion-label>
                    <ion-toggle v-model="optPushNotificationValue" slot="end"
                        @ionChange="togglePNTracking"></ion-toggle>
                </ion-item>
                <ion-button expand="block" @click="handleGetNotificationTraclingStatus"
                    class="button-with-spacing">Notification Tracking Status</ion-button>
            </ion-item-group>

            <ion-item-group>
                <ion-item-divider>
                    <ion-label>Push Token</ion-label>
                </ion-item-divider>

                <ion-item>
                    <ion-label position="floating">Enter New Token</ion-label>
                    <ion-input v-model="inputToken" type="text"></ion-input>
                </ion-item>

                <ion-button expand="block" @click="handleSetPushToken" class="button-with-spacing">Set Push
                    Token</ion-button>

                <ion-button expand="block" @click="handlePushToken" class="button-with-spacing">Show Push
                    Token</ion-button>

            </ion-item-group>

        </ion-content>
    </ion-page>
</template>

<script lang="ts">

import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonItem, IonInput, IonButton, IonTextarea, IonBackButton, alertController, IonToggle, IonLabel } from '@ionic/vue';
import { onMounted, ref } from 'vue'
import { Clipboard } from '@capacitor/clipboard';
import SmartechPush from 'smartech-push-cordova';

export default {
    components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonButton, IonBackButton, IonButtons, IonItem, IonInput, IonTextarea, IonToggle, IonLabel },
    setup() {

        const optPushNotificationValue = ref<boolean>(false);
        const inputToken = ref('');

        const togglePNTracking = async (event: { detail: { checked: boolean; }; }) => {
            try {
                optPushNotificationValue.value = event.detail.checked
                SmartechPush.optPushNotification(event.detail.checked)

            } catch (error) {
                console.error("Opt Event tracking error", error)
            }

        }

        const handleGetNotificationTraclingStatus = () => {
            SmartechPush.hasOptedPushNotification(async (result) => {
                const alert = await alertController.create({
                    header: 'response',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const handlePushToken = () => {
            SmartechPush.getDevicePushToken(async (result) => {
                const alert = await alertController.create({
                    header: 'response',
                    message: String(result),
                    buttons: [{
                        text: 'OK',
                    },
                    {
                        text: 'Copy',
                        handler: async() => {
                            await Clipboard.write({
                                string: result
                            });
                        }
                    }]
                });
                await alert.present();

            })
        }

        const handleSetPushToken = () => {
            SmartechPush.setDevicePushToken(inputToken.value)
        }

        const loadToggleState = async () => {
            try {
                SmartechPush.hasOptedPushNotification(async (result) => {
                    console.log("smartech tracking value is " + result);
                    optPushNotificationValue.value = result === true;
                })
            } catch (error) {
                console.error('Error loading toggle state', error);
            }
        };

        const handleRequestNotificationPermission = () => {
            SmartechPush.requestNotificationPermission((result: number) => {
                console.log(" requestNotificationPermission  permission status " + result);
            })
        }

        const handleUpdateNotificationPermissionStatus = () => {
            SmartechPush.updateNotificationPermission(0);
        }

        handleUpdateNotificationPermissionStatus();

        onMounted(() => {
            loadToggleState()
        })

        return { optPushNotificationValue, togglePNTracking, handleGetNotificationTraclingStatus, handlePushToken, inputToken, handleSetPushToken }
    }
}
</script>

<style scoped>
.flex-grow {
    flex-grow: 1;
}

.button-with-spacing {
    margin-top: 10px;
    /* Adjust the margin-top as needed */
}
</style>