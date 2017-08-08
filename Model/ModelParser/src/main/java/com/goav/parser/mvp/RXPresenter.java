/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.parser.mvp;

import com.goav.mvp.presenter.Presenter;
import com.goav.mvp.view.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RXPresenter<V extends View> extends Presenter<V> {
    private CompositeSubscription subscription;

    public void addSubscription(Subscription subscription) {
        if (this.subscription == null) {
            this.subscription = new CompositeSubscription();
        }
        this.subscription.add(subscription);
    }


    private void unSubscription() {
        if (this.subscription != null) {
            this.subscription.unsubscribe();
        }
    }

    @Override
    public void onUnAttachView() {
        unSubscription();
        super.onUnAttachView();
    }
}
