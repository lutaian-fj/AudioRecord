package lta.com.audioRecord.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author: LuTaiAn
 * @className: AudioUtil
 * @description: 录音工具类
 * @date: 2017/4/25
 */
public class AudioUtil {

    /**
     * 格式化时间为 mm'ss'
     * @param:  time
     * @return:
     */
    public static String formatToAudioShowTime(long time) {
        StringBuffer stringBuffer = new StringBuffer();
        //long hour = time / (3600*1000);
        long minute = time / (60 * 1000);
        long second = (time - minute * 60 * 1000) / 1000;
        stringBuffer.append(String.format("%d", minute));
        stringBuffer.append("'");
        stringBuffer.append(String.format("%02d", second));
        stringBuffer.append("\"");
        // String formatTime = formatter.format(date);
        return stringBuffer.toString();
    }

    /**播放动画
     * @param mContext
     * @return
     */
    public static AnimationDrawable getAnimationDrawable(Context mContext) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        // animationDrawable = (AnimationDrawable) mVAudioPlay.getBackground();
        // Drawable drawable1 = mContext.getDrawable(R.drawable.audio_play1);
        for (int i = 1; i <= 3; i++) {
            // 根据资源名称和目录获取R.java中对应的资源ID
            int id = mContext.getResources().getIdentifier("audio_play" + i, "mipmap",
                    mContext.getPackageName());
            // 根据资源ID获取到Drawable对象
            Drawable drawable = mContext.getResources().getDrawable(id);
            // 将此帧添加到AnimationDrawable中
            animationDrawable.addFrame(drawable, 300);
        }
        animationDrawable.setOneShot(false);
        return animationDrawable;
    }

}
