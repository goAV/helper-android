/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BaseRepeatedPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.szdmcoffee.live.util.PixelUtil;

/**
 * Created by ymlong on Nov 24, 2015.
 */
public final class FrescoHelper {

    public static final int PAGE_PADDING_IN_DP = 16;

    private FrescoHelper() {

    }

    public static void frescoResize(Uri uri, int width, int height, SimpleDraweeView sdv) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        frescoController(sdv, request);
    }

    public static void frescoController(SimpleDraweeView draweeView, ImageRequest request) {
        draweeView.setController(Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .build());
    }

    public static DraweeController createResizeController(final Context context, final int
            parentWidth, final SimpleDraweeView imageView, String url) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                Log.d("FrescoActivity",
                        "Final image received! " + imageInfo.getWidth() + imageInfo.getHeight());
                setSize(context, parentWidth, imageView, imageInfo.getWidth(),
                        imageInfo.getHeight());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                FLog.d(getClass(), "Intermediate image received");

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }
        };
        return Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(url))
                .build();
    }

    private static void setSize(Context context, int parentWidth, SimpleDraweeView imageView, int
            width, int height) {
        //窗口的宽度
        int size = parentWidth - PixelUtil.dp2px(context, PAGE_PADDING_IN_DP);
        height = (int) (height * (((double) size) / width));
        width = size;
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width,
                height);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(param);
    }

    public static void clearCache(Uri uri) {
        if (uri == null) {
            return;
        }
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
    }

    public static Uri resStr(int id) {
        return Uri.parse("res:///" + id);
    }


    public static class BlurProcesser extends BaseRepeatedPostProcessor {

        private Paint paint;

        public BlurProcesser() {
            this.paint = new Paint();
        }


        @Override
        public void process(Bitmap dest, Bitmap source) {

            int width = source.getWidth();
            int height = source.getHeight();
            int scaledWidth = dest.getWidth();
            int scaledHeight = dest.getHeight();

            Bitmap blurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(blurredBitmap);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(source, 0, 0, paint);

            Bitmap blurBitmap = BlurHelper.bToBlur(blurredBitmap);

            blurredBitmap.recycle();

            super.process(dest, blurBitmap);
        }

        @Override
        public void process(Bitmap bitmap) {
            super.process(bitmap);
        }
    }
}
