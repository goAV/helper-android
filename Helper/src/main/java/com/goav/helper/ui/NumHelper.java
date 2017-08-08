/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.ui;

import android.content.Context;
import android.util.SparseArray;

import com.szdmcoffee.live.helper.sys.ContextHelper;
import com.szdmcoffee.live.helper.sys.ResouceHelper;

public final class NumHelper {
    private static SparseArray<String> mUnit;

    public static final String K = "k";//1_000
    public static final String M = "m";//1_000_000
    public static final String B = "b";//1_000_000_000


    static {
//        mUnit = new SparseArray<>();
//
//        Context context = ContextHelper.get();
//
//        for (int i = 0; i < 10; i++) {//亿
//
//            String mValue = null;
//            try {
//                mValue = context.getString(ResouceHelper.resId(context, ResouceHelper.TYPE.STRING, "all_num_unit_" + i));
//            } catch (Exception e) {
//
//            }
//            mUnit.put(i, mValue);
//        }

    }

    // TODO: 17/5/13 11:34 在这里字符串验证是否为null


    /**
     * @param l long num
     * @return to string hold 4
     */
    public static String n2str(long l) {
        return n2str(l, 0);
    }

    /**
     * @param l long num
     * @return to string hold 4
     */
    public static String n2str(String l) {
        return n2str(l, 0);
    }


    /**
     * @param l       long num
     * @param decimal 保留小数个数
     * @return to string hold ten thousand
     */
    public static String n2str(long l, int decimal) {
        return n2str(l, M, decimal);
    }


    /**
     * @param l       long num
     * @param decimal 保留小数个数
     * @return to string hold ten thousand
     */
    public static String n2str(String l, int decimal) {
        return n2str(l, M, decimal);
    }


    /**
     * @param l       long num
     * @param num     几位数起步缩进
     * @param decimal 保留小数个数
     * @return to string hold `num`
     */
    public static String n2str(long l, String k, int decimal) {
//        String.format("%."+decimal+"f",l);
        int num = getK(k);
        String numText = l + "";
        if (num > 0) {
            double length = l / Math.pow(10, num);
            if (length >= 1)
                return String.format("%." + Math.max(0, Math.min(decimal, num)) + "f", length) + k;
        }
        return numText;
    }

    private static int getK(String k) {
        switch (k) {
            case K:
                return 3;
            case M:
                return 6;
            case B:
                return 9;
            default:
                return 0;
        }
    }

    /**
     * @param l       long num
     * @param num     几位数起步缩进
     * @param decimal 保留小数个数
     * @return to string hold `num`
     */
    public static String n2str(String l, String k, int decimal) {
        return n2str(Double.valueOf(l).longValue(), k, decimal);//igone 后面整
    }


}
