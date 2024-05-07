// turn off type checking
declare let window: any;

const kPluginName = 'SmartechPushCordova';

export class SmartechPushPlugin {

    SmartechNotificationReceived = "SmartechNotificationReceived";

    addListener(type: string, listener: (ev: Event) => any): void {
        document.addEventListener(type, listener);
    }

    removeListener(type: string, listener: (ev: Event) => any): void {
        document.removeEventListener(type, listener);
    }

    /**
     * Checks whether the user has opted for push notifications.
     * @param {Function} handler - A callback function to handle the result of the check.
     */

    hasOptedPushNotification(handler: (isOpted: boolean) => void): void {
        window.cordova.exec(handler, function(){}, kPluginName, "hasOptedPushNotification", []);
    };

    /**
     * Sets the user's preference for push notifications.
     * @param {boolean} value - The value indicating whether the user opts for push notifications. 
     */
    optPushNotification(value: boolean, ): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "optPushNotification", [value]);
    };

    /**
     * Retrieves the push notification token associated with the device.
     * @param {Function} handler - A callback function to handle the retrieved push notification token.
     */
    getDevicePushToken(handler: (token: string) => void): void {
        window.cordova.exec(handler, function(){}, kPluginName, "getDevicePushToken", []);
    };

    /**
     * Sets the push notification token inside the SDK.
     * @param token {string} token - The push notification token to be set for the device.
     */
    setDevicePushToken(token: String): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "setDevicePushToken", [token]);
    };

    /**
     * Fetches the already generated token from Firebase Cloud Messaging (FCM)
     * and sets it to the SDK.
     */
    fetchAlreadyGeneratedTokenFromFCM(): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "fetchAlreadyGeneratedTokenFromFCM", []);
    };

    /**
     * Pass the notificatio payload to be handled by Smartech SDK.
     * @param {string} payload - The payload of the received push notification.
     */
    handlePushNotification(payload: object): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "handlePushNotification", [payload]);
    };

    registerForPushNotificationWithAuthorizationOptions(): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "registerForPushNotificationWithAuthorizationOptions", []);
    };

    /**
     * Requests permission for notifications from the user.
     * @param {Function} handler - A callback function to handle the permission status.
     */
    requestNotificationPermission(handler: (status: number) => void): void {
        window.cordova.exec(handler, function(){}, kPluginName, "requestNotificationPermission", []);
    };

    /**
     * Updates the notification permission status within the SDK.
     * @param {number} status - The updated notification permission status.
     */
    updateNotificationPermission(status: number): void {
        window.cordova.exec(function(){}, function(){}, kPluginName, "updateNotificationPermission", [status]);
    };


}

const SmartechPushCordova = new SmartechPushPlugin();

if (!window.plugins) {
    window.plugins = {};
}

if (!window.plugins.SmartechPushCordova) {
    window.plugins.SmartechPushCordova = SmartechPushCordova;
}

export default SmartechPushCordova;