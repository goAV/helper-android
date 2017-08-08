/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.text.TextUtils;

import com.entertainment.galaxy.view.a.Live;
import com.goav.parser.gson.GoGson;
import com.szdmcoffee.live.constant.HttpConstant;

import java.util.HashMap;
import java.util.Map;

import static com.szdmcoffee.live.constant.Const.MAIN_HOST_FOR_PING;

public final class HttpHelper {

    public static String schema(String path) {
        return schema(path, HttpConstant.MAIN_HOST_SCHEME_HTTP + MAIN_HOST_FOR_PING);
    }

    public static String schema(String path, String defaultSchema) {
        if (!TextHelper.isEnEmpty(path)) {
            if (path.startsWith("https://") || path.startsWith("http://")) {
                return path;
            } else {
                return defaultSchema + path;
            }
        }
        return "";
    }


    public static class Builder {
        private Map<String, Object> map;

        public Builder() {
            map = new HashMap<>();
            add("token", TextHelper.isUnEmpty(Live.getToken()));
        }

        public Builder add(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public Builder remove(String key) {
            map.remove(key);
            return this;
        }

        public Map<String, Object> buildMap() {
            return map;
        }

        public String buildJson() {
            return GoGson.toJson(map);
        }

    }

}
