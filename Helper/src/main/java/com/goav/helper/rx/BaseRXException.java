/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.rx;

import com.goav.mvp.RXException;

public final class BaseRXException extends RXException {

    private int type;

    public BaseRXException(String message, Throwable e) {
        super(message, e);
    }


    public BaseRXException(int type) {
        super();
        this.type = type;
    }

    public BaseRXException(int type, String message) {
        super(message);
        this.type = type;
    }


    public int getType() {
        return type;
    }
}
