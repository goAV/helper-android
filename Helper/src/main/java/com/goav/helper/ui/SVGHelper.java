/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;

public final class SVGHelper {


    public static AnimatedVectorDrawableCompat getSVGAnim(Context context, @DrawableRes int drwRes) {
        return AnimatedVectorDrawableCompat.create(context, drwRes);
    }

    public static VectorDrawableCompat getSVG(Context context, @DrawableRes int drawRes) {
        return VectorDrawableCompat.create(context.getResources(), drawRes, context.getTheme());
    }

}
