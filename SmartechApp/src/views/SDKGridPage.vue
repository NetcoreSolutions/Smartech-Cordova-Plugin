<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-title>Smartech</ion-title>
            </ion-toolbar>
        </ion-header>
        <ion-content class="ion-padding">
            <ion-grid>
                <ion-row>
                    <!-- First item -->
                    <ion-col size="4">
                        <div class="grid-item" @click="handleItemClick(1)">
                            <span class="item-text">Base</span>
                        </div>
                    </ion-col>

                    <!-- Second item -->
                    <ion-col size="4">
                        <div class="grid-item" @click="handleItemClick(2)">
                            <span class="item-text">Push</span>
                        </div>
                    </ion-col>

                    <!-- Third item -->
                    <ion-col size="4">
                        <div class="grid-item" @click="handleItemClick(3)">App Inbox</div>
                    </ion-col>

                    <ion-col size="4">
                        <div class="grid-item" @click="handleItemClick(4)">App Inbox By API</div>
                    </ion-col>
                </ion-row>
            </ion-grid>
        </ion-content>
    </ion-page>
</template>
  
<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonTitle, IonContent,  IonRow, IonCol, IonGrid } from '@ionic/vue';
import { useRoute, useRouter } from 'vue-router';
import SmartechAppInbox, {Category} from 'smartech-appinbox-cordova';
export default {
    name: 'GridExample',

    components: { IonPage, IonHeader, IonToolbar, IonTitle, IonContent, IonRow, IonCol, IonGrid },
    setup() {
        const router = useRouter();

        const handleItemClick = (itemId: number) => {
            if (itemId == 1) {
                router.push('/SmartechBase')
            } else if (itemId == 2) {
                router.push('/SmartechPush')
            } else if (itemId == 3) {
                router.push('/SmartechAppInbox')
            }else if (itemId == 4) {
                router.push('/SmartechAppInboxByApi')
            }
        }

        const testAppInbox = () => {
            let data: Category[] = [{categoryName: "TestJR"}, {categoryName: "SubtitlePN"}, {categoryName: "SimplePN"}];

            SmartechAppInbox.getAppInboxCategoryList((result: object[]) => {
                console.log("Category list ==> "+result);
            })

            SmartechAppInbox.getAppInboxMessagesWithCategory(data, (result: object[]) => {
                console.log("getAppInboxMessagesWithCategory list ==>"+JSON.stringify(result));

            }, (error: Error) => {
               
            });

            SmartechAppInbox.getAppInboxMessagesByApiCall(10, 1, data, (result: object[]) => {
                

            }, (error: Error) => {
                
            })

        }

       // testAppInbox();

        return {handleItemClick}
    }
};
</script>
  
<style scoped>
/* Custom styling for grid items */
.grid-item {
    background-color: #f0f0f0;
    padding: 20px;
    text-align: center;
    cursor: pointer;
}

.grid-item:hover {
    background-color: #e0e0e0;
}
</style>
  