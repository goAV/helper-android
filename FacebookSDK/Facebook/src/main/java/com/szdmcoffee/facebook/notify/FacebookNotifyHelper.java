/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.notify;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.notifications.NotificationsManager;
import com.szdmcoffee.facebook.Constants;

public class FacebookNotifyHelper {

    // Add to each long-lived activity
    public static void onResume(Context context) {
        AppEventsLogger.activateApp(context);
    }

    // for Android, you should also log app deactivation
    public static void onPause(Context context) {
        AppEventsLogger.deactivateApp(context);
    }

    // when refresh firebase token
    public static void onStartWith(String token) {
        Log.d(Constants.tag("notify"), "token=" + token);
        AppEventsLogger.setPushNotificationsRegistrationId(token);
    }

    // show notify with app card
    public static void onAppCardNotification(Activity a) {
        NotificationsManager.presentCardFromNotification(a);
    }


}
