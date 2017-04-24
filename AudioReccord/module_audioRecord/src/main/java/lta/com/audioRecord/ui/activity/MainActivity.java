package lta.com.audioRecord.ui.activity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

import lta.com.audioRecord.R;
import lta.com.audioRecord.utils.AudioRecordUtil;

public class MainActivity extends BaseActivity {
    private Button mStartBtn,mStopBtn;
    private AudioRecordUtil mAduioRecordUtil; //工具类
    private File mOutFile; //录音存放文件夹
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStopBtn = (Button) findViewById(R.id.btn_stop);
    }

    @Override
    protected void initData() {
        mOutFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AudioRecord");
        mAduioRecordUtil = AudioRecordUtil.getInstance(mOutFile);
    }

    @Override
    protected void initEvent() {
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = System.currentTimeMillis()+"record.amr";
//                File recAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AudioRecord", fileName);
                mAduioRecordUtil.startRecord(fileName);
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAduioRecordUtil.stopRecord();
            }
        });
    }
}
