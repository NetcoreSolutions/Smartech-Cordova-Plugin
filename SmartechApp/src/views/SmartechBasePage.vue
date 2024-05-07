<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>
                    <div class="title-container">
                        <div>SmartechBase</div>
                        <div class="subtitle">{{ subtitle }}</div>
                    </div>
                </ion-title>
            </ion-toolbar>
        </ion-header>

        <ion-content class="ion-padding">

            <ion-item-group>
                <ion-item-divider>
                    <ion-label>Auth</ion-label>
                </ion-item-divider>

                <ion-item>
                    <ion-label position="floating">Enter identity</ion-label>
                    <ion-input v-model="inputText" type="text"></ion-input>
                </ion-item>

                <!-- Button -->
                <ion-button expand="block" @click="handleSetIdentity" class="button-with-spacing">Set
                    Identity</ion-button>
                <ion-button expand="block" @click="handleClearUserIdentity" class="button-with-spacing">Clear User
                    Identity</ion-button>
                <ion-button expand="block" @click="handleLogin" class="button-with-spacing">Login</ion-button>
                <ion-button expand="block" @click="handleLogoutAndClearUserIdentity"
                    class="button-with-spacing">LogOutAndClear User Identity</ion-button>

                <ion-button expand="block" @click="handleGetUserIdentity" class="button-with-spacing">Get
                    Identity</ion-button>

                <ion-button expand="block" @click="handleUpdateUserProfile" class="button-with-spacing">Update User
                    Profile</ion-button>

            </ion-item-group>

            <ion-item-group>
                <ion-item-divider>
                    <ion-label>Track</ion-label>
                </ion-item-divider>

                <ion-item>
                    <ion-label class="ion-text-center ion-text-start">Opt Event Tracking</ion-label>
                    <ion-toggle v-model="optEventTrackingValue" slot="end"
                        @ionChange="toggleOptEventTracking"></ion-toggle>
                </ion-item>
                <ion-button expand="block" @click="handleGetEventTrackingStatus" class="button-with-spacing">Event
                    tracking
                    Status</ion-button>

                <ion-item>
                    <ion-label class="ion-text-center ion-text-start">Opt InApp Tracking</ion-label>
                    <ion-toggle v-model="optInAppTrackingValue" slot="end"
                        @ionChange="toggleOptInAppTracking"></ion-toggle>
                </ion-item>

                <ion-button expand="block" @click="handleGetInAppTrackingStatus"
                    class="button-with-spacing">InAppMessage
                    tracking
                    Status</ion-button>


            </ion-item-group>


            <ion-item-group class="button-with-spacing">
                <ion-item-divider>
                    <ion-label>SDK Info</ion-label>
                </ion-item-divider>

                <ion-button expand="block" @click="handleGetAppId" class="button-with-spacing">Get App Id</ion-button>
                <ion-button expand="block" @click="handleGetDeviceId" class="button-with-spacing">Get Device
                    Id</ion-button>
                <ion-button expand="block" @click="handleGetSDKVersion" class="button-with-spacing">Get SDK
                    Version</ion-button>

            </ion-item-group>

            <ion-item-group class="button-with-spacing">
                <ion-item-divider>
                    <ion-label>Location</ion-label>
                </ion-item-divider>

                <ion-item>
                    <ion-label position="floating">Enter Lat</ion-label>
                    <ion-input v-model="inputLat" type="number"></ion-input>
                </ion-item>
                <ion-item>
                    <ion-label position="floating">Enter Long</ion-label>
                    <ion-input v-model="inputLong" type="number"></ion-input>
                </ion-item>
                <ion-button expand="block" @click="handleSetLocation" class="button-with-spacing">Set
                    Location</ion-button>

            </ion-item-group>

            <ion-item-group class="button-with-spacing">
                <ion-item-divider>
                    <ion-label>Track Events</ion-label>
                </ion-item-divider>
                <ion-button expand="block" @click="handleTrackEvent" class="button-with-spacing">Track
                    Event</ion-button>
                <ion-button expand="block" @click="handleTrackAppInsatall" class="button-with-spacing">Track App
                    Install</ion-button>
                <ion-button expand="block" @click="handleTrackAppUpdate" class="button-with-spacing">Track App
                    Update</ion-button>
                <ion-button expand="block" @click="handleTrackAppInstallBySmartech" class="button-with-spacing">Track
                    App
                    Install by Smartech</ion-button>
            </ion-item-group>


        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonInput, IonRow, IonCol, IonButton, IonTextarea, IonBackButton, alertController, IonToggle } from '@ionic/vue';
import { onMounted, ref } from 'vue'
import SmartechBaseCordova from 'smartech-base-cordova';

