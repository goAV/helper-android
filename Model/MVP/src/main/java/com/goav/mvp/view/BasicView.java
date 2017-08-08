/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.mvp.view;

import com.goav.mvp.RXException;

public interface BasicView {

    public void onPrepare();

    public void onCompleted();

    public void onError(RXException e);
}
