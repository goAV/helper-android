/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.notify;

import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.iid.FirebaseInstanceId;
import com.szdmcoffee.facebook.Constants;
import com.szdmcoffee.firebase.MyFirebaseInstanceIDService;

public class FBFirebaseInstanceIDService extends MyFirebaseInstanceIDService {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.tag("notify"), "FBFirebaseInstanceIDService create");
    }

    @Override
    public void onTokenRefresh() {
        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            FacebookNotifyHelper.onStartWith(token);
        } catch (Exception e) {
            Log.e(Constants.tag("notify"), "Failed to complete token refresh", e);
        }
    }
}
