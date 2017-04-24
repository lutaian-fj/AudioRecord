package lta.com.audioRecord.utils;

import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;

/**
 * @author: LuTaiAn
 * @className:
 * @description:
 * @date: 2017/4/24
 */
public class AudioRecordUtil {
    private static AudioRecordUtil mInstance;
    public MediaRecorder mMediaRecorder;
    private File outFile; //录音存放文件夹
    /**
     * 初始化方法
     * @param:
     * @return: mInstance
     */
    public static synchronized AudioRecordUtil getInstance(File outFile) {
        if (mInstance == null) {
            mInstance = new AudioRecordUtil(outFile);
        }
        return mInstance;
    }

    /**
     * 构造方法
     * @param: path
     * @return:
     */
    public AudioRecordUtil(File outFile) {
        if(!outFile.exists()) {
            outFile.mkdir();
        }
        this.outFile = outFile;
    }

    /**
     * 开始录音
     * @param: recName 录音文件名字
     * @return:
     */
    public void startRecord(String recName) {
        File file = new File(outFile,recName);
        if(file.exists()) {
            file.delete();
        }
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mMediaRecorder.setOutputFile(file.getAbsolutePath());
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaRecorder.start();
    }

    /**
     * 停止录音
     * @param:
     * @return:
     */
    public void stopRecord() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
    }
}
