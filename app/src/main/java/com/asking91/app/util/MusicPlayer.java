package com.asking91.app.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.Voice;
import android.text.TextUtils;

import com.asking91.app.R;
import com.asking91.app.ui.widget.AskSimpleDraweeView;

import java.io.IOException;

/**
 * Created by wxwang on 2016/12/13.
 */
public class MusicPlayer implements MediaPlayer.OnCompletionListener {
    private static MusicPlayer player;
    private MediaPlayer mMediaPlayer;

    Context mContext;

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public static MusicPlayer getPlayer() {
        if(player==null)
            player = new MusicPlayer();
        return player;
    }

    public AskSimpleDraweeView voice;
    public AskSimpleDraweeView _preVoice;

    public MusicPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
    }

    public MusicPlayer bindVoice(Context mContext,AskSimpleDraweeView voice) {
        this.mContext = mContext;
        if(this._preVoice!=null && !voice.equals(this._preVoice)){
            this._preVoice.setBackgroundResource(R.mipmap.super_talking_voice);
        }
        _preVoice = voice;
        this.voice = voice;

        return getPlayer();
    }

    private long mExitTime;
    private String urlTmp;

    public void play(String url) {
        try {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//防止点击过快
                mExitTime = System.currentTimeMillis();

                //设置上一个播放图标
                if(this._preVoice!=null && !voice.equals(this._preVoice)){
                    this._preVoice.setBackgroundResource(R.mipmap.super_talking_voice);
                }

                if (mMediaPlayer != null && TextUtils.equals(url,urlTmp)) {
                    mMediaPlayer.start();
                } else  if (mMediaPlayer != null && !TextUtils.isEmpty(url)) {
                    urlTmp = url;
                    mMediaPlayer.reset();
                    mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            try{
                                mMediaPlayer.pause();
                                mMediaPlayer.reset();
                                if (voice != null) {
                                    voice.getController().getAnimatable().stop();
                                }
                                if (mContext != null) {
                                    CommonUtil.Toast(mContext,"暂无音频信息");
                                }
                            }catch (Exception e){}
                            return true;
                        }
                    });
                    mMediaPlayer.setDataSource(url);
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            if (mMediaPlayer != null){
                                mMediaPlayer.start();
                            }
                            if (voice != null) {
                                voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
                            }
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            if (isPlaying()) {
                mMediaPlayer.pause();
                if (voice != null) {
                    voice.getController().getAnimatable().stop();
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            if (voice != null) {
                voice.getController().getAnimatable().stop();
            }
        } catch (Exception e) {
        }
        ;
    }


    public void release() {
        mMediaPlayer.release();
//        mMediaPlayer = null;
    }

}
