/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.sys;

import java.util.Random;

public class RandomHelper {

    private static final Random random = new Random();

    public final static String RANDOM_STR = "abcdef0123456789";


    public static String getRandomRange(int length) {
        StringBuffer buffer = new StringBuffer();
        int len = RANDOM_STR.length();
        for (int i = 0; i < length; i++) {
            buffer.append(RANDOM_STR.charAt(random.nextInt(len)));
        }
        return buffer.toString();
    }

}
