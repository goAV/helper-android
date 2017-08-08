/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser;

import android.content.Context;

import com.goav.parser.okhttp.GoPrefs;

public class GoModelParser {

    public static void init(Context context) {
        GoPrefs.init(context, ParserConst.SHARE_NAME);
    }

    public static void init(Context context, String filename) {
        GoPrefs.init(context, filename);
    }

    public static void debug(boolean debug) {
        ParserConst.DEBUG = debug;
    }

}
