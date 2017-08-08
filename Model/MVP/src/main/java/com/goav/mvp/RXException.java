/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.mvp;

public class RXException extends RuntimeException {

    public RXException(String message, Throwable e) {
        super(message, e);
    }

    public RXException() {
        super();
    }

    public RXException(String message) {
        super(message);
    }

}
