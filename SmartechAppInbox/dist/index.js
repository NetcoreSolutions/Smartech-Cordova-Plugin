"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SmartechAppInboxPlugin = void 0;
var kPluginName = 'SmartechAppInboxCordova';
var SmartechAppInboxPlugin = /** @class */ (function () {
    function SmartechAppInboxPlugin() {
        // Below are the AppInbox message type.
        this.INBOX_MESSAGE = 1;
        this.READ_MESSAGE = 2;
        this.UNREAD_MESSAGE = 3;
    }
    /**
     * Retrieves the list of categories for the AppInbox.
     * @param {Function} handler - A callback function to handle the result of the category list retrieval.
     */
    SmartechAppInboxPlugin.prototype.getAppInboxCategoryList = function (handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "getAppInboxCategoryList", []);
    };
    ;
    /**
     * Retrieves in-app inbox messages filtered by specified categories.
     * @param {Category[]} categories - An array of Category objects representing the categories to filter messages by.
     * @param {Function} handlerSuccess - A callback function to handle the successful retrieval of messages.
     * @param {Function} handlerError - A callback function to handle errors that occur during message retrieval.
     */
    SmartechAppInboxPlugin.prototype.getAppInboxMessagesWithCategory = function (categories, handlerSuccess, handlerError) {
        try {
            window.cordova.exec(function (result) { return handlerSuccess(JSON.parse(result)); }, handlerError, kPluginName, "getAppInboxMessagesWithCategory", [categories]);
        }
        catch (error) {
            console.error("SmarechAppInbox getAppInboxMessagesWithCategory error", error);
        }
    };
    ;
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
    SmartechAppInboxPlugin.prototype.getAppInboxMessages = function (inboxMessageType, handler) {
        try {
            window.cordova.exec(handler, function () { }, kPluginName, "getAppInboxMessages", [inboxMessageType]);
        }
        catch (error) {
            console.error("SmarechAppInbox getAppInboxMessages error", error);
        }
    };
    ;
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
    SmartechAppInboxPlugin.prototype.getAppInboxMessageCount = function (messageType, handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "getAppInboxMessageCount", [messageType]);
    };
    ;
    /**
     * Marks a message as viewed in the AppInbox.
     *
     * @param {string} appInboxMessage - The AppInbox message to be marked as viewed.
     */
    SmartechAppInboxPlugin.prototype.markMessageAsViewed = function (appInboxMessage) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "markMessageAsViewed", [appInboxMessage]);
    };
    ;
    /**
     * Marks a message as clicked in the AppInbox.
     *
     * @param {string} trid - The trid of the clicked message.
     * @param {string} deeplink - The deep link of the clicked message.
     *
     */
    SmartechAppInboxPlugin.prototype.markMessageAsClicked = function (trid, deeplink) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "markMessageAsClicked", [trid, deeplink]);
    };
    ;
    /**
     * Marks a message as dismissed in AppInbox.
     *
     * @param {string} appInboxMessage - The payload of the message to be marked as dismissed.
     * @param {Function} handler - A callback function to handle the result of the dismissal operation.
     *                              It receives a boolean parameter indicating the success of the operation.
     */
    SmartechAppInboxPlugin.prototype.markMessageAsDismissed = function (appInboxMessage, handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "markMessageAsDismissed", [appInboxMessage]);
    };
    ;
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
    SmartechAppInboxPlugin.prototype.getAppInboxMessagesByApiCall = function (messageLimit, messageType, appInboxCategoryArray, handlerSuccess, handlerError) {
        try {
            window.cordova.exec(handlerSuccess, handlerError, kPluginName, "getAppInboxMessagesByApiCall", [messageLimit, messageType, appInboxCategoryArray]);
        }
        catch (error) {
            console.error("SmarechAppInbox getAppInboxMessagesByApiCall error", error);
        }
    };
    ;
    /**
     * Marks a message as clicked and performs a copy action associated with it.
     *
     * @param {any} selectedAction - The selected action to be performed.
     * @param {string} trid - The trid of the clicked message.
     */
    SmartechAppInboxPlugin.prototype.copyMessageAsClicked = function (selectedAction, trid) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "copyMessageAsClicked", [selectedAction, trid]);
    };
    ;
    return SmartechAppInboxPlugin;
}());
exports.SmartechAppInboxPlugin = SmartechAppInboxPlugin;
var SmartechAppInboxCordova = new SmartechAppInboxPlugin();
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.Smartech) {
    window.plugins.SmartechAppInboxCordova = SmartechAppInboxCordova;
}
exports.default = SmartechAppInboxCordova;
