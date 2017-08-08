/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.retrofit;

import com.goav.parser.gson.GoGson;
import com.goav.parser.gson.StringAdapterFactory;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 初始化 {@link Retrofit} ,把网络终端和model处理分离
 */
public class GoRetrofitSource {

    private Retrofit retrofit;

    private GoRetrofitSource() {
//        GoGson.register(new StringAdapterFactory());
    }

    /**
     * @param impl 网络相关工厂
     * @return {@link Retrofit}
     */
    private Retrofit createRetrofit(GoRetrofitIMPL impl) {
        return new Retrofit.Builder()
                .baseUrl(HttpUrl.parse(impl.callHostAddress()))
                .addConverterFactory(impl.callConverterFactory())
                .addCallAdapterFactory(impl.callAdapterFactory())
                .callFactory(impl.callClient())
                .build();
    }

    /**
     * @return 网络接口类
     */
    private <T> T createIMML(GoRetrofitIMPL impl, Class<T> t) {
        return createRetrofit(impl).create(t);
    }

    /**
     * @return 网络接口类
     */
    public static <T> T create(GoRetrofitIMPL impl, Class<T> t) {
        return GoRetrofitSourceHelper.source.createIMML(impl, t);
    }


    /**
     * {@inheritDoc} sington
     */
    private static class GoRetrofitSourceHelper {
        private static GoRetrofitSource source = new GoRetrofitSource();
    }

}
