/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

public final class Log {


    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (ParserConst.DEBUG)
            Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.d(message, args);
    }

    public static void d(Object object) {
        if (ParserConst.DEBUG)
            Logger.d(object);
    }

    public static void e(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (ParserConst.DEBUG)
            Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (ParserConst.DEBUG)
            Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (ParserConst.DEBUG)
            Logger.xml(xml);
    }
}
