<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <!-- Use ion-back-button to create the back button -->
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>
                    Profile Update
                </ion-title>
                <ion-buttons slot="end">
                    <ion-button @click="submitProfile">
                        <ion-icon :icon="checkmarkOutline"></ion-icon>
                    </ion-button>
                </ion-buttons>
            </ion-toolbar>
        </ion-header>

        <ion-content>
            <div class="registration-component">
                <ion-label>
                    You can update your profile and update profile will be visible in the Smartech Panel.
                </ion-label>

                <ion-list>
                    <!-- <ion-item>
                        <ion-label position="floating">Primary Key</ion-label>
                        <ion-input v-model="userProfile.PRIMARY_KEY" placeholder="Enter your primary key"></ion-input>
                    </ion-item>

                    <ion-item>
                        <ion-label position="stacked">Email</ion-label>
                        <ion-input type="email" v-model="userProfile.EMAIL" placeholder="Enter your email"></ion-input>
                    </ion-item> -->

                    <ion-item>
                        <ion-label position="stacked">First Name</ion-label>
                        <ion-input v-model="userProfile.FIRST_NAME" placeholder="Enter your first name"></ion-input>
                    </ion-item>

                    <ion-item>
                        <ion-label position="stacked">Last Name</ion-label>
                        <ion-input v-model="userProfile.LAST_NAME" placeholder="Enter your last name"></ion-input>
                    </ion-item>

                    <ion-item>
                        <ion-label position="stacked">Age</ion-label>
                        <ion-input type="number" v-model="userProfile.AGE" placeholder="Enter your age"></ion-input>
                    </ion-item>


                    <ion-item>
                        <ion-label position="stacked">Country</ion-label>
                        <ion-input v-model="userProfile.COUNTRY" placeholder="Enter your country"></ion-input>
                    </ion-item>

                    <ion-item>
                        <ion-label position="stacked">State</ion-label>
                        <ion-input v-model="userProfile.STATE" placeholder="Enter your state"></ion-input>
                    </ion-item>


                    <ion-item>
                        <ion-label position="stacked">Pincode</ion-label>
                        <ion-input type="number" v-model="userProfile.PINCODE"
                            placeholder="Enter your pincode"></ion-input>
                    </ion-item>


                </ion-list>
            </div>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { onMounted, ref } from 'vue';
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem, IonLabel, IonInput, IonButton, IonIcon, IonBackButton } from '@ionic/vue';
import { checkmarkOutline } from 'ionicons/icons';
import Smartech from 'smartech-base-cordova';

export default {
    components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem, IonLabel, IonInput, IonButton, IonIcon, IonBackButton },

    setup() {

        // const userProfile = ref({
        //     PRIMARY_KEY: '',
        //     FIRST_NAME: '',
        //     LAST_NAME: '',
        //     EMAIL: '',
        //     AGE: '',
        //     COUNTRY: '',
        //     STATE: '',
        //     PINCODE: ''
        // });

        const userProfile = ref({
            FIRST_NAME: '',
            LAST_NAME: '',
            AGE: '',
            COUNTRY: '',
            STATE: '',
            PINCODE: ''
        });



        const submitProfile = () => {
            if (!userProfile.value.FIRST_NAME || !userProfile.value.LAST_NAME || !userProfile.value.AGE || !userProfile.value.COUNTRY || !userProfile.value.STATE || !userProfile.value.PINCODE) {
                alert('Please fill in all fields.');
                return;
            }

            const jsonData = JSON.stringify(userProfile.value);
            console.log(jsonData); // Here you can send the JSON data to your backend
            Smartech.updateUserProfile(userProfile)
            alert("Profile updated successfully.")
        }

        onMounted(() => {

        })

        return { submitProfile, checkmarkOutline, userProfile }

    }
}
</script>

<style scoped>
.registration-component {
    padding: 20px;
}
</style>