/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.content.Context;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public final class KeyBoardHelper {

    public static void hidden(Context context, View v) {
        InputMethodManager manager = Manager.getInput(ContextHelper.basicContext(context));
        if (manager != null && manager.isActive() && v != null) {
            manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void show(Context context, EditText v) {
        InputMethodManager manager = Manager.getInput(ContextHelper.basicContext(context));
        if (manager != null && v != null) {
            manager.showSoftInput(v, 0);
        }
    }


    public static void toggle() {
        InputMethodManager manager = Manager.getInput(ContextHelper.get());
        if (manager != null) {
            manager.toggleSoftInput(0, 0);
        }
    }
}
