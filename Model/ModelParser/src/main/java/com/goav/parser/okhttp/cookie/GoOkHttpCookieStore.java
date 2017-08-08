/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cookie;

import android.net.Uri;
import android.text.TextUtils;

import com.goav.parser.ParserConst;
import com.goav.parser.okhttp.GoPrefs;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

public final class GoOkHttpCookieStore implements CookieStore {

    GoOkHttpCookieStore(){

    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        GoPrefs.set(uri.toString(), cookie.toString());
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        List<HttpCookie> cookies = new ArrayList<>();
        String cookie = GoPrefs.get(uri.toString());
        if (!TextUtils.isEmpty(cookie)) {
            cookies.addAll(HttpCookie.parse(cookie));
        }
        return cookies;
    }

    @Override
    public List<HttpCookie> getCookies() {
        Map<String, ?> all = GoPrefs.getAll();
        List<HttpCookie> cookies = new ArrayList<>();
        if (!all.isEmpty()) {
            for (Map.Entry<String, ?> stringEntry : all.entrySet()) {
//                String key=stringEntry.getKey();
                String value = stringEntry.getValue().toString();
//                HttpCookie.parse()
                cookies.addAll(HttpCookie.parse(value));
            }
        }
        return cookies;
    }

    @Override
    public List<URI> getURIs() {
        Map<String, ?> all = GoPrefs.getAll();
        List<URI> cookies = new ArrayList<>();
        if (!all.isEmpty()) {
            for (Map.Entry<String, ?> stringEntry : all.entrySet()) {
                String key = stringEntry.getKey();
                cookies.add(URI.create(key));
            }
        }
        return cookies;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        GoPrefs.remove(uri.toString());
        return true;
    }

    @Override
    public boolean removeAll() {
        GoPrefs.clear();
        return true;
    }
}
