/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.rx;

public final class SMException extends Exception {

    public SMException(String message, Throwable e) {
        super(message, e);
    }

    public SMException() {
        super();
    }

    public SMException(String message) {
        super(message);
    }

    public static SMException create(String message, Throwable e) {
        return new SMException(message, e);
    }
}
