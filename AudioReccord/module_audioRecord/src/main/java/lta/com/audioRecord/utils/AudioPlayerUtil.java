package lta.com.audioRecord.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

import java.io.File;

/**
 * @author: LuTaiAn
 * @className: AudioPlayerUtil
 * @description: 录音播放工具类
 * @date: 2017/4/27
 */
public class AudioPlayerUtil {
	private static MediaPlayer mMediaPlayer;
	private Context mContext;
	
	public AudioPlayerUtil(Context context){
		mContext = context;
	}
	
	public void play(File file){
		if (file!=null && file.exists()) {
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
	
	public void stopPlayer(){
		if(mMediaPlayer.isPlaying()){
			mMediaPlayer.stop();
			mMediaPlayer.release();
		}
	}
}
