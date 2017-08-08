/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.bill;

import com.goav.bill.util.IabResult;

public class GooglePayException extends Exception {
    private final IabResult IabResult;
    private final String type;

    public GooglePayException(String type, IabResult message) {
        super(message.getMessage());
        this.type = type;
        this.IabResult = message;
    }


    public int getCode() {
        return IabResult.getCode();
    }

    public String getType() {
        return type;
    }
}
