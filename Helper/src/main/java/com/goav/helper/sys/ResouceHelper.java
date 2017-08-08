/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.RawRes;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.goav.parser.Log;
import com.entertainment.galaxy.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ResouceHelper {

    private static String packe_name;

    public static int rawId(Context context, String filename) {
        return resId(context, TYPE.RAW, filename);
    }

    public static AssetFileDescriptor rawFile(Context context, String filename) throws IOException {
        return context.getAssets().openNonAssetFd(filename);
    }

    public static String rawStr(@RawRes int raw) {
        return "android.resource://" + PackagesHelper.getPackageName() + "/" + raw;
    }

    public static Uri rawUri(@RawRes int raw) {
        return Uri.parse(rawStr(raw));
    }

    public static InputStream rawIO(Context context, @RawRes int raw) {
        return context.getResources().openRawResource(raw);
    }


    public static String assStrAst(String filename) {
        return "/assets/" + filename;
    }

    public static String assStrFile(String filename) {
        return "file://assets/" + filename;
    }

    public static InputStream assStrIO(String filename) {
        return ResouceHelper.class.getResourceAsStream(assStrAst(filename));
    }

    public static int resId(Context v0, @TYPE String type, String name) {
        Resources res = v0.getResources();

        if (TextUtils.isEmpty(packe_name)) {
            packe_name = PackagesHelper.getPackageName(v0);
        }

        int resId = res.getIdentifier(name, type.toString(), packe_name);

        if (resId <= 0) {
            Log.d("获取资源ID失败:(packageName=" + packe_name + " type=" + type + " name=" + name);
        }
        return resId;
    }


    public
    @Retention(RetentionPolicy.SOURCE)
    @StringDef
    @interface TYPE {
        String LAYOUT = "layout";

        String ID = "id";

        String DRAWABLE = "drawable";

        String STYLE = "style";

        String STRING = "string";

        String COLOR = "color";

        String DIMEN = "dimen";

        String RAW = "raw";

        String ANIM = "anim";

        String STYLEABLE = "styleable";

    }


}
