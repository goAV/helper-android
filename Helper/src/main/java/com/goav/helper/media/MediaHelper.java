/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.entertainment.galaxy.R;
import com.szdmcoffee.live.API;
import com.szdmcoffee.live.BeautyLiveApplication;
import com.szdmcoffee.live.constant.FileConstant;
import com.szdmcoffee.live.helper.sys.FileHelper;
import com.szdmcoffee.live.helper.sys.RandomHelper;
import com.szdmcoffee.live.log.Report;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public final class MediaHelper {

    private static final int RESULT_REQUST_CODE_CAMERA = 120;
    private static final int RESULT_REQUEST_CODE_GALLERY = 114;
    private static String MEDIA_FILE_NAME = FileConstant.FILE_CACHE_PHOTO_NAME;

    public static void openPhotoFinder(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.photo_select)),
                RESULT_REQUEST_CODE_GALLERY);
    }


    public static boolean openPhotoCamera(Activity activity) {
        try {
            File f = FileHelper.getExternalStorageDirectory(FileConstant.PATH.FILE_PHOTO);
            if (!f.exists()) {
                f.mkdirs();
            }
            MEDIA_FILE_NAME = RandomHelper.getRandomRange(5) + "_" + FileConstant.FILE_CACHE_PHOTO_NAME;
            File file = new File(f, MEDIA_FILE_NAME);
                  /*  if(!file.exists()){
                        file.createNewFile();}
*/
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri u = API.create(activity, file);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent = API.createRWX(intent);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            activity.startActivityForResult(intent, RESULT_REQUST_CODE_CAMERA);
            return true;
        } catch (Exception e) {
            Report.Post("openPhotoCamera", e);
            return false;
//                    Toast.makeText(ImpromptuActivity.this, "没有找到储存目录",Toast.LENGTH_LONG).show();
        }
    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data, OnCallBack callBack) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == RESULT_REQUEST_CODE_GALLERY) {
            final Uri selectedUri = data.getData();
            callBack.onCallBack(selectedUri == null ? null : data.getData());
        } else if (requestCode == UCrop.REQUEST_CROP) {
            callBack.onCropBack(UCrop.REQUEST_CROP, data);
        } else if (requestCode == RESULT_REQUST_CODE_CAMERA) {
            Uri u = null;
            if (data != null) {
                u = data.getData();
            }
            if (u == null) {
                File f = FileHelper.getExternalStorageDirectory(FileConstant.PATH.FILE_PHOTO);
                if (!f.exists()) {
                    f.mkdirs();
                }
                File file = new File(f, MEDIA_FILE_NAME);
                u = API.create(file);
                if (u == null) {
                    if (file.exists()) {
                        try {
                            u = Uri.parse(MediaStore.Images.Media.insertImage(BeautyLiveApplication.getContextInstance().getContentResolver(),
                                    file.getAbsolutePath(), null, null));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            callBack.onCallBack(u);
        }

        if (resultCode == UCrop.RESULT_ERROR)

        {
            callBack.onCropBack(UCrop.REQUEST_CROP, data);
        }

    }

    public static String onHandleCropResult(Context context, Intent intent) {
        try {
            final Uri uri = UCrop.getOutput(intent);
            String path = FileHelper.getPath(context, uri);
            return path;
        } catch (Exception e) {
            return null;
        }
    }


    public interface OnCallBack {
        void onCallBack(Uri uri);

        void onCropBack(int requestCode, Intent data);
    }

}
