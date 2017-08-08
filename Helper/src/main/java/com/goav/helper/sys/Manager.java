/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.szdmcoffee.live.BeautyLiveApplication;

public final class Manager {

    public static SharedPreferences getShare(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getShare() {
        return getShare(BeautyLiveApplication.getContextInstance());
    }

    public static AlarmManager getAlarm(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static NotificationManager getNotify(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static InputMethodManager getInput(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static TelephonyManager telephony(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static ClipboardManager getCopy(Context context){
        return (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
