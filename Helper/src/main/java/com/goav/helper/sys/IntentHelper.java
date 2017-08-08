/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.content.ClipDescription;
import android.content.Intent;

import com.entertainment.galaxy.R;

public class IntentHelper {

    public static Intent getSystemIntent(String action) {
        return new Intent(action);
    }


    public static Intent getSysShare(String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ClipDescription.MIMETYPE_TEXT_PLAIN);
        intent.putExtra(Intent.EXTRA_TITLE,title);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return Intent.createChooser(intent, ContextHelper.get().getString(R.string.all_share_platform));
    }


    public static Intent getSysGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

}
