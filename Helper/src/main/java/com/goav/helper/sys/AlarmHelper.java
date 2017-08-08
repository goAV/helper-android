/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import java.util.Map;

public final class AlarmHelper {

    public static void post(Context context, PendingIntent pendingIntent) {
        Manager.getAlarm(context)
                .set(AlarmManager.RTC, System.currentTimeMillis() + 2000l, pendingIntent);
    }

}
