package com.netcore.cordova;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import androidx.annotation.Nullable;
import com.netcore.android.smartechappinbox.SmartechAppInbox;
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback;
import com.netcore.android.smartechappinbox.network.model.SMTActionButton;
import com.netcore.android.smartechappinbox.network.model.SMTCarousel;
import com.netcore.android.smartechappinbox.network.model.SMTInboxCategory;
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData;
import com.netcore.android.smartechappinbox.network.model.SMTPayload;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxMessageType;
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder;
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;


public class SmartechAppInboxCordova extends CordovaPlugin {
    SmartechAppInbox smartechAppInbox;

    public static final String TAG = "SmartechAppInboxCordova";
    private static final String SMARTECH_GET_APPINBOX_CATEGORYLIST = "getAppInboxCategoryList";
    private static final String SMARTECH_GET_APPINBOX_MESSAGES_WITH_CATEGORY = "getAppInboxMessagesWithCategory";
    private static final String SMARTECH_GET_APPINBOX_MESSAGES = "getAppInboxMessages";
    private static final String SMARTECH_GET_APPINBOX_MESSAGE_COUNT = "getAppInboxMessageCount";
    private static final String SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_VIEWED = "markMessageAsViewed";
    private static final String SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_CLICKED = "markMessageAsClicked";
    private static final String SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_DISMISSED = "markMessageAsDismissed";
    private static final String SMARTECH_GET_APPINBOX_MESSAGES_BY_API_CALL = "getAppInboxMessagesByApiCall";
    private static final String SMARTECH_GET_APPINBOX_COPY_MESSAGE_AS_CLICKED = "copyMessageAsClicked";


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        try {
            smartechAppInbox = SmartechAppInbox.getInstance(new WeakReference<>(cordova.getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        boolean result = false;
        try {

            switch (action) {

                case SMARTECH_GET_APPINBOX_CATEGORYLIST:
                    result = getAppInboxCategoryList(callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_MESSAGES_WITH_CATEGORY:
                    result = getAppInboxMessagesWithCategory(data, callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_MESSAGES:
                    result = getAppInboxMessages(data, callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_MESSAGE_COUNT:
                    result = getAppInboxMessageCount(data, callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_VIEWED:
                    result = markMessageAsViewed(data);
                    break;

                case SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_CLICKED:
                    result = markMessageAsClicked(data);
                    break;

                case SMARTECH_GET_APPINBOX_MARK_MESSAGE_AS_DISMISSED:
                    result = markMessageAsDismissed(data, callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_MESSAGES_BY_API_CALL:
                    result = getAppInboxMessagesByApiCall(data, callbackContext);
                    break;

                case SMARTECH_GET_APPINBOX_COPY_MESSAGE_AS_CLICKED:
                    result = copyMessageAsClicked(data);
                    break;


                default:
                    return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Error in execute sequence"+e.getLocalizedMessage());
        }
        return result;
    }

    public boolean getAppInboxMessages(JSONArray data, CallbackContext callbackContext) {
        try {
            int messageType = data.getInt(0);
            ArrayList<SMTInboxMessageData> appInbox;
            switch (messageType) {
                case 2:
                    appInbox = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.READ_MESSAGE);
                    break;
                case 3:
                    appInbox = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.UNREAD_MESSAGE);
                    break;
                default:
                    appInbox = smartechAppInbox.getAppInboxMessages(SMTAppInboxMessageType.INBOX_MESSAGE);
                    break;
            }
            JSONArray appInboxMessageArray = createAppInboxMessages(appInbox);
            SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, appInboxMessageArray);
        } catch (Exception e) {
            Log.e(TAG, "getAppInboxMessages error message" + e.getMessage());
            e.printStackTrace();
            SmartechAppInboxCallbackHelper.callbackError(callbackContext, e.getMessage());
        }
        return true;
    }

    private JSONArray createAppInboxMessages(ArrayList<SMTInboxMessageData> appinbox) {
        JSONArray appInboxMessageArray = new JSONArray();
        try {
            for (int i = 0; (i < appinbox.size()); i++) {
                JSONObject appInboxMessage = new JSONObject();

                SMTInboxMessageData inboxMessageData =  appinbox.get(i);
                String notificationType = inboxMessageData.getSmtPayload().getType();
                ArrayList<JSONObject> carouselArray = new ArrayList<JSONObject>();
                ArrayList<JSONObject> actionArray = new ArrayList<JSONObject>();

                SMTPayload payload =  inboxMessageData.getSmtPayload();

                appInboxMessage.put("title", payload.getTitle());
                appInboxMessage.put("subtitle", payload.getSubTitle());
                appInboxMessage.put("description", payload.getBody());
                appInboxMessage.put("notificationCategory", payload.getAppInboxCategory());
                appInboxMessage.put("trid", payload.getTrid());
                appInboxMessage.put("deeplink", payload.getDeeplink());
                appInboxMessage.put("mediaURL", payload.getMediaUrl());
                appInboxMessage.put("publishedDate", payload.getPublishedDate());
                appInboxMessage.put("status", payload.getStatus());
                appInboxMessage.put("notificationType", notificationType);

                if (notificationType.equals("CarouselLandscape") || notificationType.equals("CarouselPortrait")) {
                    ArrayList<SMTCarousel> carouselAppInboxArray = payload.getCarousel();
                    for (SMTCarousel carousel : carouselAppInboxArray) {
                        JSONObject carouselObject = new JSONObject();
                        carouselObject.put("imgUrl", carousel.getImgUrl());
                        carouselObject.put("imgUrlPath", carousel.getImgUrl());
                        carouselObject.put("imgTitle", carousel.getImgTitle());
                        carouselObject.put("imgMsg", carousel.getImgMsg());
                        carouselObject.put("imgDeeplink", carousel.getImgDeeplink());
                        carouselArray.add(carouselObject);
                    }
                }

                if (!payload.getActionButton().isEmpty()) {
                    ArrayList<SMTActionButton> actionButtonArray = (ArrayList<SMTActionButton>) payload.getActionButton();
                    for (SMTActionButton actions : actionButtonArray) {
                        JSONObject actionObject = new JSONObject();
                        actionObject.put("actionDeeplink", actions.getActionDeeplink());
                        actionObject.put("actionName", actions.getActionName());
                        actionObject.put("aTyp", actions.getATyp());
                        actionObject.put("callToAction", actions.getCallToAction());
                        actionObject.put("config_ctxt", actions.getConfigCtxt());
                        actionArray.add(actionObject);
                    }
                }

                appInboxMessage.put("carousel", carouselArray);
                appInboxMessage.put("actionButton", actionArray);

                appInboxMessageArray.put(appInboxMessage);
            }
        } catch (Exception e) {
            Log.e(TAG, "createAppInboxMessages error"+e.getMessage());
            e.printStackTrace();
        }
        return appInboxMessageArray;
    }

    public boolean getAppInboxCategoryList(CallbackContext callbackContext) {
        try {
            ArrayList<SMTInboxCategory> categoryList = smartechAppInbox.getAppInboxCategoryList();
            JSONArray array = new JSONArray();
            for (int i = 0; (i < categoryList.size()); i++) {
                JSONObject object1 = new JSONObject();
                try {
                    object1.put("categoryName", categoryList.get(i).getName());
                    object1.put("isSelected", categoryList.get(i).getState());
                    array.put(object1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, array);
        } catch (Exception e) {
            Log.e(TAG, "getAppInboxCategoryList error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public boolean getAppInboxMessagesWithCategory(JSONArray data, CallbackContext callbackContext) {
        try {
            JSONArray categoryData = data.getJSONArray(0);
            ArrayList<String> categoryList = new ArrayList<String>();
            for (int i = 0; i < categoryData.length(); i++) {
                JSONObject object1 = categoryData.getJSONObject(i);
                try {
                    categoryList.add(object1.get("categoryName").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            JSONArray appInboxMessageArray = createAppInboxMessages( smartechAppInbox.getAppInboxMessages(categoryList));
            SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, appInboxMessageArray);
        } catch (Exception e) {
            Log.e(TAG, "getAppInboxMessagesWithCategory error message" + e.getMessage());
            e.printStackTrace();
            SmartechAppInboxCallbackHelper.callbackError(callbackContext, e.getMessage());
        }
        return true;
    }

    public boolean getAppInboxMessageCount(JSONArray data, CallbackContext callbackContext) {
        try {
            int messageType = data.getInt(0);
            int inboxCount;
            switch (messageType) {
                default:
                    inboxCount = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.INBOX_MESSAGE);
                    break;
                case 2:
                    inboxCount = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.READ_MESSAGE);
                    break;
                case 3:
                    inboxCount = smartechAppInbox.getAppInboxMessageCount(SMTAppInboxMessageType.UNREAD_MESSAGE);
                    break;
            }
            SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, inboxCount);
        } catch (Exception e) {
            Log.e(TAG, "getAppInboxMessageCount error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public boolean getAppInboxMessagesByApiCall(JSONArray data, CallbackContext callbackContext) {
        try {
            int messageLimit = data.getInt(0);
            int messageType = data.getInt(1);

            JSONArray categoryData = data.getJSONArray(2);
            ArrayList<String> categoryList = new ArrayList<String>();
            for (int i = 0; i < categoryData.length(); i++) {
                JSONObject object1 = categoryData.getJSONObject(i);
                try {
                    categoryList.add(object1.get("categoryName").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SMTInboxDataType messageDataType;

            switch (messageType) {
                default:
                    messageDataType = SMTInboxDataType.ALL;
                    break;
                case 2:
                    messageDataType = SMTInboxDataType.LATEST;
                    break;
                case 3:
                    messageDataType = SMTInboxDataType.EARLIEST;
                    break;
            }

            SMTAppInboxRequestBuilder builder = new SMTAppInboxRequestBuilder.Builder(messageDataType)
                    .setCallback(new SMTInboxCallback() {
                        @Override
                        public void onInboxProgress() {

                        }

                        @Override
                        public void onInboxSuccess(@Nullable List<SMTInboxMessageData> list) {
                            try {
                                JSONArray appInboxArray = createAppInboxMessages(smartechAppInbox.getAppInboxMessages(categoryList));
                                SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, appInboxArray);
                            } catch (Throwable e) {
                                e.printStackTrace();
                                SmartechAppInboxCallbackHelper.callbackError(callbackContext, e.getMessage());
                            }
                        }

                        @Override
                        public void onInboxFail() {

                        }
                    })
                    .setCategory(categoryList).setLimit(messageLimit).build();
            smartechAppInbox.getAppInboxMessages(builder);
        } catch (Exception e) {
            Log.e(TAG, "getAppInboxMessagesByApiCall error message" + e.getMessage());
            e.printStackTrace();
            SmartechAppInboxCallbackHelper.callbackError(callbackContext, e.getMessage());
        }
        return true;
    }

    public boolean markMessageAsViewed(JSONArray data) {
        try {
            JSONObject inboxMesaage = data.getJSONObject(0);
            SMTInboxMessageData appInboxMessage = smartechAppInbox.getAppInboxMessageById((String) inboxMesaage.get("trid"));
            if (appInboxMessage != null) {
                smartechAppInbox.markMessageAsViewed(appInboxMessage);
            } else {
                Log.e(TAG, "MarkedAsViewed param is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "markMessageAsViewed error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    public boolean markMessageAsClicked(JSONArray data) {
        try {
            String trid = data.getString(0);
            String deeplink = data.getString(1);
            SMTInboxMessageData appInboxMessage = smartechAppInbox.getAppInboxMessageById(trid);
            if (appInboxMessage != null) {
                smartechAppInbox.markMessageAsClicked(deeplink, appInboxMessage);
            }
        } catch (Exception e) {
            Log.e(TAG, "markMessageAsClicked error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    public boolean copyMessageAsClicked(JSONArray data) {
        try {
            JSONObject objActionButton = data.getJSONObject(0);
            String trId = data.getString(1);
            ClipboardManager clipboard = (ClipboardManager) cordova.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(null, objActionButton.get("config_ctxt").toString());
            clipboard.setPrimaryClip(clip);
            if (!objActionButton.get("actionDeeplink").toString().isEmpty()) {
                SMTInboxMessageData appInboxMessage = smartechAppInbox.getAppInboxMessageById(trId);
                if (appInboxMessage != null) {
                    smartechAppInbox.markMessageAsClicked(objActionButton.get("actionDeeplink").toString(), appInboxMessage);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "copyMessageAsClicked error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public boolean markMessageAsDismissed(JSONArray data, CallbackContext callbackContext) {
        try {
            JSONObject inboxMessage = data.getJSONObject(0);
            SMTInboxMessageData appInboxMessage = smartechAppInbox.getAppInboxMessageById((String) inboxMessage.get("trid"));
            if (appInboxMessage != null) {
                smartechAppInbox.markMessageAsDismissed(appInboxMessage);
            }
            SmartechAppInboxCallbackHelper.callbackSuccess(callbackContext, true);
        } catch (Exception e) {
            Log.e(TAG, "markMessageAsDismissed error message" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

}
