/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> cls = typeToken.getRawType();
        if (cls == String.class) {
            return (TypeAdapter<T>) new StringAdapter();
        }
        return null;
    }


    public static class StringAdapter extends TypeAdapter<String> {

        @Override
        public void write(JsonWriter jsonWriter, String s) throws IOException {
            if (s != null) {
                jsonWriter.value(s);
            } else {
                jsonWriter.nullValue();
            }
        }

        @Override
        public String read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return "";
            }
            return jsonReader.nextString();
        }
    }
}
