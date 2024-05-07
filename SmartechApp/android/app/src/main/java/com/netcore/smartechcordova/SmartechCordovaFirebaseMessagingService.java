package com.netcore.smartechcordova;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netcore.android.smartechpush.SmartPush;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class SmartechCordovaFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        SmartPush.getInstance(new WeakReference<Context>(this)).setDevicePushToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        boolean isPushFromSmartech = false;
        try {
            isPushFromSmartech = SmartPush.getInstance(new WeakReference<Context>(this)).isNotificationFromSmartech(new JSONObject(remoteMessage.getData().toString()));
            if(isPushFromSmartech){
              //  SmartPush.getInstance(new WeakReference<>(getApplicationContext())).handlePushNotification(remoteMessage.getData().toString());
                SmartPush.getInstance(new WeakReference<>(getApplicationContext())).handleRemotePushNotification(remoteMessage);
            } else {
                // Notification received from other sources
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
