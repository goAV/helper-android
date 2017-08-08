/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cookie;

import com.goav.parser.ParserConst;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import static com.goav.parser.ParserConst.KEY_WORD;
import static com.goav.parser.ParserConst.KEY_WORD_S;

/**
 * {@link CookieJar#NO_COOKIES} is default empty cookie
 */
public class GoOkHttpCookie implements CookieJar {

    private CookieHandler cookie;
    private Map<String, List<Cookie>> map;

    private GoOkHttpCookie() {
        this(new CookieManager(new GoOkHttpCookieStore(), CookiePolicy.ACCEPT_ALL));
    }

    public GoOkHttpCookie(CookieHandler cookie) {
        this.cookie = cookie;
        this.map = new HashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies == null || cookies.isEmpty()) return;


        List<String> cookiejar = new ArrayList<>();
        for (Cookie cookie : cookies) {
            cookiejar.add(cookie.toString());
        }

        Map map = Collections.singletonMap(KEY_WORD, cookiejar);

        try {
            cookie.put(url.uri(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        Map<String, List<String>> cacheCookie = Collections.emptyMap();
        Map<String, List<String>> mCookie = new HashMap<>();
        try {
            mCookie = cookie.get(url.uri(), cacheCookie);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Cookie> cookie = new ArrayList<>();

        if (!mCookie.isEmpty()) {
            for (Map.Entry<String, List<String>> map : mCookie.entrySet()) {
                String key = map.getKey();
                if (key.equalsIgnoreCase(KEY_WORD) || key.equalsIgnoreCase(KEY_WORD_S)) {
                    cookie.addAll(Cookie.parseAll(url, parserVaulr(map.getValue())));
                }
            }
        }
        return cookie;
    }

    private Headers parserVaulr(List<String> s) {
        Headers.Builder builder = new Headers.Builder();
        StringBuffer stringBuffer = new StringBuffer();
        for (String s1 : s) {
            stringBuffer.append(s1);
            stringBuffer.append(";");
        }
        builder.add(KEY_WORD, stringBuffer.toString());
        return builder.build();
    }

    private static class GoRetrofitCacheHelper {
        private static GoOkHttpCookie source = new GoOkHttpCookie();
    }

    public static GoOkHttpCookie create() {
        return GoRetrofitCacheHelper.source;
    }


    public static OkHttpClient create(OkHttpClient client) {
        return create(client.newBuilder()).build();
    }

    public static OkHttpClient.Builder create(OkHttpClient.Builder builder) {
        return builder.cookieJar(GoRetrofitCacheHelper.source);
    }


}
