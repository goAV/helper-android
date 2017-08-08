/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cache;

import android.text.TextUtils;

import com.goav.parser.ParserConst;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GoResponseInterceptCache implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return getCacheControl(response);
    }

    private Response getCacheControl(Response response) {
        //from response
        CacheControl cacheControl = CacheControl.parse(response.headers());
        return getNetWorkWithCache(response, cacheControl);
    }

    private Response getNetWorkWithCache(Response response, CacheControl cacheControl) {
        CacheControl control = cacheControl;
        String cache = control.toString();

        if (false) {
            //if network's state is connectting
            if (TextUtils.isEmpty(cache)) {
                //no 'Cache-Control'
                response = response.newBuilder()
                        .header(
                                ParserConst.CACHE_CONTROL,
                                new CacheControl.Builder()
                                        .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)// alive
                                        .build()
                                        .toString()
                        )
                        .build();
            } else {
                if (control.isPublic()) {

                }
            }
        } else {
            //only read cache
            if (TextUtils.isEmpty(cache)) {
                //no 'Cache-Control'
                response = response.newBuilder()

                        .header(ParserConst.CACHE_CONTROL, CacheControl.FORCE_CACHE.toString())
                        .build();
            } else {
                if (control.isPublic()) {

                }
            }
        }


        return response;
    }
}
