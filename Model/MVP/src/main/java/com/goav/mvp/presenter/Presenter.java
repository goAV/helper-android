/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.mvp.presenter;

import com.goav.mvp.view.View;

public class Presenter<V extends View> implements BasicPresenter<V> {
    protected V View;

    @Override
    public void onAttachView(V v) {
        this.View = v;
    }

    @Override
    public void onUnAttachView() {
        this.View = null;
    }
}
