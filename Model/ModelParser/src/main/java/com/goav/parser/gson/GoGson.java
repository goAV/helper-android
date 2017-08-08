/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.goav.parser.gson.GoGson.*;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class GoGson {

    private static Gson mGson;

    private GoGson() {
    }

    static {
        mGson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .registerTypeAdapter(String.class, new StringAdapterFactory.StringAdapter()) //fix value is ull
                .create();
        instance(mGson);
    }

    public static Gson create() {
        return mGson;
    }

    public static void instance(Gson gson) {
        mGson = gson;
    }

    public static void register(TypeAdapterFactory factory) {
        instance(new GsonBuilder().registerTypeAdapterFactory(factory)
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create());
    }

    public static Gson createNormal() {
        return new GsonBuilder().create();
    }

    public static String toJson(Object o) {
        return mGson.toJson(o);
    }

    public static <T> T toClass(String o, Class<T> t) {
        return toClass(o, (Type) t);
    }

    public static <T> T toClass(String o, Type t) {
        return (T) mGson.fromJson(o, t);
    }

    public static <T> T toClass(String o, GoTypeIMPL t) {
        return (T) mGson.fromJson(o, t.build());
    }


    public static class GoParameterizedType<T> implements ParameterizedType, GoTypeIMPL {

        private Class<T> raw;
        private Type[] types;

        public GoParameterizedType(final Class<T> raw, final Type... types) {
            this.raw = raw;
            this.types = types;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return types;
        }

        @Override
        public Type getRawType() {
            return null;
        }

        @Override
        public Type getOwnerType() {
            return raw;
        }

        public Type build() {
            return this;
        }
    }

    public static class GoTypeToken<T> implements GoTypeIMPL {

        public GoTypeToken() {
        }

        public Type build() {
            return new TypeToken<T>() {
            }.getType();
        }
    }


    public interface GoTypeIMPL {
        Type build();
    }


}
