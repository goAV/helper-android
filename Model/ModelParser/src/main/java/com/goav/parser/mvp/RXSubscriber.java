/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.mvp;

import com.goav.mvp.RXException;
import com.goav.mvp.view.BasicView;

import rx.Subscriber;

public abstract class RXSubscriber<T> extends Subscriber<T> {

    protected BasicView mView;

    public RXSubscriber(BasicView v) {
        this.mView = v;
    }

    @Override
    public void onStart() {
        mView.onPrepare();
    }

    @Override
    public void onCompleted() {
        mView.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mView.onCompleted();
        mView.onError(new RXException("some problem about NetWork Error.", e));
    }
}
