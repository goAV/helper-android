/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.szdmcoffee.live.API;
import com.szdmcoffee.live.constant.FileConstant;

import java.io.File;

public final class FileHelper {

    public static boolean isEnable() {
        return Environment.isExternalStorageEmulated()
                &&
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * @param path     {@link FileConstant.PATH}
     * @param filename
     * @return app外部根目录
     */
    public static File createExternalFile(@FileConstant.PATH String path, String filename) {
        File file = getExternalStorageDirectory(path);
        return createFile(file, filename);
    }

    public static File createFile(File file, String filename) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, filename);
    }


    public static String Uri2File(Context context, Uri uri) {
        if (uri == null) {
            throw new NullPointerException("the uri can't be null");
        }
        String path = null;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (cursor.getColumnCount() > 0) {
                cursor.moveToFirst();
                int _id = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                if (_id > 0) {
                    path = cursor.getString(_id);
                }
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return path;

    }


    /**
     * @param path {@link FileConstant}
     * @return {@link File}
     */
    public static File getExternalStorageDirectory(@FileConstant.PATH String path) {
        return new File(Environment.getExternalStorageDirectory(), path);
    }

    /**
     * @param path {@link FileConstant}
     * @return {@link File}
     */
    public static File getCacheDir(Context context, @FileConstant.PATH String path) {
        return new File(context.getCacheDir(), path);
    }

    /**
     * @param path {@link FileConstant}
     * @return {@link File}
     */
    public static File getExternalFilesDir(Context context, @FileConstant.PATH String path) {
        return new File(context.getExternalFilesDir(null), path);
    }

    /**
     * @param path {@link FileConstant}
     * @return {@link File}
     */
    public static File getExternalCacheDir(Context context, @FileConstant.PATH String path) {
        return new File(context.getExternalCacheDir(), path);
    }

    /**
     * @param path {@link FileConstant}
     * @return {@link File}
     */
    public static File getFileDir(Context context, @FileConstant.PATH String path) {
        return new File(context.getFilesDir(), path);
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (API.v(Build.VERSION_CODES.KITKAT) && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
