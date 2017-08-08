/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp.cache;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GoRequestInterceptCache implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().cacheControl(getNetWorkWithCache()).build();
        return chain.proceed(request);
    }

    /**
     * @return cache state
     */
    private CacheControl getNetWorkWithCache() {

        if (false) {
            //if network's state is connectting
            return new CacheControl.Builder().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
        }
        //only read cache
        return CacheControl.FORCE_CACHE;
    }
}
