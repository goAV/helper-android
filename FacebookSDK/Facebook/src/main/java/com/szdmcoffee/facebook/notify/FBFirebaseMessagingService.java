/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.notify;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.notifications.NotificationsManager;
import com.google.firebase.messaging.RemoteMessage;
import com.szdmcoffee.facebook.Constants;
import com.szdmcoffee.firebase.MyFirebaseMessagingService;

import java.util.List;
import java.util.Map;

import static android.R.attr.name;
import static android.R.string.no;
import static com.szdmcoffee.facebook.Constants.FB_EXTRA;

public class FBFirebaseMessagingService extends MyFirebaseMessagingService {

//    private AppEventsLogger appEventsLogger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.tag("notify"), "FBFirebaseMessagingService create");
//        appEventsLogger = AppEventsLogger.newLogger(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;
        Map<String, String> remoteResponse = remoteMessage.getData();
        if (remoteResponse == null || remoteResponse.isEmpty()) return;
        Log.d(Constants.tag("notify"), "receiver message from server.\n " + remoteResponse.toString());
        String extra = null;
        if (remoteResponse.containsKey(FB_EXTRA)) {
            extra = remoteResponse.get(FB_EXTRA);
        }
        Bundle remoteBundle = new Bundle();
        for (Map.Entry<String, String> entry : remoteResponse.entrySet()) {
            remoteBundle.putString(entry.getKey(), entry.getValue());
        }
//        appEventsLogger.logPushNotificationOpen(remoteBundle);
        if (NotificationsManager.canPresentCard(remoteBundle)) {
            NotificationsManager.presentNotification(
                    getApplicationContext(),
                    remoteBundle,
                    createRemoteIntent(extra)
            );
        }
    }

    /**
     * @param remove extra msg
     */
    private void doMessageWithRemoteExtra(String remove) {
        Log.d(Constants.tag("notify"), "extra:" + remove);
    }

    /**
     * @param extra 内容里面要包含跳转信息
     * @return 根据内容返回跳转页面
     */
    private Intent createRemoteIntent(String extra) {
        Intent mIntent = new Intent();
        mIntent.setAction(Intent.ACTION_MAIN);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> mResolveInfo =
                getPackageManager()
                        .queryIntentActivities(mIntent, PackageManager.GET_ACTIVITIES);
        Intent intent = new Intent();
        try {
            ActivityInfo info = mResolveInfo.get(0).activityInfo;
            String mLauncherActivityName = info.name;
            ComponentName componentName = new ComponentName(this, mLauncherActivityName);
            intent.setComponent(componentName);
        } catch (Exception e) {
            Log.e(Constants.tag("notify"), "createRemoteIntent", e);
            intent.setPackage(getPackageName());
            intent = mIntent;
        }
        return intent;
    }
}
