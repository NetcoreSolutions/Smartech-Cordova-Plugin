<template>
    <ion-page>
        <ion-header>
            <ion-toolbar>
                <ion-buttons slot="start">
                    <ion-back-button defaultHref="/"></ion-back-button>
                </ion-buttons>
                <ion-title>
                    {{ pageTitle }} ({{ itemCount }})
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


            <ion-list>
                <ion-item-sliding class="custom-item" v-for="(item, index) in dynamicArray" :key="index"
                    :ref="setSlidingRef(index)" @click="clickItem(item)">
                    <ion-item color="light">
                        <ion-label style="padding-top: 10;">
                            <h2>{{ item.title }}</h2>
                            <p v-html="item.description"></p>
                            <p>{{ item.deeplink }}</p>
                        </ion-label>
                    </ion-item>

                    <ion-item-options side="end">
                        <ion-item-option color="warning" @click="viewedItem(item)">
                            <ion-icon slot="end" :icon="archive"></ion-icon>
                            Viewed
                        </ion-item-option>

                        <ion-item-option color="danger" @click="dissmisItem(item, index)">
                            <ion-icon slot="end" :icon="trash"></ion-icon>
                            Dismiss
                        </ion-item-option>
                    </ion-item-options>
                </ion-item-sliding>
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
import Smartech from 'smartech-base-cordova';
import SmartechAppInboxCordova from 'smartech-appinbox-cordova';
export default {
    components: { IonPage, IonHeader, IonToolbar, IonButtons, IonBackButton, IonTitle, IonContent, IonList, IonItemSliding, IonItem, IonLabel, IonItemOptions, IonItemOption, IonChip, IonSelect, IonSelectOption },
    setup() {

        const ionItemSlidingRef = ref()
        const dynamicArray = ref([] as SMTAppInboxMessage[]);
        const ogData = ref([] as SMTAppInboxMessage[]);
        const slidingItems = ref<(HTMLIonItemSlidingElement | null)[]>(Array(dynamicArray.value.length).fill(null));

        // Header
        const pageTitle = ref('App Inbox');
        const itemCount = ref(0);

        let defaultSelectedMessageTyoe = 1;

        // chips
        const showChips = ref(false);
        const categoryChips = ref<string[]>([]);
        const selectedChips = ref<string[]>([]); // Track selected chips

        // category selection
        const options = [
            { label: 'All Message', value: 1 },
            { label: 'Read Message', value: 2 },
            { label: 'Unread Message', value: 3 },
        ];
        const selectedOption = ref(1);

        const onSelectionChange = (event: CustomEvent) => {
            console.log("Knightfury  onSelection change" + event.detail.value);
            selectedOption.value = event.detail.value;

            if (defaultSelectedMessageTyoe !== event.detail.value) {
                fetchAppInboxMessage(event.detail.value);
                getAppInboxMessageCount(event.detail.value);
            }
            defaultSelectedMessageTyoe = event.detail.value;

        };

        const renderHTML = (item: SMTAppInboxMessage) => {
            return `
        <h2>${item.title}</h2>
        <p>${item.description}</p>
        <p>${item.deeplink}</p>`

        }

        // Fetch category data
        const getAppInboxCategory = () => {
            showChips.value = true;
            SmartechAppInbox.getAppInboxCategoryList((result) => {
                categoryChips.value = [];
                const data = result as SMTAppInboxCategory[]
                for (var val of data) {
                    categoryChips.value.push(val.categoryName)
                }
                console.log("Appinbox result is " + result);

            }
            )
        }

        // Fetch refresh count
        const getAppInboxMessageCount = async (messageType: number) => {
            console.log("Message type is "+ messageType);
            SmartechAppInbox.getAppInboxMessageCount(messageType, async (count) => {
                console.log("message count" + count)
                itemCount.value = count
            }
            )
        }

        const fetchAppInboxMessage = (messageType: number) => {
            SmartechAppInbox.getAppInboxMessages(messageType, (result) => {
                const array = result as SMTAppInboxMessage[];
                dynamicArray.value = array
                ogData.value = array
                slidingItems.value = Array(dynamicArray.value.length).fill(null)
                console.log("Knightfury value of array" + JSON.stringify(array));
            })
        }

        const getAppInboxMessagesByApi = (count: number, messageType: number, categoryList: Object[]) => {

            SmartechAppInbox.getAppInboxMessagesByApiCall(count, messageType, categoryList, (result) => {
                const array = result as SMTAppInboxMessage[];
                dynamicArray.value = array
                ogData.value = array
                slidingItems.value = Array(dynamicArray.value.length).fill(null)
                console.log("getAppInboxMessagesByApiCall value of array" + JSON.stringify(array));
            }, (error) => {
                console.log("getAppInboxMessagesByApi error" + error);
            })
        }

        const setSlidingRef = (index: number) => (el: HTMLIonItemSlidingElement | null) => {
            slidingItems.value[index] = el;
        };



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


        fetchAppInboxMessage(defaultSelectedMessageTyoe)
        getAppInboxMessageCount(SmartechAppInbox.INBOX_MESSAGE)
        getAppInboxCategory()


        const viewedItem = (result: SMTAppInboxMessage) => {
            console.log("marked message as viewed called.");
            SmartechAppInbox.markMessageAsViewed(result)
        }

        const dissmisItem = (payload: SMTAppInboxMessage, index: number) => {

            SmartechAppInbox.markMessageAsDismissed(payload, (response) => {
                console.log("dismiss result is " + response);
                const slidingItem = slidingItems.value[index];
                if (slidingItem) {
                    slidingItem.$el.close();
                    setTimeout(() => {
                        dynamicArray.value.splice(index, 1);
                    }, 500); // Duration of the animation in milliseconds

                }
            })

        }

        const clickItem = (result: SMTAppInboxMessage) => {
            SmartechAppInbox.markMessageAsClicked(result.trid, result.deeplink)

        }

        const refreshData = () => {
            fetchAppInboxMessage(defaultSelectedMessageTyoe)
            getAppInboxMessageCount(defaultSelectedMessageTyoe)
            getAppInboxCategory()
        }

        return {
            dynamicArray, viewedItem, dissmisItem, clickItem, archive, heart, trash, slidingItems,
            setSlidingRef, showChips, categoryChips, refreshIcon: refresh, pageTitle, itemCount, refreshData, toggleFilter, selectedChips, options,
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