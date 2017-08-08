/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.retrofit;

import com.goav.parser.gson.GoGson;
import com.goav.parser.gson.StringAdapterFactory;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 业务分离
 */
public abstract class GoRetrofitIMPL {

    /**
     * @return host address default null
     */
    public abstract String callHostAddress();

    /**
     * @return parser default {@link GsonConverterFactory}
     */
    public Converter.Factory callConverterFactory() {
        return GsonConverterFactory.create(new GsonBuilder()
                .registerTypeAdapterFactory(new StringAdapterFactory())
                .create());
    }

    /**
     * @return response default {@link RxJavaCallAdapterFactory}
     */
    public CallAdapter.Factory callAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    /**
     * @return http's  default {@link OkHttpClient}
     * @see {@link OkHttpClient}
     */
    public okhttp3.Call.Factory callClient() {
        return new OkHttpClient();//// FIXME: 17/3/21 
    }


}
