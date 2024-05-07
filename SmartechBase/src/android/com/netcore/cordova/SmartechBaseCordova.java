package com.netcore.cordova;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.netcore.android.SMTBundleKeys;
import com.netcore.android.Smartech;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;


/**
 * This class echoes a string called from JavaScript.
 */
public class SmartechBaseCordova extends CordovaPlugin implements DeeplinkCallbackHandler {

    private static final String TAG = "SmartechBaseCordova";
    private static final String SET_DEEPLINK_INIT = "setDeeplinkInit";
    private static final String SET_USER_IDENTITY = "setUserIdentity";
    private static final String GET_USER_IDENTITY = "getUserIdentity";

    private static final String CLEAR_USER_IDENTITY = "clearUserIdentity";

    private static final String LOGIN = "login";
    private static final String LOGOUT_CLEAR_USER_IDENTITY = "logoutAndClearUserIdentity";
    private static final String TRACK_EVENT = "trackEvent";
    private static final String SET_USER_LOCATION = "setUserLocation";
    private static final String TRACK_APP_INSTALL = "trackAppInstall";
    private static final String TRACK_APP_UPDATE = "trackAppUpdate";
    private static final String TRACK_APP_INSTALL_UPDATE_SMARTECH = "trackAppInstallUpdateBySmartech";
    private static final String UPDATE_USER_PROFILE = "updateUserProfile";
    private static final String HAS_OPTED_TRACKING = "hasOptedTracking";
    private static final String HAS_OPTED_INAPPMESSAGE = "hasOptedInAppMessage";
    private static final String OPT_TRACKING = "optTracking";
    private static final String OPT_INAPP_MESSAGE = "optInAppMessage";
    private static final String GET_APP_ID = "getAppId";

    private static final String GET_DEVICE_ID = "getDeviceGuid";
    private static final String GET_SDK_VERSION = "getSDKVersion";


    private static final String SmartechDeeplink = "SmartechDeeplink";
    private static final String smtDeeplinkSourceIdentifier = "smtDeeplinkSource";
    private static final String smtDeeplinkIdentifier = "smtDeeplink";
    private static final String smtPayloadIdentifier = "smtPayload";
    private static final String smtCustomPayloadIdentifier = "smtCustomPayload";
    private static final String smtIsDeeplinkFromBg = "smtIsDeeplinkFromBg";

    //For legacy support
    private static final String SmartechDeeplinkNotification = "SmartechDeeplinkNotification";

    private static final String SmartechDeepLinkIdentifier = "deeplink";
    private static final String SmartechCustomPayloadIdentifier = "customPayload";


    public static boolean isSmartechDeeplinkInit = false;
    private final Long maxDelayTime = 3000L;
    private final Long delayDiff = 100L;
    private final Long startDelayTime = 400L;

