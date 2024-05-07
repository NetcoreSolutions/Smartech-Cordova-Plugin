package com.netcore.cordova;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.netcore.android.Smartech;

import java.lang.ref.WeakReference;

public class SmartechBasePlugin implements SmartechDeeplinkReceivers.OnDeeplinkReceived {

    public static final String TAG = SmartechBasePlugin.class.getName();

    private static volatile SmartechBasePlugin instance;
    private DeeplinkCallbackHandler handler = null;
    private final Long maxDelayTime = 3000L;
    private final Long delayDiff = 100L;
    private final Long startDelayTime = 400L;
    private Smartech smartech = null;

    private SmartechBasePlugin() {
    }

    public static SmartechBasePlugin getInstance() {
        if (instance == null) {
            synchronized (SmartechBasePlugin.class) {
                if (instance == null) {
                    instance = new SmartechBasePlugin();
                }
            }
        }
        return instance;
    }

    public void setHandlerListener(DeeplinkCallbackHandler handler) {
        this.handler = handler;
    }


    public void init(Application context) {
        try {
            smartech = Smartech.getInstance((new WeakReference<>(context)));
            smartech.initializeSdk(context);
            SmartechDeeplinkReceivers.setRegisterCallback(this);
            SmartechDeeplinkReceivers deeplinkReceiver = new SmartechDeeplinkReceivers();

            IntentFilter filter = new IntentFilter("com.smartech.EVENT_PN_INBOX_CLICK");
            if (Build.VERSION.SDK_INT >= 34) {
                context.registerReceiver(deeplinkReceiver, filter, Context.RECEIVER_EXPORTED);
            } else {
                context.registerReceiver(deeplinkReceiver, filter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDebugLevel(int debugLevel) {
        if (smartech != null) {
            smartech.setDebugLevel(debugLevel);
        }
    }

    @Override
    public void onDeeplinkReceived(Intent intent) {
        try {
            if (handler == null) {
                handleHandlerInit(startDelayTime, intent);
            } else {
                handler.onMessageReceived(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleHandlerInit(Long delay, Intent intent) {
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (handler == null) {
                        Log.e(TAG, "Handler delay is " + delay + " handler instance is " + handler);
                        if (delay < maxDelayTime) {
                            handleHandlerInit((delay + delayDiff), intent);
                        }
                    } else {
                        handler.onMessageReceived(intent);
                    }
                }
            }, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
