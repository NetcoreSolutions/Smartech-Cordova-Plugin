package com.netcore.cordova;

import android.util.Log;
import com.netcore.android.smartechpush.SmartPush;
import com.netcore.android.smartechpush.notification.SMTNotificationReceivedListener;
import com.netcore.android.smartechpush.pnpermission.SMTNotificationPermissionCallback;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.Iterator;

public class SmartechPushCordova extends CordovaPlugin implements SMTNotificationReceivedListener {

    SmartPush smartPush;
    private static final String TAG = SmartechPushCordova.class.getName();
    private static final String SMARTPUSH_OPT_PUSHNOTIFICATION = "optPushNotification";
    private static final String SMARTPUSH_HAS_OPTED_PUSHNOTIFICATION = "hasOptedPushNotification";
    private static final String SMARTPUSH_GET_DEVICE_PUSH_TOKEN = "getDevicePushToken";
    private static final String SMARTPUSH_SET_DEVICE_PUSH_TOKEN = "setDevicePushToken";
    private static final String SMARTPUSH_FETCH_ALREADY_GENERATED_TOKEN = "fetchAlreadyGeneratedTokenFromFCM";
    private static final String SmartechNotificationReceived = "SmartechNotificationReceived";
    private static final String SMARTPUSH_REQUEST_NOTIFICATION_PERMISSION = "requestNotificationPermission";
    private static final String SMARTECH_HANDLE_PUSH_NOTIFICATION = "handlePushNotification";
    private static final String SMARTPUSH_UPDATE_NOTIFICATION_PERMISSION = "updateNotificationPermission";
    private static final String SMARTPUSH_REGISTER_PUSH_NOTIFICATION_AUTHORIZATION = "registerForPushNotificationWithAuthorizationOptions";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        smartPush = SmartPush.getInstance(new WeakReference<>(cordova.getActivity()));
        smartPush.setSMTNotificationReceivedListener(this);
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        boolean result = false;
        try {
            switch (action) {

                case SMARTPUSH_OPT_PUSHNOTIFICATION:
                    result = optPushNotification(data);
                    break;

                case SMARTPUSH_HAS_OPTED_PUSHNOTIFICATION:
                    result = hasOptedPushNotification(callbackContext);
                    break;

                case SMARTPUSH_GET_DEVICE_PUSH_TOKEN:
                    result = getDevicePushToken(callbackContext);
                    break;

                case SMARTPUSH_SET_DEVICE_PUSH_TOKEN:
                    result = setDevicePushToken(data);
                    break;

                case SMARTPUSH_FETCH_ALREADY_GENERATED_TOKEN:
                    result = fetchAlreadyGeneratedTokenFromFCM();
                    break;

                case SMARTPUSH_REQUEST_NOTIFICATION_PERMISSION:
                    result = requestNotificationPermission(callbackContext);
                    break;

                case SMARTPUSH_UPDATE_NOTIFICATION_PERMISSION:
                    result = updateNotificationPermission(data);
                    break;

                case SMARTPUSH_REGISTER_PUSH_NOTIFICATION_AUTHORIZATION:
                    result = registerForPushNotificationWithAuthorizationOptions(data);
                    break;

                case SMARTECH_HANDLE_PUSH_NOTIFICATION:
                    result = handlePushNotification(callbackContext, data);
                    break;

                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    private boolean optPushNotification(JSONArray data) {
        try {
            boolean isPushNotificationOptIn = data.getBoolean(0);
            smartPush.optPushNotification(isPushNotificationOptIn);
        } catch (Exception e) {
            Log.e(TAG, "optPushNotification error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to get the device push token used by Smartech SDK.
     */
    private boolean getDevicePushToken(CallbackContext callbackContext) {
        try {
            String token = smartPush.getDevicePushToken();
            SmartPushCallbackHelper.callbackSuccess(callbackContext, token);
        } catch (Exception e) {
            Log.e(TAG, "getDevicePushToken error message:" + e.getMessage());
        }
        return true;
    }

    /**
     * This method is used to get the current status of opt push notifications.
     */
    private boolean hasOptedPushNotification(CallbackContext callbackContext) {

        try {
            boolean value = smartPush.hasOptedPushNotification();
            SmartPushCallbackHelper.callbackSuccess(callbackContext, value);
        } catch (Exception e) {
            Log.e(TAG, "hasOptedPushNotification error message:" + e.getMessage());
        }
        return true;
    }

    private boolean setDevicePushToken(JSONArray data) {
        try {
            String token = data.getString(0);
            smartPush.setDevicePushToken(token);
        } catch (Exception e) {
            Log.e(TAG, "setDevicePushToken error message:" + e.getMessage());
        }
        return true;
    }

    private boolean fetchAlreadyGeneratedTokenFromFCM() {
        try {
            smartPush.fetchAlreadyGeneratedTokenFromFCM();
        } catch (Exception e) {
            Log.e(TAG, "fetchAlreadyGeneratedTokenFromFCM error message:" + e.getMessage());
        }
        return true;
    }

    private boolean handlePushNotification(CallbackContext callbackContext, JSONArray data) {
        try {
            JSONObject objNotification = data.getJSONObject(0);
            Log.i(TAG, "Notification payload is " + objNotification.toString());

            boolean isPushFromSmartech = smartPush.isNotificationFromSmartech(objNotification);
            SmartPushCallbackHelper.callbackSuccess(callbackContext, isPushFromSmartech);

            if (isPushFromSmartech) {
                smartPush.handlePushNotification(objNotification.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "handlePushNotification error message:" + e.getMessage());
            SmartPushCallbackHelper.callbackSuccess(callbackContext, false);
        }
        return true;
    }

    @Override
    public void onNotificationReceived(String payload) {
        try {
            System.out.println("SmartPush ReactNative Module On Notification Received : " + payload);
            JSONObject obj = jsonParsing(new JSONObject(payload));
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String javascriptCode = "javascript:cordova.fireDocumentEvent('" + SmartechNotificationReceived + "', " + obj + ");";
                    webView.loadUrl(javascriptCode);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exposed method to request the Notification Permission
     *
     * @param : Callback interface for getting status of Notification permission.
     */

    public boolean requestNotificationPermission(CallbackContext callbackContext) {
        try {
            smartPush.requestNotificationPermission(new SMTNotificationPermissionCallback() {
                @Override
                public void notificationPermissionStatus(int i) {
                    SmartPushCallbackHelper.callbackSuccess(callbackContext, i);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Exposed method to update the Notification Permission status
     *
     * @param data: status of permission either enabled
     *                            (SMTPNPermissionConstants.SMT_PN_PERMISSION_GRANTED)
     *                            or
     *                            disabled
     *                            (SMTPNPermissionConstants.SMT_PN_PERMISSION_DENIED)
     */
    public boolean updateNotificationPermission(JSONArray data) {
        try {
            int status = data.getInt(0);
            smartPush.updateNotificationPermission(status);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean registerForPushNotificationWithAuthorizationOptions(JSONArray data) {
        return true;
    }

    public static JSONObject jsonParsing(JSONObject jsonObject) throws JSONException {
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
                hashMap.put(key, jsonParsing((JSONObject) value));
            } else if (value instanceof JSONArray) {
                hashMap.put(key, jsonArrayParsing((JSONArray) value));
            } else if (value.getClass().isEnum()) {
                hashMap.put(key, value.toString());
            }
        }

        return hashMap;
    }

    public static JSONArray jsonArrayParsing(JSONArray data) throws JSONException {

        JSONArray jsonArray = new JSONArray();

        if (data == null || data.length() <= 0) {
            return null;
        }

        for (int i = 0; i < data.length(); i++){
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
                    jsonArray.put(jsonParsing((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    jsonArray.put(jsonArrayParsing((JSONArray) value));
                } else if (value.getClass().isEnum()) {
                    jsonArray.put(value.toString());
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
