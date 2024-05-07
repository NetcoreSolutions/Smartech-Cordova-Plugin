export declare class SmartechPushPlugin {
    SmartechNotificationReceived: string;
    addListener(type: string, listener: (ev: Event) => any): void;
    removeListener(type: string, listener: (ev: Event) => any): void;
    /**
     * Checks whether the user has opted for push notifications.
     * @param {Function} handler - A callback function to handle the result of the check.
     */
    hasOptedPushNotification(handler: (isOpted: boolean) => void): void;
    /**
     * Sets the user's preference for push notifications.
     * @param {boolean} value - The value indicating whether the user opts for push notifications.
     */
    optPushNotification(value: boolean): void;
    /**
     * Retrieves the push notification token associated with the device.
     * @param {Function} handler - A callback function to handle the retrieved push notification token.
     */
    getDevicePushToken(handler: (token: string) => void): void;
    /**
     * Sets the push notification token inside the SDK.
     * @param token {string} token - The push notification token to be set for the device.
     */
    setDevicePushToken(token: String): void;
    /**
     * Fetches the already generated token from Firebase Cloud Messaging (FCM)
     * and sets it to the SDK.
     */
    fetchAlreadyGeneratedTokenFromFCM(): void;
    /**
     * Pass the notificatio payload to be handled by Smartech SDK.
     * @param {string} payload - The payload of the received push notification.
     */
    handlePushNotification(payload: object): void;
    registerForPushNotificationWithAuthorizationOptions(): void;
    /**
     * Requests permission for notifications from the user.
     * @param {Function} handler - A callback function to handle the permission status.
     */
    requestNotificationPermission(handler: (status: number) => void): void;
    /**
     * Updates the notification permission status within the SDK.
     * @param {number} status - The updated notification permission status.
     */
    updateNotificationPermission(status: number): void;
}
declare const SmartechPushCordova: SmartechPushPlugin;
export default SmartechPushCordova;
