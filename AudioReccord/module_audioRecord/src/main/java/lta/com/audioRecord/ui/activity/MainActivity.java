package lta.com.audioRecord.ui.activity;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import lta.com.audioRecord.R;
import lta.com.audioRecord.data.db.dao.RecordDao;
import lta.com.audioRecord.data.db.model.RecordModel;
import lta.com.audioRecord.ui.widget.RecordItemView;
import lta.com.audioRecord.utils.AudioRecordUtil;

/**
 * @author: LuTaiAn
 * @className: MainActivity
 * @description: 程序入口
 * @date: 2017/4/25
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Context mContent; //上下文
    private AudioRecordUtil mAudioRecordUtil; //工具类
    private File mOutFile; //录音存放文件夹
    private int startBgRes = R.drawable.start_record_audio_selector; //开始录音背景
    private int stopBgRes = R.drawable.start_record_audio_big_selector; //停止录音背景
    private TextView mTvPlay; //录音开启关闭按钮
    private boolean mIsRecording = false; //是否正在录音
    private long mRecordLength = 0; //录音时长
    private String mRecordName = ""; //录音文件名
    private LinearLayout mRecordLayout; //录音列表布局

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvPlay = (TextView) findViewById(R.id.tv_play);
        mTvPlay.setOnClickListener(this);
        mTvPlay.setBackgroundResource(startBgRes);
        mRecordLayout = (LinearLayout) findViewById(R.id.ll_record);
    }

    @Override
    protected void initData() {
        mContent = this;
        mOutFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecord");
        mAudioRecordUtil = AudioRecordUtil.getInstance(mOutFile);
        List<RecordModel> records = RecordDao.getInstance().queryAll();
        for (RecordModel record : records) {
            addRecordView(record);
        }
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
                mRecordLength = System.currentTimeMillis() - mRecordLength;
                saveDataToDB(mRecordName, mRecordLength);
                return;
            }
            long currentMillis = System.currentTimeMillis();
            mRecordLength = currentMillis;
            mRecordName = currentMillis + "record.amr";
            mAudioRecordUtil.startRecord(mRecordName);
            mTvPlay.setBackgroundResource(stopBgRes);
            mIsRecording = true;
        }
    }

    /**
     * 保存数据到数据库
     * @param: recordName
     * @param: recordLength
     * @return:
     */
    public void saveDataToDB(String recordName, long recordLength) {
        RecordModel model = new RecordModel();
        model.setRecordName(recordName);
        model.setId(System.currentTimeMillis() + "");
        model.setRecordLength(recordLength);
        model.setCreateTime(System.currentTimeMillis());
        addRecordView(model);
        RecordDao.getInstance().createOrUpdate(model);
    }

    /**
     * 添加录音布局
     * @param: recordModel
     * @return:
     */
    private void addRecordView(RecordModel recordModel) {
        RecordItemView recordView = new RecordItemView(mContent, recordModel);
        mRecordLayout.addView(recordView, 0);
    }
}
