/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cookie;

import com.goav.parser.ParserConst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截，存缓存
 */
public class GoResponseInterceptCookie extends GoInterceptCookie {

    public GoResponseInterceptCookie(CookieJar cookieJar) {
        super(cookieJar);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        List<String> cookies = response.headers(ParserConst.KEY_WORD);
        if (!cookies.isEmpty()) {
            List<Cookie> cookie = new ArrayList<>();
            cookie.addAll(Cookie.parseAll(request.url(), response.headers()));
            saveFromResponse(request.url(), cookie);
        }

        return response;
    }
}
