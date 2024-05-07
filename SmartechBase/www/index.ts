// To turn off type checking
declare let window: any;

const kPluginName = 'SmartechBaseCordova';

export class SmartechBasePlugin {

    SmartechDeeplink = "SmartechDeeplink";

    addListener(type: string, listener: (ev: Event) => any): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'setDeeplinkInit', []);
        document.addEventListener(type, listener);
    }

    removeListener(type: string, listener: (ev: Event) => any): void {
        document.removeEventListener(type, listener);
    }

    /**
     * Tracks the installation of the application.
     */
    trackAppInstall(): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'trackAppInstall', []);
    };

    /**
     * Tracks the update of the application.
     */
    trackAppUpdate(): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'trackAppUpdate', []);
    };

    /**
     * Tracks the installation or update of the application using Smartech.
     */
    trackAppInstallUpdateBySmartech(): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'trackAppInstallUpdateBySmartech', []);
    };
        
    /**
     * Tracks a custom event with associated payload.
     * @param {string} eventName - The name of the event to track.
     * @param {object} payload - The payload associated with the event.
     */
    trackEvent(eventName: string, payload: object): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'trackEvent', [eventName, payload]);
    };

     /**
     * Logs in the user with the provided identity.
     * @param {string} identity - The identity of the user to log in. 
     */
    login(identity: string): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'login', [identity]);
    };

    /**
     * Logs out the user and clears their user identity if specified.
     * @param {boolean} clearIdentity - A boolean value indicating whether to clear the user identity.
     */
    logoutAndClearUserIdentity(clearIdentity: boolean): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'logoutAndClearUserIdentity', [clearIdentity]);
    };

    /**
     * Sets the user identity to the provided value.
     * @param {string} identity - The identity of the user to be set.
     */
    setUserIdentity(identity: string): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'setUserIdentity', [identity]);
    };

    /**
     * Retrieves the identity of the current user.
     * @param {Function} handler - A callback function to handle the retrieved user identity.
     *                              It receives a string parameter representing the user identity.
     */
    getUserIdentity(handler: (identity: string) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'getUserIdentity', []);
    };

     /**
     * Clears the identity of the current user.
     */
    clearUserIdentity(): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'clearUserIdentity', []);
    };

     /**
     *  Updates the user profile with the provided payload.
     * 
     *  @param {object} payload - The payload containing the updated user profile information.
     */
    updateUserProfile(payload: object): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'updateUserProfile', [payload]);
    };

    /**
     * Sets the Event tracking preference.
     * 
     * @param {boolean} isOpted - A boolean value indicating whether to opt for tracking or not.
     */
    optTracking(isOpted: boolean): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'optTracking', [isOpted]);
    }

    /**
     * Checks whether the user has opted for tracking.
     * 
     * @param {Function} handler - A callback function to handle the result of the tracking preference check.
     *                               It receives a boolean parameter indicating whether to opt for tracking or not.
     * 
     */
    hasOptedTracking(handler: (isOpted: boolean) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'hasOptedTracking', []);
    }

     /**
     * Sets the preference for receiving in-app messages.
     * 
     * @param {boolean} isOpted - A boolean value indicating whether to opts for in-app messages or not.
     */
    optInAppMessage(isOpted: boolean): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'optInAppMessage', [isOpted]);
    }

     /**
     * 
     * Checks whether the user has opted for receiving in-app messages.
     * 
     * @param {Function} handler - A callback function to handle the result of the in-app message preference check.
     *                              It receives a boolean parameter indicating whether the user has opted for in-app messages.
     */
    hasOptedInAppMessage(handler: (isOpted: boolean) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'hasOptedInAppMessage', []);
    }

    /**
     * 
     * Sets the location using latitude and longitude coordinates.
     * 
     * @param {number} latitude - The latitude coordinate of the user's location.
     * @param {number} longitude - The longitude coordinate of the user's location.
     */
    setUserLocation(latitude: number, longitude: number): void {
        window.cordova.exec(function () { }, function () { }, kPluginName, 'setUserLocation', [latitude, longitude])
    }

    /**
     * Retrieves the unique identifier of the  application.
     * @param {Function} handler - A callback function to handle the retrieved AppId.
     *                              It receives a string parameter representing the AppId.
     */
    getAppId(handler: (appId: string) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'getAppId', [])
    }

    /**
     * Retrieves the unique identifier (GUID) associated with the current device.
     * 
     * @param {Function} handler - A callback function to handle the retrieved device GUID.
     *                              It receives a string parameter representing the device GUID.
     */
    getDeviceGuid(handler: (deviceGuid: string) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'getDeviceGuid', [])
    }

     /**
     * Retrieves the version of the SDK.
     * 
     * @param {Function} handler - A callback function to handle the retrieved SDK version.
     *                              It receives a string parameter representing the SDK version.
     */
    getSDKVersion(handler: (sdkVersion: string) => void): void {
        window.cordova.exec(handler, function () { }, kPluginName, 'getSDKVersion', [])
    }
}

const SmartechBaseCordova = new SmartechBasePlugin();

if (!window.plugins) {
    window.plugins = {};
}

if (!window.plugins.SmartechBaseCordova) {
    window.plugins.SmartechBaseCordova = SmartechBaseCordova;
}

export default SmartechBaseCordova;