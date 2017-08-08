/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cookie;

import com.goav.parser.ParserConst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截，取出缓存cookie发送
 */
public class GoRequestInterceptCookie extends GoInterceptCookie {


    public GoRequestInterceptCookie(CookieJar cookieJar) {
        super(cookieJar);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        List<String> cookiestr = request.headers(ParserConst.KEY_WORD_S);
        StringBuffer buffer = new StringBuffer();
        if (!cookiestr.isEmpty()) {
            for (String s : cookiestr) {
                buffer.append(s);
                buffer.append(";");
            }
        }
        List<Cookie> cookie = new ArrayList<>();
        cookie = Collections.unmodifiableList(loadForRequest(request.url()));
        if (!cookie.isEmpty()) {
            for (Cookie c : cookie) {
                String cc = c.toString();
                buffer.append(cc);
                buffer.append(";");
            }
            Request.Builder build = request.newBuilder();
            build.header(ParserConst.KEY_WORD_S, buffer.toString());
            return chain.proceed(build.build());
        }
        return chain.proceed(request);
    }
}
