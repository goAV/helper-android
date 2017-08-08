/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.okhttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;

import static android.R.attr.key;
import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * A helper class to handle preference file operations.
 * <p>To reduce Context parameter of each method call, this class assumes all sp operation
 * happens in only one file. By initialize of application context, you needn't to worry about
 * context leaks.</p>
 *
 * @hide
 */
@SuppressWarnings("unused")
/* package */ public class GoPrefs {
    private static Context appContext;
    private static String fileName;

    /**
     * Initialize this class for subsequent calls.
     *
     * @param context A valid context object.
     * @param file    The SP file name to read and write.
     */
    public static void init(@NonNull Context context, @NonNull String file) {
        appContext = context.getApplicationContext();
        fileName = file;
    }

    /**
     * Retrieve sp value, assume the second parameter is the right class.
     *
     * @param <T> Boolean, Integer, Long, Float, Double, String allowed. For other types, an
     *            UnsupportedOperationException is thrown.
     */
    @SuppressWarnings("unchecked")
    public static String get(@NonNull String key) {
        SharedPreferences sp = getSharedPreferences();
        String result = sp.getString(key, "");
        return result;
    }


    /**
     * Put value into sp.
     *
     * @param <T> Boolean, Integer, Long, Float, Double, String allowed. For other types, an
     *            UnsupportedOperationException is thrown.
     */
    public static void set(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    /**
     * clear all data!
     *
     * @see SharedPreferences.Editor#clear()
     */
    public static void clear() {
        getSharedPreferences().edit().clear().apply();
    }

    private static SharedPreferences getSharedPreferences() {
        checkInitiatedOrThrow();
        return appContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * Retrieve all values from the preferences.
     *
     * @see SharedPreferences#getAll()
     */
    public static Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    private static void checkInitiatedOrThrow() {
        if (appContext == null || TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException("The GoPrefs class is not initialized correctly.");
        }
    }
}