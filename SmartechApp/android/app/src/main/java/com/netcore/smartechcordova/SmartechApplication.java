package com.netcore.smartechcordova;

import android.app.Application;
import android.util.Log;

import com.netcore.android.logger.SMTDebugLevel;
import com.netcore.cordova.SmartechBasePlugin;

public class SmartechApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Log.e("knightfury", "application class");
        SmartechBasePlugin smartechBasePlugin = SmartechBasePlugin.getInstance();
        smartechBasePlugin.init(this,  9);

    }
}