export default {

    components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonInput, IonRow, IonCol, IonButton, IonTextarea, IonBackButton, IonToggle },

    setup() {


        const inputText = ref('');
        const inputLat = ref<number>(0.0);
        const inputLong = ref<number>(0.0);
        const optEventTrackingValue = ref<boolean>(false);
        const optInAppTrackingValue = ref<boolean>(false);
        const subtitle = ref('')

        const handleSetIdentity = async () => {
            const identity = inputText.value;
            if (identity.length > 0) {
                SmartechBaseCordova.setUserIdentity(identity)
                // Reload subtitle
                loadSubtitle()
            } else {
                const alert = await alertController.create({
                    header: 'Error',
                    message: 'Enter a valid primary key.' + identity.length,
                    buttons: ['OK']
                });
                await alert.present();
            }
        };

        const handleLogin = async () => {
            const identity = inputText.value;
            if (identity.length > 0) {
                SmartechBaseCordova.login(identity)
                // Reload subtitle
                loadSubtitle()
            } else {
                const alert = await alertController.create({
                    header: 'Error',
                    message: 'Enter a valid primary key.' + identity.length,
                    buttons: ['OK']
                });
                await alert.present();
            }
        };

        const handleClearUserIdentity = () => {
            SmartechBaseCordova.clearUserIdentity();
            // Reload subtitle
            loadSubtitle()
        }

        const handleLogoutAndClearUserIdentity = () => {
            SmartechBaseCordova.logoutAndClearUserIdentity(true);
            // Reload subtitle
            loadSubtitle()
        }

        const handleGetUserIdentity = async () => {
            SmartechBaseCordova.getUserIdentity(async (result) => {
                const alert = await alertController.create({
                    header: 'response',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const handleUpdateUserProfile = () => {
            const payloadata = {
                NAME: "TestUser",
                EMAILID: "abc@xyz.com",
                AGE: "30",
                MOBILE: "4545748"
            };
            SmartechBaseCordova.updateUserProfile(payloadata);
        }

        const handleGetEventTrackingStatus = async () => {
            SmartechBaseCordova.hasOptedTracking(async (result) => {
                const alert = await alertController.create({
                    header: 'Event Tracking',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const handleGetAppId = async () => {
            SmartechBaseCordova.getAppId(async (result) => {
                const alert = await alertController.create({
                    header: 'AppId',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const handleGetDeviceId = async () => {
            SmartechBaseCordova.getDeviceGuid(async (result) => {
                const alert = await alertController.create({
                    header: 'Device Id',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const handleGetSDKVersion = async () => {
            SmartechBaseCordova.getSDKVersion(async (result) => {
                const alert = await alertController.create({
                    header: 'SDK Version',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();

            })
        }

        const handleGetInAppTrackingStatus = async () => {
            SmartechBaseCordova.hasOptedInAppMessage(async (result) => {
                const alert = await alertController.create({
                    header: 'Event Tracking',
                    message: String(result),
                    buttons: ['OK']
                });
                await alert.present();
            })
        }

        const toggleOptEventTracking = async (event: { detail: { checked: boolean; }; }) => {
            try {
                optEventTrackingValue.value = event.detail.checked
                SmartechBaseCordova.optTracking(event.detail.checked)
            } catch (error) {
                console.error("Opt Event tracking error", error)
            }
        }

        const toggleOptInAppTracking = async (event: { detail: { checked: boolean; }; }) => {
            try {
                optInAppTrackingValue.value = event.detail.checked
                SmartechBaseCordova.optInAppMessage(event.detail.checked)
            } catch (error) {
                console.error("Opt Event tracking error", error)
            }
        }

        const loadToggleState = async () => {

            // event Tracking
            try {

                SmartechBaseCordova.hasOptedTracking(async (result) => {
                    console.log("smartech tracking value is " + result);
                    optEventTrackingValue.value = result === true;
                })

                SmartechBaseCordova.hasOptedInAppMessage(async (result) => {
                    console.log("smartech InApp tracking value is " + result);
                    optInAppTrackingValue.value = result === true;
                })


            } catch (error) {
                console.error('Error loading toggle state', error);
            }
        };

        const handleSetLocation = () => {
            SmartechBaseCordova.setUserLocation(inputLat.value, inputLong.value);
        }

        const handleTrackEvent = () => {
            const payload = {
                "product_id": "1329",
                "product_name": "T-shirt",
                "brand": "Polo"
            }
            SmartechBaseCordova.trackEvent("Product Viewed", payload);
        }

        const handleTrackAppInsatall = () => {
            SmartechBaseCordova.trackAppInstall();
        }

        const handleTrackAppUpdate = () => {
            SmartechBaseCordova.trackAppUpdate();
        }

        const handleTrackAppInstallBySmartech = () => {
            SmartechBaseCordova.trackAppInstallUpdateBySmartech();
        }

        const loadSubtitle = () => {
            SmartechBaseCordova.getUserIdentity((identity) => {
                subtitle.value = identity;
            })
        }

        onMounted(() => {
            loadToggleState()
            loadSubtitle()
        })

        return { inputText, handleSetIdentity, handleLogin, handleGetUserIdentity, handleClearUserIdentity, handleLogoutAndClearUserIdentity, handleUpdateUserProfile, toggleOptEventTracking, optEventTrackingValue, handleGetEventTrackingStatus, toggleOptInAppTracking, optInAppTrackingValue, handleGetInAppTrackingStatus, handleGetAppId, handleGetDeviceId, handleGetSDKVersion, inputLat, inputLong, handleSetLocation, handleTrackAppInsatall, handleTrackAppUpdate, handleTrackAppInstallBySmartech, handleTrackEvent, subtitle }
    }



}

</script>

<style scoped>
.flex-grow {
    flex-grow: 1;
}

.title-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.subtitle {
    font-size: small;
    /* Adjust font size as needed */
    color: var(--ion-color-medium);
    /* Adjust color as needed */
}

.button-with-spacing {
    margin-top: 10px;
    /* Adjust the margin-top as needed */
}
</style>