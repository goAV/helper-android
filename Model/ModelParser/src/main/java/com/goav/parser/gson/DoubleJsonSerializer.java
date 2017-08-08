/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DoubleJsonSerializer implements JsonSerializer<Double> {

    @Override
    public JsonElement serialize(Double src, Type type, JsonSerializationContext jsonSerializationContext) {
        long longValue = src.longValue();
        if (src == longValue) {
            return new JsonPrimitive(longValue);
        }
        return new JsonPrimitive(src);
    }
}
