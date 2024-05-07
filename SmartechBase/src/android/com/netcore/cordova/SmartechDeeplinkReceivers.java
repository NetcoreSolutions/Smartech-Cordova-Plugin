package com.netcore.cordova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SmartechDeeplinkReceivers extends BroadcastReceiver {
  public  static  OnDeeplinkReceived onDeeplinkReceived;

  public static void setRegisterCallback(OnDeeplinkReceived onDeeplinkReceivedCB) {
    onDeeplinkReceived = onDeeplinkReceivedCB;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    try {
      onDeeplinkReceived.onDeeplinkReceived(intent);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  interface OnDeeplinkReceived{
    void onDeeplinkReceived(Intent deeplinkPayload);
  }

}