    Smartech smartech;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        initSDK();
    }

    private void initSDK() {
        SmartechBasePlugin instance = SmartechBasePlugin.getInstance();
        instance.setHandlerListener(this);
        smartech = Smartech.getInstance(new WeakReference<>(cordova.getActivity()));
    }

    private boolean setDeeplinkInit() {
        isSmartechDeeplinkInit = true;
        return true;
    }

    private boolean setUserIdentity(JSONArray data) {

        try {
            String identity = data.getString(0);
            smartech.setUserIdentity(identity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean getUserIdentity(CallbackContext callbackContext) {
        try {
            String userIdentity = smartech.getUserIdentity();
            CallbackHelper.callbackSuccess(callbackContext, userIdentity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getUserIdentity error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method will be used track app install event.
     */
    private boolean trackAppInstall() {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.trackAppInstall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    /**
     * This method will be used track app update event.
     */
    private boolean trackAppUpdate() {
        try {
            smartech.trackAppUpdate();
        } catch (Exception e) {
            Log.e(TAG, "trackAppUpdate error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method will be used to install app install and update event will be track by SmartechSDK.
     */
    private boolean trackAppInstallUpdateBySmartech() {
        try {
            smartech.trackAppInstallUpdateBySmartech();
        } catch (Exception e) {
            Log.e(TAG, "trackAppInstallUpdateBySmartech error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method will be used to track event.
     *
     * @param data JSONArray containing eventName and payload
     * @return
     */
    private boolean trackEvent(JSONArray data) {
        try {
            String eventName = data.getString(0);
            JSONObject payload = data.getJSONObject(1);
            HashMap<String, Object> hMapPayload = jsonToHashMap(payload);
            smartech.trackEvent(eventName, hMapPayload);
        } catch (Exception e) {
            Log.e(TAG, "trackEvent error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method will be used to set users identity.
     *
     * @param data JSONArray containing users identity
     * @return
     */
    private boolean login(JSONArray data) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String identity = data.getString(0);
                    smartech.setUserIdentity(identity);
                    smartech.login(identity);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "login error message:" + e.getMessage());
                }
            }
        });
        return true;
    }

    /**
     * @param data Json array containing boolean value
     * @return
     */
    private boolean logoutAndClearUserIdentity(JSONArray data) {
        try {
            boolean value = data.getBoolean(0);
            smartech.logoutAndClearUserIdentity(value);
        } catch (Exception e) {
            Log.e(TAG, "logoutAndClearUserIdentity error message:" + e.getMessage());
        }
        return true;
    }

    private boolean clearUserIdentity() {
        try {
            smartech.clearUserIdentity();
        } catch (Exception e) {
            Log.e(TAG, "clearUserIdentity error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to update the user profile.
     * It should be called by the developer to update all the user related attributes to Smartech.
     *
     * @param data It will contain profile payload.
     * @return
     */
    private boolean updateUserProfile(JSONArray data) {
        try {
            JSONObject objProfileData = data.getJSONObject(0);
            HashMap<String, Object> hMapProfile = jsonToHashMap(objProfileData);
            smartech.updateUserProfile(hMapProfile);
        } catch (Exception e) {
            Log.e(TAG, "updateUserProfile error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to opt tracking.
     *
     * @param data "Contains a boolean value to determine whether tracking should occur or not."
     * @return
     */
    private boolean optTracking(JSONArray data) {
        try {
            boolean isTracking = data.getBoolean(0);
            smartech.optTracking(isTracking);
        } catch (Exception e) {
            Log.e(TAG, "optTracking error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to get the current status of opt tracking.
     */
    private boolean hasOptedTracking(CallbackContext callbackContext) {
        try {
            boolean value = smartech.hasOptedTracking();
            CallbackHelper.callbackSuccessBoolean(callbackContext, value);
        } catch (Exception e) {
            Log.e(TAG, "hasOptedTracking error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to opt InApp messages.
     *
     * @param data contains a boolean value, true if the in-app message should be displayed to the user,
     *             false if the in-app message should not be displayed.
     *             Its default value is true.
     * @return
     */
    private boolean optInAppMessage(JSONArray data) {
        try {
            boolean isInMessagesOptIn = data.getBoolean(0);
            smartech.optInAppMessage(isInMessagesOptIn);
        } catch (Exception e) {
            Log.e(TAG, "optInAppMessage error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * It will return the status of opt InApp messages.
     */
    private boolean hasOptedInAppMessage(CallbackContext callbackContext) {

        try {
            boolean value = smartech.hasOptedInAppMessage();
            CallbackHelper.callbackSuccessBoolean(callbackContext, value);
        } catch (Exception e) {
            Log.e(TAG, "hasOptedInAppMessage error message:" + e.getMessage());
        }

        return true;
    }

    // Location Method

    /**
     * This method will be used to set user's location in SDK.
     *
     * @param data JSONArray containing latitude and longitude.
     * @return
     */
    private boolean setUserLocation(JSONArray data) {
        try {
            Double latitude = data.getDouble(0);
            Double longitude = data.getDouble(1);
            PluginResult result;
            if (latitude != null && longitude != null) {
                Location location = new Location("SmartechCordova");
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                smartech.setUserLocation(location);
            }
        } catch (Exception e) {
            Log.e(TAG, "setUserLocation error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to get the app id used by the Smartech SDK.
     */
    private boolean getAppId(CallbackContext callbackContext) {
        try {
            String appId = smartech.getAppID();
            CallbackHelper.callbackSuccess(callbackContext, appId);
        } catch (Exception e) {
            Log.e(TAG, "getAppId error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to get the device unique id used by Smartech SDK.
     */
    private boolean getDeviceUniqueId(CallbackContext callbackContext) {
        try {
            String deviceId = smartech.getDeviceUniqueId();
            CallbackHelper.callbackSuccess(callbackContext, deviceId);
        } catch (Exception e) {
            Log.e(TAG, "getDeviceUniqueId error message:" + e.getMessage());
        }

        return true;
    }

    /**
     * This method is used to get the current Smartech SDK version.
     */
    private boolean getSDKVersion(CallbackContext callbackContext) {
        try {
            String sdkVersion = smartech.getSDKVersion();
            CallbackHelper.callbackSuccess(callbackContext, sdkVersion);
        } catch (Exception e) {
            Log.e(TAG, "getSDKVersion error message:" + e.getMessage());
        }

        return true;
    }

    //private boolean
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {

        boolean result = false;
        try {
            switch (action) {

                case SET_DEEPLINK_INIT:
                    result = setDeeplinkInit();
                    break;
                case SET_USER_IDENTITY:
                    result = setUserIdentity(data);
                    break;
                case GET_USER_IDENTITY:
                    result = getUserIdentity(callbackContext);
                    break;

                case CLEAR_USER_IDENTITY:
                    result = clearUserIdentity();
                    break;

                case LOGIN:
                    result = login(data);
                    break;

                case LOGOUT_CLEAR_USER_IDENTITY:
                    result = logoutAndClearUserIdentity(data);
                    break;

                case TRACK_EVENT:
                    result = trackEvent(data);
                    break;

                case SET_USER_LOCATION:
                    result = setUserLocation(data);
                    break;

                case TRACK_APP_INSTALL:
                    result = trackAppInstall();
                    break;

                case TRACK_APP_UPDATE:
                    result = trackAppUpdate();
                    break;

                case TRACK_APP_INSTALL_UPDATE_SMARTECH:
                    result = trackAppInstallUpdateBySmartech();
                    break;

                case UPDATE_USER_PROFILE:
                    result = updateUserProfile(data);
                    break;

                case HAS_OPTED_TRACKING:
                    result = hasOptedTracking(callbackContext);
                    break;

                case HAS_OPTED_INAPPMESSAGE:
                    result = hasOptedInAppMessage(callbackContext);
                    break;

                case OPT_TRACKING:
                    result = optTracking(data);
                    break;

                case OPT_INAPP_MESSAGE:
                    result = optInAppMessage(data);
                    break;

                case GET_APP_ID:
                    result = getAppId(callbackContext);
                    break;

                case GET_DEVICE_ID:
                    result = getDeviceUniqueId(callbackContext);
                    break;

                case GET_SDK_VERSION:
                    result = getSDKVersion(callbackContext);
                    break;

                default:
                    result = false;
                    break;
            }
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    public static HashMap<String, Object> jsonToHashMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            hashMap.put(key, value);
        }
        return hashMap;
    }

    public void handleDeeplinkIntent(Intent intent) {

        try {
            JSONObject deeplinkPayload = processDeeplinkIntent(intent);
            boolean isFromBg = false;
            
            if (deeplinkPayload.has(smtIsDeeplinkFromBg) && !deeplinkPayload.isNull(smtIsDeeplinkFromBg)) {
                isFromBg = deeplinkPayload.getBoolean(smtIsDeeplinkFromBg);
            }

            if (isFromBg) {

                if (isSmartechDeeplinkInit) {
                    sendDeeplinkCallback(deeplinkPayload);
                } else {
                    Thread backgroundThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handleDeeplinkInTerminatedState(startDelayTime, deeplinkPayload);
                        }
                    });
                    backgroundThread.start();
                }
            } else {
                sendDeeplinkCallback(deeplinkPayload);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDeeplinkCallback(JSONObject payload) {
        try {
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String javascriptCode = "javascript:cordova.fireDocumentEvent('" + SmartechDeeplink + "', " + payload + ");";
                    webView.loadUrl(javascriptCode);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject processDeeplinkIntent(Intent intent) {
        JSONObject smtData = new JSONObject();
        if (intent != null) {
            Bundle bundleExtra = intent.getExtras();
            if (bundleExtra != null) {

                String smtDeeplinkSource = "";
                String smtDeeplink = "";
                String smtPayload = "";
                String smtCustomPayload = "";
                Boolean isFromBg = false;

                if (bundleExtra.containsKey(SMTBundleKeys.SMT_KEY_DEEPLINK_SOURCE)) {
                    smtDeeplinkSource = bundleExtra.getString(SMTBundleKeys.SMT_KEY_DEEPLINK_SOURCE);
                } else {
                    Log.v(TAG, "does not have deeplink source.");
                }

                if (bundleExtra.containsKey(SMTBundleKeys.SMT_KEY_DEEPLINK)) {
                    smtDeeplink = bundleExtra.getString(SMTBundleKeys.SMT_KEY_DEEPLINK);
                } else {
                    Log.v(TAG, "does not have deeplink path.");
                }

                if (bundleExtra.containsKey(SMTBundleKeys.SMT_KEY_PAYLOAD)) {
                    smtPayload = bundleExtra.getString(SMTBundleKeys.SMT_KEY_PAYLOAD);
                } else {
                    Log.v(TAG, "does not have smt payload.");
                }

                if (bundleExtra.containsKey(SMTBundleKeys.SMT_KEY_CUSTOM_PAYLOAD)) {
                    smtCustomPayload = bundleExtra.getString(SMTBundleKeys.SMT_KEY_CUSTOM_PAYLOAD);
                } else {
                    Log.v(TAG, "does not have custom payload.");
                }

                if (bundleExtra.containsKey(SMTBundleKeys.SMT_KEY_IS_DEEPLINK_FROM_BG)) {
                    isFromBg = bundleExtra.getBoolean(SMTBundleKeys.SMT_KEY_IS_DEEPLINK_FROM_BG, false);
                }


                try {
                    smtData.put(smtDeeplinkSourceIdentifier, smtDeeplinkSource);
                    smtData.put(smtDeeplinkIdentifier, smtDeeplink);
                    JSONObject rMapPayload = null;
                    if (smtPayload != null && !smtPayload.isEmpty()) {
                        rMapPayload = jsonToHashMapParsing(new JSONObject(smtPayload));
                    }
                    smtData.put(smtPayloadIdentifier, rMapPayload);
                    JSONObject rMapCustomPayload = null;
                    if (smtCustomPayload != null && !smtCustomPayload.isEmpty()) {
                        rMapCustomPayload = jsonToHashMapParsing(new JSONObject(smtCustomPayload));
                    }
                    smtData.put(smtCustomPayloadIdentifier, rMapCustomPayload);
                    //This is for legacy support, will remove this after some releases when customers upgraded
                    smtData.put(SmartechDeepLinkIdentifier, smtDeeplink);
                    smtData.put(SmartechCustomPayloadIdentifier, smtCustomPayload);
                    smtData.put(smtIsDeeplinkFromBg, isFromBg);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }
        }
        return smtData;
    }

    public static JSONObject jsonToHashMapParsing(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }

        JSONObject hashMap = new JSONObject();
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);

            if (value == null) {
                hashMap.put(key, null);
            } else if (value instanceof Boolean) {
                hashMap.put(key, value);
            } else if (value instanceof Integer) {
                hashMap.put(key, value);
            } else if (value instanceof Double || value instanceof Long || value instanceof Float) {
                String str = String.valueOf(value);
                hashMap.put(key, Double.parseDouble(str));
            } else if (value instanceof String) {
                hashMap.put(key, value);
            } else if (value instanceof JSONObject) {
                hashMap.put(key, jsonToHashMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                hashMap.put(key, jsonArrayToHashMap((JSONArray) value));
            } else if (value.getClass().isEnum()) {
                hashMap.put(key, value.toString());
            }
        }

        return hashMap;
    }

    public static JSONArray jsonArrayToHashMap(JSONArray data) throws JSONException {

        JSONArray jsonArray = new JSONArray();

        if (data == null || data.length() <= 0) {
            return null;
        }

        for (int i = 0; i < data.length(); i++) {
            try {
                Object value = jsonArray.get(i);

                if (value == null) {
                    jsonArray.put(null);
                } else if (value instanceof Boolean) {
                    jsonArray.put((Boolean) value);
                } else if (value instanceof Integer) {
                    jsonArray.put((Integer) value);
                } else if (value instanceof Double || value instanceof Long || value instanceof Float) {
                    String str = String.valueOf(value);
                    jsonArray.put(Double.parseDouble(str));
                } else if (value instanceof String) {
                    jsonArray.put(value.toString());
                } else if (value instanceof JSONObject) {
                    jsonArray.put(jsonToHashMapParsing((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    jsonArray.put(jsonArrayToHashMap((JSONArray) value));
                } else if (value.getClass().isEnum()) {
                    jsonArray.put(value.toString());
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }


    /**
     * Handles the deep link when the application is in a terminated state, with retry behavior.
     * <p>
     * This function is responsible for processing the deep link received when the application
     * is not running. It takes a [map] containing the parameters of the deep link and performs
     * the necessary actions. If the processing fails, it retries sending the deep link with
     * increasing delay times, up to a maximum of three retries.
     * <p>
     * Retry condition is based on isSmartechDeeplinkInit value.
     *
     * @param delay           Delay to pass handler for checking if listener is initialised.
     * @param deeplinkPayload A [ReadableMap] containing the parameters of the deep link.
     *                        The keys represent the parameter names, and the values represent the parameter values.
     *                        This map typically includes information about the deep link, such as target screen,
     *                        data to be passed, etc.
     */
    private void handleDeeplinkInTerminatedState(Long delay, JSONObject deeplinkPayload) {
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, "Deeplink Handler - delay is: " + delay + " seconds & isSmartechDeeplinkInit: " + isSmartechDeeplinkInit);

                        if (isSmartechDeeplinkInit) {
                            sendDeeplinkCallback(deeplinkPayload);
                        } else {
                            if (delay < maxDelayTime) {
                                // Increment delay and call startHandler again
                                handleDeeplinkInTerminatedState((delay + delayDiff), deeplinkPayload);
                            } else {
                                Log.i(TAG, "Maximum retries reached. Invoke Deeplink callback");
                                sendDeeplinkCallback(deeplinkPayload);
                            }
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                        sendDeeplinkCallback(deeplinkPayload);
                    }
                }
            }, delay);
        } catch (Throwable t) {
            t.printStackTrace();
            sendDeeplinkCallback(deeplinkPayload);
        }
    }

    @Override
    public void onMessageReceived(Intent intent) {
        handleDeeplinkIntent(intent);
    }
}
