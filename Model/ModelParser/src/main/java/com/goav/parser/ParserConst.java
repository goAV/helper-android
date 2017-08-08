/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser;

import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.HttpUrl;

public class ParserConst {


    /**
     * {@link Cookie#parseAll(HttpUrl, Headers)}
     */
    public final static String KEY_WORD = "Set-Cookie";
    public final static String KEY_WORD_S = "Cookie";

    public static boolean DEBUG = false;

    public static final String SHARE_NAME = "PRIVATE_HTTP_COOKIE";
    public static final String CACHE_CONTROL = "Cache-Control";
}
