/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

abstract class GoInterceptCookie implements Interceptor, CookieJar {

    private final CookieJar cookieJar;

    public GoInterceptCookie(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }


    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return this.cookieJar.loadForRequest(url);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookieJar.saveFromResponse(url, cookies);
    }
}
