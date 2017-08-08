/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.goav.parser.Log;
import com.goav.parser.gson.GoGson;
import com.google.gson.reflect.TypeToken;
import com.szdmcoffee.live.BeautyLiveApplication;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class TextHelper {

    public static void marqueeIgnoreEllipsis(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        try {
            Field field = configuration.getClass().getDeclaredField("mFadingMarqueeEnabled");
            field.setAccessible(true);
            boolean isEnable = (boolean) field.get(configuration);
            if (!isEnable) {
                field.set(configuration, true);
            }
        } catch (Exception e) {

        }
    }

    public static void startMarquee(TextView view) {
        if (view != null) {
            view.setSingleLine();
            view.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            view.setMarqueeRepeatLimit(-1);
            view.setTextIsSelectable(true);
            view.setSelected(true);
//            view.setFocusable(true);
//            view.setFocusableInTouchMode(true);
        }
    }


    public static Map<String, Object> Str2Map(String values) {
        values = values.replaceAll(":", ",");
        Log.d(values);
        Map<String, Object> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(values, ",");
        for (; tokenizer.hasMoreTokens(); ) {
            map.put(tokenizer.nextToken(), tokenizer.nextElement());
        }
        return map;
    }

    public static Map<String, Object> json2Map(String values) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(values);
            Iterator<String> keys = object.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, object.opt(key));
            }
        } catch (Exception e) {
            return gson2Map(values);
        }
        return map;
    }

    public static Map<String, Object> gson2Map(String values) {
        return GoGson.create().fromJson(values, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> boolean isEnEmpty(List<T> data) {
        return data == null || data.isEmpty();
    }

    /**
     * @param data
     * @param <T>
     * @return null 时返回不可改变列表
     */
    public static <T> List<T> isUnEmpty(List<T> data) {
        return isEnEmpty(data) ? Collections.<T>emptyList() : data;
    }


    public static boolean isEnEmpty(CharSequence str) {
        String s = null;
        if (!TextUtils.isEmpty(str)) {
            s = str.toString();
        }
        return isEnEmpty(s);
    }

    public static boolean isEnEmpty(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.trim();
        }
        return TextUtils.isEmpty(str);
    }


    public static String isNumUnit(int num, @StringRes int resUnit) {
        return String.format("%d" + BeautyLiveApplication.getContextInstance().getString(resUnit), num);
    }


    /**
     * {@inheritDoc}
     */
    public static void textAutoSize(final TextView view, final float defaultSize) {
        textAutoSize(view, defaultSize, false);
    }


    /**
     * @param view        TextView
     * @param defaultSize 默认字体大小 px
     * @param auto        换行
     */
    public static void textAutoSize(final TextView view, float defaultSize, boolean auto) {
        if (view == null) return;
        String str = view.getText().toString();
        int width = ViewHelper.getViewWidth(view);
        if (width <= 0) return;
        DisplayMetrics metrice = BeautyLiveApplication.getContextInstance().getResources().getDisplayMetrics();
        int strLength = str.length();
        float space = view.getLineSpacingExtra() * (strLength - 1);
        float textSize = view.getTextSize();
        float size = textSize/* * metrice.scaledDensity */ * strLength;
        size += space;
        if (size > width) {
            if (auto) {
                //换行
                int maxLength = Double.valueOf(Math.ceil(width / (view.getTextSize() + view.getLineSpacingExtra()))).intValue();
                if (maxLength > strLength) return;
                String newStr = str;
                StringBuffer sb = new StringBuffer();
                int moreSize = strLength / maxLength;
                int index = 0;
                for (int i = 0; i < moreSize; i++) {
                    sb.append(str.substring(index, (maxLength - 1) * (i + 1)));
                    sb.append("\n");
                    index += maxLength;
                }
                sb.append(newStr.substring(maxLength * moreSize, strLength));
                view.setText(sb);

            } else {
                final float maxAllSize = width - space;//new text size num
                float newSize = maxAllSize / strLength;//sp * metrics.scaledDensity
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.min(newSize/* * metrice.scaledDensity*/, defaultSize));
                Log.d("newSize = %1$f , defaultSize  = %2$f", newSize, defaultSize);
            }
        }
    }

    /**
     * @param str str
     * @return unable {@link NullPointerException}
     */
    public static String isUnEmpty(String str) {
        return isEnEmpty(str) ? "" : str;
    }

    public static String isUnEmpty(CharSequence str) {
        return isEnEmpty(str) ? "" : str.toString();
    }

    public static String isUnEmpty(String str, String def) {
        return isEnEmpty(str) ? def : str;
    }


    public static class TextRecycle {
        private static final Field TEXT_LINE_CACHED;

        static {
            Field textLineCached = null;
            try {
                textLineCached = Class.forName("android.text.TextLine").getDeclaredField("sCached");
                textLineCached.setAccessible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            TEXT_LINE_CACHED = textLineCached;
        }

        public static void clearTextLineCache() {
            // If the field was not found for whatever reason just return.
            if (TEXT_LINE_CACHED == null) return;

            Object cached = null;
            try {
                // Get reference to the TextLine sCached array.
                cached = TEXT_LINE_CACHED.get(null);
            } catch (Exception ex) {
                //
            }
            if (cached != null) {
                // Clear the array.
                for (int i = 0, size = Array.getLength(cached); i < size; i++) {
                    Array.set(cached, i, null);
                }
            }
        }

        private TextRecycle() {
        }
    }

}
