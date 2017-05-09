package lta.com.audioRecord.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

import java.io.File;

import lta.com.audioRecord.ui.listener.OnStopCallPack;

/**
 * @author: LuTaiAn
 * @className: AudioPlayerUtil
 * @description: 录音播放工具类
 * @date: 2017/4/27
 */
public class AudioPlayerUtil {
    private static MediaPlayer mMediaPlayer;
    private Context mContext;
    private static AudioPlayerUtil mInstance;

    /**
     * 获取对象
     * @param: context
     * @return:
     */
    public static AudioPlayerUtil getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new AudioPlayerUtil(context);
        }
        return mInstance;
    }

    public AudioPlayerUtil(Context context) {
        mContext = context;
    }

    /**
     * 开始播放
     *
     * @param:
     * @return:
     */
    public void play(File file) {
        if (file != null && file.exists()) {
            Uri uri = Uri.fromFile(file);
            mMediaPlayer = MediaPlayer.create(mContext, uri);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    //TODO:finish
                }
            });
        }
    }

    /**
     * 停止播放
     * @param: cb
     * @return:
     */
    public void stopPlayer(OnStopCallPack cb) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            cb.onStopCallBack();
        }
    }

    /**
     * 设置播放结束监听器
     *
     * @param: listener
     * @return:
     */
    public void setCompleteListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(listener);
    }

}
