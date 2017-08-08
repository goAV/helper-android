/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.share;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.widget.GameRequestDialog;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.szdmcoffee.facebook.Constants;

import java.util.Arrays;

import static android.app.ProgressDialog.show;

public final class FacebookShareHelper {

    private FacebookShareHelper() {
    }

    public static void showGame(Activity activity, String title, String message) {
        Log.d(Constants.tag("share"), "title=" + title + ";message=" + message);
        GameRequestDialog.show(activity, new GameRequestContent.Builder()
                .setMessage(message)
//                .setActionType(GameRequestContent.ActionType.SEND)
//                .setObjectId("")
                .setTitle(title)
                .build());
    }

    public static void showGame(Fragment fragment, String title, String message) {
        Log.d(Constants.tag("share"), "title=" + title + ";message=" + message);
        GameRequestDialog.show(fragment, new GameRequestContent.Builder()
                .setMessage(message)
                .setTitle(title)
//                .setActionType(GameRequestContent.ActionType.SEND)
//                .setObjectId("")
                .build());
    }

    public static void showTest(Object activity) {
        if (activity instanceof Activity) {
            showGame((Activity) activity, "老司机带你飞~", "小朋友快来玩呐~");
        } else if (activity instanceof Fragment) {
            showGame((Fragment) activity, "老司机带你飞~", "小朋友快来玩呐~");
        }
    }


    public static void showMedia(Activity activity, ShareMedia... constants) {
        showMedia(activity, new ShareMediaContent.Builder().addMedia(Arrays.asList(constants)).build());
    }

    public static void showMedia(Activity activity, ShareContent constants) {
        ShareDialog.show(activity, constants);
    }


    public static void showMsg(Activity a, ShareContent constants) {
        MessageDialog.show(a, constants);
    }

}
