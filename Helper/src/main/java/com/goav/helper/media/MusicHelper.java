/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.live.helper.media;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresApi;

import com.goav.parser.Log;
import com.szdmcoffee.live.API;
import com.szdmcoffee.live.helper.sys.ContextHelper;
import com.szdmcoffee.live.helper.sys.ResouceHelper;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 播放音频文件
 */
public final class MusicHelper {
    private MediaPlayer mMediaPlayer;
    private Ringtone mRingtone;
    private boolean isOpen;

    /**
     * @param context context
     * @param raw     raw
     */
    public MusicHelper(Context context, @RawRes int raw) {
        isOpen = true;
        try {
            mMediaPlayer = createMediaPlay(context, raw);
        } catch (Exception e) {

        }
        if (mMediaPlayer == null) {
            try {
                mRingtone = createRingone(context, raw);
            } catch (Exception e) {

            }
        }
    }

    private Ringtone createRingone(Context context, int raw) {
        Uri uri = ResouceHelper.rawUri(raw);
        Log.d(uri);
        return RingtoneManager.getRingtone(ContextHelper.basicContext(context), uri);
    }

    private MediaPlayer createMediaPlay(Context context, int raw) {
        return MediaPlayer.create(ContextHelper.basicContext(context), raw);
    }

    public boolean isReady() {
        return mMediaPlayer != null || mRingtone != null;
    }

    public void start() {
        if (!this.isOpen) return;
        Log.d("本次播放结果为:%1$s", String.valueOf(this.isOpen));

        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.start();
            } else if (mRingtone != null) {
                mRingtone.play();
            }
        } catch (Exception e) {
            com.goav.parser.Log.e(e, "SOUND-start");
        }

    }

    public void modifVolState(boolean isOpen) {
        this.isOpen = isOpen;
    }


    public void release() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            } else if (mRingtone != null) {
                mRingtone.stop();
            }
        } catch (Exception e) {
            com.goav.parser.Log.e(e, "SOUND-stop");
        } finally {
            mMediaPlayer = null;
            mRingtone = null;
        }
    }


    /**
     * This is a util to u how to use {@link SoundPool},
     * u can use {@link android.media.SoundPool.Builder} directly
     *
     * @param context context
     * @param raw     raw's Id
     * @return {@link SoundPool}
     */
    public static Pool getPool(Context context, @RawRes int[] raw) {
        return new Pool(context, raw);
    }


    private static class Pool {

        private Map<Integer, Integer> musics;
        private SoundPool mSoundPool;

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("NewApi")
        Pool(Context context, @RawRes int[] raw) {
            if (API.v(Build.VERSION_CODES.LOLLIPOP)) {
                mSoundPool = new SoundPool.Builder()
                        .setMaxStreams(raw.length)
                        .build();
            } else {
                mSoundPool = new SoundPool(raw.length, AudioManager.STREAM_MUSIC, 2);
            }

            prepareMusic(context, raw);
        }

        private void prepareMusic(Context context, int[] raw) {
            if (mSoundPool == null) {
                return;
            }
            musics = new WeakHashMap<>();
            for (int i : raw) {
                musics.put(i, mSoundPool.load(context, i, 1));
            }
        }

        public void play(@RawRes int raw) {
            if (musics.containsKey(raw)) {
                mSoundPool.play(musics.get(raw), 1.f, 1.f, 1, 0, 1.0f);
            }
        }

        public void pause(@RawRes int raw) {
            if (musics.containsKey(raw)) {
                mSoundPool.pause(musics.get(raw));
            }
        }

        public void resume(@RawRes int raw) {
            if (musics.containsKey(raw)) {
                mSoundPool.resume(musics.get(raw));
            }
        }

        public void pause() {
            mSoundPool.autoPause();
        }

        public void resume() {
            mSoundPool.autoResume();
        }

        public void stop(@RawRes int raw) {
            mSoundPool.stop(musics.get(raw));
        }

        public void release() {
            mSoundPool.release();
        }


    }

}
