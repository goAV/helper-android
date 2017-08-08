/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.szdmcoffee.live.API;

public final class DrawableHelper {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setTint(ImageView view, int color) {
        Drawable drawable = view.getDrawable();
        if (API.v(Build.VERSION_CODES.LOLLIPOP)) {
            drawable.setTint(color);
        } else {
            DrawableCompat.setTint(drawable, color);
        }
        view.setImageDrawable(drawable);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setTint(ImageView view, ColorStateList stateList) {
        Drawable drawable = view.getDrawable();
        if (API.v(Build.VERSION_CODES.LOLLIPOP)) {
            drawable.setTintList(stateList);
        } else {
            DrawableCompat.setTintList(drawable, stateList);
        }
        view.setImageDrawable(drawable);
    }

}
