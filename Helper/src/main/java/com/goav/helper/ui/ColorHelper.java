/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

public final class ColorHelper {

    public static int getColor(Context context, @ColorRes int res) {
        return ContextCompat.getColor(context, res);
    }

    public static ColorStateList getColorStateList(Context context, @ColorRes int colorRes) {
        return ContextCompat.getColorStateList(context, colorRes);
    }

}
