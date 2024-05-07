"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SmartechPushPlugin = void 0;
var kPluginName = 'SmartechPushCordova';
var SmartechPushPlugin = /** @class */ (function () {
    function SmartechPushPlugin() {
        this.SmartechNotificationReceived = "SmartechNotificationReceived";
    }
    SmartechPushPlugin.prototype.addListener = function (type, listener) {
        document.addEventListener(type, listener);
    };
    SmartechPushPlugin.prototype.removeListener = function (type, listener) {
        document.removeEventListener(type, listener);
    };
    /**
     * Checks whether the user has opted for push notifications.
     * @param {Function} handler - A callback function to handle the result of the check.
     */
    SmartechPushPlugin.prototype.hasOptedPushNotification = function (handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "hasOptedPushNotification", []);
    };
    ;
    /**
     * Sets the user's preference for push notifications.
     * @param {boolean} value - The value indicating whether the user opts for push notifications.
     */
    SmartechPushPlugin.prototype.optPushNotification = function (value) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "optPushNotification", [value]);
    };
    ;
    /**
     * Retrieves the push notification token associated with the device.
     * @param {Function} handler - A callback function to handle the retrieved push notification token.
     */
    SmartechPushPlugin.prototype.getDevicePushToken = function (handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "getDevicePushToken", []);
    };
    ;
    /**
     * Sets the push notification token inside the SDK.
     * @param token {string} token - The push notification token to be set for the device.
     */
    SmartechPushPlugin.prototype.setDevicePushToken = function (token) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "setDevicePushToken", [token]);
    };
    ;
    /**
     * Fetches the already generated token from Firebase Cloud Messaging (FCM)
     * and sets it to the SDK.
     */
    SmartechPushPlugin.prototype.fetchAlreadyGeneratedTokenFromFCM = function () {
        window.cordova.exec(function () { }, function () { }, kPluginName, "fetchAlreadyGeneratedTokenFromFCM", []);
    };
    ;
    /**
     * Pass the notificatio payload to be handled by Smartech SDK.
     * @param {string} payload - The payload of the received push notification.
     */
    SmartechPushPlugin.prototype.handlePushNotification = function (payload) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "handlePushNotification", [payload]);
    };
    ;
    SmartechPushPlugin.prototype.registerForPushNotificationWithAuthorizationOptions = function () {
        window.cordova.exec(function () { }, function () { }, kPluginName, "registerForPushNotificationWithAuthorizationOptions", []);
    };
    ;
    /**
     * Requests permission for notifications from the user.
     * @param {Function} handler - A callback function to handle the permission status.
     */
    SmartechPushPlugin.prototype.requestNotificationPermission = function (handler) {
        window.cordova.exec(handler, function () { }, kPluginName, "requestNotificationPermission", []);
    };
    ;
    /**
     * Updates the notification permission status within the SDK.
     * @param {number} status - The updated notification permission status.
     */
    SmartechPushPlugin.prototype.updateNotificationPermission = function (status) {
        window.cordova.exec(function () { }, function () { }, kPluginName, "updateNotificationPermission", [status]);
    };
    ;
    return SmartechPushPlugin;
}());
exports.SmartechPushPlugin = SmartechPushPlugin;
var SmartechPushCordova = new SmartechPushPlugin();
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.SmartechPushCordova) {
    window.plugins.SmartechPushCordova = SmartechPushCordova;
}
exports.default = SmartechPushCordova;
