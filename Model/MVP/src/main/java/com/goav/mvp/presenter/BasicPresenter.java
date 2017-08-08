/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.mvp.presenter;

import com.goav.mvp.view.BasicView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public interface BasicPresenter<V extends BasicView> {

    void onAttachView(V t);

    void onUnAttachView();

}
