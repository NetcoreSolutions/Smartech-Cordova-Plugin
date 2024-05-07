export interface Category {
    categoryName: string;
}
export declare class SmartechAppInboxPlugin {
    INBOX_MESSAGE: number;
    READ_MESSAGE: number;
    UNREAD_MESSAGE: number;
    /**
     * Retrieves the list of categories for the AppInbox.
     * @param {Function} handler - A callback function to handle the result of the category list retrieval.
     */
    getAppInboxCategoryList(handler: (result: object[]) => void): void;
    /**
     * Retrieves in-app inbox messages filtered by specified categories.
     * @param {Category[]} categories - An array of Category objects representing the categories to filter messages by.
     * @param {Function} handlerSuccess - A callback function to handle the successful retrieval of messages.
     * @param {Function} handlerError - A callback function to handle errors that occur during message retrieval.
     */
    getAppInboxMessagesWithCategory(categories: Category[], handlerSuccess: (result: object[]) => void, handlerError: (error: Error) => void): void;
    /**
     * Retrieves AppInbox messages of a specified type.
     *
     * @param {number} inboxMessageType - The type of inbox messages to retrieve the count for.
     *                                Possible values are:
     *                                - 1: ALL_MESSAGE
     *                                - 2: INBOX_MESSAGE
     *                                - 3: READ_MESSAGE
     *                                - 4: UNREAD_MESSAGE
     * @param {Function} handler - A callback function to handle the retrieved inbox messages.
     *
     */
    getAppInboxMessages(inboxMessageType: number, handler: (result: object[]) => void): void;
    /**
     * Retrieves the count of AppInbox messages based on the specified message type.
     *
     * @param {number} messageType - The type of inbox messages to retrieve the count for.
     *                                Possible values are:
     *                                - 1: ALL_MESSAGE
     *                                - 2: INBOX_MESSAGE
     *                                - 3: READ_MESSAGE
     *                                - 4: UNREAD_MESSAGE
     * @param {Function} handler - A callback function to handle the retrieved message count.
     *                              It receives a number parameter representing the count of messages.
     *
     */
    getAppInboxMessageCount(messageType: number, handler: (count: number) => void): void;
    /**
     * Marks a message as viewed in the AppInbox.
     *
     * @param {string} appInboxMessage - The AppInbox message to be marked as viewed.
     */
    markMessageAsViewed(appInboxMessage: Object): void;
    /**
     * Marks a message as clicked in the AppInbox.
     *
     * @param {string} trid - The trid of the clicked message.
     * @param {string} deeplink - The deep link of the clicked message.
     *
     */
    markMessageAsClicked(trid: string, deeplink: string): void;
    /**
     * Marks a message as dismissed in AppInbox.
     *
     * @param {string} appInboxMessage - The payload of the message to be marked as dismissed.
     * @param {Function} handler - A callback function to handle the result of the dismissal operation.
     *                              It receives a boolean parameter indicating the success of the operation.
     */
    markMessageAsDismissed(appInboxMessage: Object, handler: (result: boolean) => void): void;
    /**
     * Retrieves AppInbox messages through an API call with specified parameters.
     * @param {number} messageLimit - The maximum number of messages to retrieve.
     * @param {number} messageType - The type of inbox messages to retrieve.
     * @param {object[]} appInboxCategoryArray - An array of objects representing the categories to filter messages by.
     * @param {Function} handlerSuccess - A callback function to handle the successful retrieval of messages.
     *                                      It receives an array of objects representing the retrieved messages.
     *
     * @param {Function} handlerError - A callback function to handle errors that occur during message retrieval.
     *                                      It receives an Error object representing the encountered error.
     *
     */
    getAppInboxMessagesByApiCall(messageLimit: number, messageType: number, appInboxCategoryArray: object[], handlerSuccess: (result: object[]) => void, handlerError: (error: Error) => void): void;
    /**
     * Marks a message as clicked and performs a copy action associated with it.
     *
     * @param {any} selectedAction - The selected action to be performed.
     * @param {string} trid - The trid of the clicked message.
     */
    copyMessageAsClicked(selectedAction: object, trid: string): void;
}
declare const SmartechAppInboxCordova: SmartechAppInboxPlugin;
export default SmartechAppInboxCordova;
