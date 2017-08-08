/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.szdmcoffee.live.API;

import java.util.Locale;

public final class LocalHelper {

    private LocalHelper() {
    }


    private static class P {
        private static final LocalHelper LOCAL_HELPER = new LocalHelper();
    }

    private String mDefaultLocal = "en";

    private String getDefaultLocal() {
        return mDefaultLocal;
    }

    private void setDefaultLocal(String mDefaultLocal) {
        this.mDefaultLocal = mDefaultLocal;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setDefault(Context context) {
        Locale defa = Locale.getDefault();
        String def = defa.getLanguage();
        if (def.equals("zh") || def.equals("en")) {
            //default support language
            P.LOCAL_HELPER.setDefaultLocal(def);
        } else {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            Locale locale = Locale.ENGLISH;
            P.LOCAL_HELPER.setDefaultLocal(locale.getLanguage());
            if (API.v(17)) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            resources.updateConfiguration(config, dm);
        }
    }

    public static String getDefault() {
        return P.LOCAL_HELPER.getDefaultLocal();
    }


}
