/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.szdmcoffee.live.presentation.ui.base.recycler.holder.ViewHolderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class RecyclerViewHelper {

    public static <VH extends RecyclerView.ViewHolder>
    void recyclerAnimator(RecyclerView recyclerView, RecyclerView.Adapter<VH> adapter) {
        AnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(new AlphaInAnimationAdapter(adapter));
        animationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        recyclerView.setAdapter(adapter);
    }
}
