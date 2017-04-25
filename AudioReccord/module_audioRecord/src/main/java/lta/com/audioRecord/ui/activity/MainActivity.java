package lta.com.audioRecord.ui.activity;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import lta.com.audioRecord.R;
import lta.com.audioRecord.utils.AudioRecordUtil;

/**
 * @author: LuTaiAn
 * @className: MainActivity
 * @description: 程序入口
 * @date: 2017/4/25
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private AudioRecordUtil mAudioRecordUtil; //工具类
    private File mOutFile; //录音存放文件夹
    private int startBgRes = R.drawable.start_record_audio_selector; //开始录音背景
    private int stopBgRes = R.drawable.start_record_audio_big_selector; //停止录音背景
    private TextView mTvPlay; //录音开启关闭按钮
    private boolean mIsRecording = false; //是否正在录音

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvPlay = (TextView) findViewById(R.id.tv_play);
        mTvPlay.setOnClickListener(this);
        mTvPlay.setBackgroundResource(startBgRes);
    }

    @Override
    protected void initData() {
        mOutFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecord");
        mAudioRecordUtil = AudioRecordUtil.getInstance(mOutFile);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_play) {
            if (mIsRecording) { //结束录音
                mAudioRecordUtil.stopRecord();
                mIsRecording = false;
                mTvPlay.setBackgroundResource(startBgRes);
                return;
            }
            String fileName = System.currentTimeMillis() + "record.amr";
            mAudioRecordUtil.startRecord(fileName);
            mTvPlay.setBackgroundResource(stopBgRes);
            mIsRecording = true;
        }
    }
}
