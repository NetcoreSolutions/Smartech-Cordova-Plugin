<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>
                    {{ pageTitle }}
                </ion-title>
                <ion-buttons slot="end">
                    <ion-button @click="refreshData">
                        <ion-icon :icon="refreshIcon"></ion-icon>
                    </ion-button>
                </ion-buttons>
            </ion-toolbar>
        </ion-header>

        <ion-content class="ion-padding">
            <ion-chip-group v-if="showChips">
                <ion-chip color="danger" v-for="(chip, index) in categoryChips" :key="index" @click="toggleFilter(chip)"
                    :class="{ 'selected': selectedChips.includes(chip) }">
                    <ion-label>{{ chip }}</ion-label>
                </ion-chip>
            </ion-chip-group>

            <ion-item>
                <ion-label>Choose an option:</ion-label>
                <ion-select v-model="selectedOption" interface="popover" @ionChange="onSelectionChange">
                    <ion-select-option v-for="option in options" :key="option.value" :value="option.value"
                        :selected="option === options[0]">
                        {{ option.label }}
                    </ion-select-option>
                </ion-select>
            </ion-item>
            <ion-list class="custom-item" v-for="(item, index) in dynamicArray" :key="index">
                <ion-item color="light">
                    <ion-label style="padding-top: 10;">
                        <h2>{{ item.title }}</h2>
                        <p v-html="item.description"></p>
                        <p>{{ item.deeplink }}</p>
                    </ion-label>
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-page>
</template>

<script lang="ts">
import { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonList, IonItemSliding, IonItem, IonLabel, IonItemOptions, IonItemOption, IonChip, IonSelect, IonSelectOption } from '@ionic/vue';
import { SMTAppInboxMessage } from '@/interfaces/SMTAppInboxMessage';
import SmartechAppInbox from 'smartech-appinbox-cordova';
import { SMTAppInboxCategory } from '@/interfaces/SMTAppInboxCategory';
import { archive, heart, trash, refresh, codeDownload } from 'ionicons/icons';
import { onMounted, ref } from 'vue';

export default {
    components: { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonList, IonItemSliding, IonItem, IonLabel, IonItemOptions, IonItemOption, IonChip, IonSelect, IonSelectOption },
    setup() {
        const dynamicArray = ref([] as SMTAppInboxMessage[]);
        const ogData = ref([] as SMTAppInboxMessage[]);

        // Header
        const pageTitle = ref('App Inbox By API');
        const itemCount = ref(0);

        // chips
        const showChips = ref(false);
        const categoryChips = ref<string[]>([]);
        const selectedChips = ref<string[]>([]); // Track selected chips

        // category selection
        const options = [
            { label: 'All', value: 1 },
            { label: 'Latest', value: 2 },
            { label: 'Earliest', value: 3 }
        ];
        const selectedOption = ref(1);

        let defaultSelectedMessageTyoe = selectedOption.value;

        const onSelectionChange = (event: CustomEvent) => {
            console.log("Knightfury  onSelection change" + event.detail.value);
            selectedOption.value = event.detail.value;

            if (defaultSelectedMessageTyoe !== event.detail.value) {
                getAppInboxCategory(event.detail.value);
            }
            defaultSelectedMessageTyoe = event.detail.value;
        };

        // Fetch category data
        const getAppInboxCategory = (type: number) => {
            showChips.value = true;
            SmartechAppInbox.getAppInboxCategoryList((result) => {
                categoryChips.value = [];
                const data = result as SMTAppInboxCategory[]
                for (var val of data) {
                    categoryChips.value.push(val.categoryName)
                }
                console.log("Appinbox result is " + result);

                getAppInboxMessagesByApi(10, type, result);
            }
            )
        }

        const getAppInboxMessagesByApi = (count: number, messageType: number, categoryList: Object[]) => {

            SmartechAppInbox.getAppInboxMessagesByApiCall(count, messageType, categoryList, (result) => {
                const array = result as SMTAppInboxMessage[];
                dynamicArray.value = array
                ogData.value = array
                console.log("getAppInboxMessagesByApiCall value of array" + JSON.stringify(array));
            }, (error) => {
                console.log("getAppInboxMessagesByApi error" + error);
            })
        }

        const filterData = () => {
            if (selectedChips.value.length === 0) {
                dynamicArray.value = ogData.value
            } else {
                const emptyArray: SMTAppInboxMessage[] = [];

                for (var category of selectedChips.value) {
                    for (var item of ogData.value) {
                        if (category === item.notificationCategory) {
                            emptyArray.push(item)
                        }
                    }
                }
                dynamicArray.value = emptyArray;
            }
        }

        const toggleFilter = (chip: string) => {

            const index = selectedChips.value.indexOf(chip);
            if (index === -1) {
                selectedChips.value.push(chip);
            } else {
                selectedChips.value.splice(index, 1);
            }
            filterData()
        };

        //fetchAppInboxMessage(defaultSelectedMessageTyoe)
        getAppInboxCategory(selectedOption.value);

        const refreshData = () => {
           // fetchAppInboxMessage(defaultSelectedMessageTyoe)
            getAppInboxCategory(selectedOption.value);
        }

        return {
            dynamicArray, archive, heart, trash, showChips, categoryChips, refreshIcon: refresh, pageTitle, itemCount, refreshData, toggleFilter, selectedChips, options,
            selectedOption,
            onSelectionChange
        }
    }
}
</script>
<style scoped>
.custom-item {
    margin-bottom: 5px;
    /* Adjust this value as needed */
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


ion-chip-group {
    display: flex;
    flex-wrap: nowrap;
    /* Prevent chips from wrapping to next line */
    overflow-x: auto;
    /* Allow horizontal scrolling if chips exceed container width */
}

.selected {
    background-color: var(--ion-color-primary);
    /* Change background color */
    color: white;
    /* Change text color */
}
</style>