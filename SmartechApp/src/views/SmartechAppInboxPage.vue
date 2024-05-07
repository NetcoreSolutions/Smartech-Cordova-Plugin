<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>{{ pageTitle }}</ion-title>
            </ion-toolbar>
        </ion-header>


        <ion-content class="ion-padding">
            <ion-chip-group v-if="showChips">
                <ion-chip color="danger" v-for="(chip, index) in categoryChips" :key="index">
                    <ion-label>{{ chip }}</ion-label>
                </ion-chip>
            </ion-chip-group>
            <ion-button expand="block" @click="getAppInboxCategory" class="button-with-spacing">Get AppInbox
                Category</ion-button>
            <ion-button expand="block" @click="getAppInboxMessageCount" class="button-with-spacing">Get AppInbox Message
                Count</ion-button>
            <ion-button expand="block" @click="getAppInboxMessage" class="button-with-spacing">Get AppInbox
                Message</ion-button>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonChip, IonLabel, IonInput, IonRow, IonCol, IonButton, IonTextarea, IonBackButton, alertController, IonToggle } from '@ionic/vue';
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import { SMTAppInboxCategory } from '@/interfaces/SMTAppInboxCategory';
import SmartechAppInbox from 'smartech-appinbox-cordova';

export default {

    components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonChip, IonLabel, IonInput, IonRow, IonCol, IonButton, IonTextarea, IonBackButton, alertController, IonToggle },

    setup() {

        const showChips = ref(false);
        const pageTitle = "App Inbox"
        const router = useRouter();
        const categoryChips = ref<string[]>([]);

        const getAppInboxCategory = () => {
            showChips.value = true;
            SmartechAppInbox.getAppInboxCategoryList((result) => {
                console.log("Appinbox result is " + result);
                categoryChips.value = [];
                const data = result as SMTAppInboxCategory[];
                for (var val of data) {
                    categoryChips.value.push(val.categoryName)
                }
            }
            )
        }

        const getAppInboxMessage = () => {

            router.push('/SmartechAppInboxMessage')
        }

        const getAppInboxMessageCount = async () => {
            SmartechAppInbox.getAppInboxMessageCount(SmartechAppInbox.INBOX_MESSAGE, async (count) => {
                const alert = await alertController.create({
                    header: 'AppInbox',
                    message: 'AppInbox message count: ' + count,
                    buttons: ['OK']
                });
                await alert.present();
            }
            )
        }

        return { pageTitle, showChips, categoryChips, getAppInboxCategory, getAppInboxMessage, getAppInboxMessageCount }

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

ion-chip {
    --background: #00213f;
    --color: #adefd1;
    min-width: 100px;
}

.custom-chip {
    min-width: 80px;
    /* Set a minimum width for the chips */
    margin-right: 8px;
    /* Add spacing between chips */
}

ion-label {
    text-align: center;
}

ion-chip-group {
    display: flex;
    flex-wrap: nowrap;
    /* Prevent chips from wrapping to next line */
    overflow-x: auto;
    /* Allow horizontal scrolling if chips exceed container width */
}
</style>