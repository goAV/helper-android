/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.szdmcoffee.live.API;
import com.szdmcoffee.live.BeautyLiveApplication;
import com.szdmcoffee.live.constant.Const;

/**
 * 转换类
 */
public final class ContextHelper {


    public static Context get() {
        return BeautyLiveApplication.getContextInstance();
    }

    /**
     * @param context
     * @return {@link Context#getApplicationContext()}
     */
    public static Context basicContext(Context context) {
        if (context == null) {
            return BeautyLiveApplication.getContextInstance();
        } else {
            return context.getApplicationContext();
        }
    }


    /**
     * @param context
     * @return activity
     */
    public static Activity basicActivity(Object context) {
        if (context == null) return null;
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return basicActivity(((ContextWrapper) context).getBaseContext());
        } else if (context instanceof Fragment) {
            return ((Fragment) context).getActivity();
        } else if (context instanceof android.app.Fragment) {
            return ((android.app.Fragment) context).getActivity();
        } else if (context instanceof View) {
            return basicActivity(((View) context).getContext());
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity21Impl(Activity activity, View translaView) {
        Bundle bundle = null;
        if (API.v(21)) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, translaView, translaView.getTransitionName());
            bundle = optionsCompat.toBundle();
        }
        ActivityCompat.startActivity(activity, null, bundle);
    }

    public static void allDestroy() {
        ContextHelper.get().sendBroadcast(new Intent(Const.LIVE_FINISH_BROADCAST_ACTION));
    }
}
