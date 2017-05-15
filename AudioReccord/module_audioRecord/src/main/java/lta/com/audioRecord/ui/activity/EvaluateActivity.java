package lta.com.audioRecord.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import java.io.File;
import java.util.List;

import lta.com.audioRecord.R;
import lta.com.audioRecord.data.db.dao.RecordDao;
import lta.com.audioRecord.data.db.model.RecordModel;
import lta.com.audioRecord.data.entity.Result;
import lta.com.audioRecord.ui.widget.RecordItemView;
import lta.com.audioRecord.utils.AudioRecordUtil;
import lta.com.audioRecord.utils.XmlResultParser;

/**
 * @author: LuTaiAn
 * @className: EvaluateActivity
 * @description: 评测的Activity
 * @date: 2017/5/9
 */
public class EvaluateActivity extends BaseActivity implements View.OnClickListener, RecordItemView.CallBack {
    private static String TAG = "EvaluateActivity";
    private Context mContext; //上下文
    private Button mStartBtn, mStopBtn, mCancelBtn, mParseBtn; //定义按钮
    private TextView mArticleTv; //文章
    private String language = "zh_cn"; // 评测语种
    private String category = "read_sentence"; // 评测题型
    private String result_level = "complete"; // 结果等级
    private String mLastResult; //结果
    private SpeechEvaluator mSpeechEvaluator; //评测类
    private LinearLayout mAudioLayout; //录音列表
    private AudioRecordUtil mAudioRecordUtil; //工具类
    private String mOutPath; //录音存放路径
    private File mOutFile; //录音存放文件夹
    private String mCurrentAudioName; //当前录音名
    private long mCurrentAudioLength = 0; //当前录音长度
    private boolean mIsRecording = false; //是否正在录音
    private List<RecordModel> mRecordModels; //录音实体

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initView() {
        mStartBtn = (Button) this.findViewById(R.id.btn_start);
        mStopBtn = (Button) this.findViewById(R.id.btn_stop);
        mCancelBtn = (Button) this.findViewById(R.id.btn_cancel);
        mParseBtn = (Button) this.findViewById(R.id.btn_parse);
        mAudioLayout = (LinearLayout) this.findViewById(R.id.ll_audio);
        mArticleTv = (TextView) this.findViewById(R.id.tv_article);
    }

    @Override
    protected void initData() {
        mContext = this;
        mSpeechEvaluator = SpeechEvaluator.createEvaluator(EvaluateActivity.this, null);
        mOutPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ziliaoyinsong/";
        mOutFile = new File(mOutPath);
        mAudioRecordUtil = AudioRecordUtil.getInstance(mOutFile);
        mRecordModels = RecordDao.getInstance().queryAll();
        for (RecordModel record : mRecordModels) {
            addRecordView(record);
        }
    }

    @Override
    protected void initEvent() {
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mParseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_start) { //开始
            mCurrentAudioLength = System.currentTimeMillis();
            mCurrentAudioName = System.currentTimeMillis() + "_record.wav";
            setParams();
            String article = mArticleTv.getText().toString();
            String[] articles = article.split("。");
            for (String str : articles) {
                Log.e(TAG, str);
            }
            mSpeechEvaluator.startEvaluating("今天天气真不错", null, mEvaluatorListener);
            mIsRecording = true;
            return;
        } else if (id == R.id.btn_stop) { //停止
            if (mSpeechEvaluator.isEvaluating()) {
                mSpeechEvaluator.stopEvaluating();
                mCurrentAudioLength = System.currentTimeMillis() - mCurrentAudioLength;
                mIsRecording = false;
            }
            return;
        } else if (id == R.id.btn_parse) { //解析
            if (mCurrentAudioLength == 0) {
                showTip("请先录音");
                return;
            }
            XmlResultParser resultParser = new XmlResultParser();
            Result result = resultParser.parse(mLastResult);
            showTip("本次得分为:" + result.total_score);
            saveDataToDB(mCurrentAudioName, mCurrentAudioLength);
            return;
        } else if (id == R.id.btn_cancel) { //取消
            mSpeechEvaluator.cancel();
        }
    }

    /**
     * 设置评测参数
     *
     * @param:
     * @return:
     */
    private void setParams() {
        SharedPreferences pref = getSharedPreferences("ise_settings", MODE_PRIVATE);
        String vad_bos = "5000"; // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_eos = "1800"; // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");
        mSpeechEvaluator.setParameter(SpeechConstant.LANGUAGE, language);
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mSpeechEvaluator.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mSpeechEvaluator.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mSpeechEvaluator.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mSpeechEvaluator.setParameter(SpeechConstant.RESULT_LEVEL, result_level);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeechEvaluator.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechEvaluator.setParameter(SpeechConstant.ISE_AUDIO_PATH, mOutPath + mCurrentAudioName);
    }

    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());

                if (!TextUtils.isEmpty(builder)) {
//                    mResultEditText.setText(builder.toString());
                }
//                mIseStartButton.setEnabled(true);
                mLastResult = builder.toString();
                showTip("录音结束");
            }
        }

        @Override
        public void onError(SpeechError error) {
//            mIseStartButton.setEnabled(true);
            if (error != null) {
                showTip("error:" + error.getErrorCode() + "," + error.getErrorDescription());
//                mResultEditText.setText("");
//                mResultEditText.setHint("请点击“开始评测”按钮");
            } else {
                Log.d(TAG, "evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.d(TAG, "evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.d(TAG, "evaluator stoped");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前音量：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

    /**
     * 添加录音布局
     *
     * @param: recordModel
     * @return:
     */
    private void addRecordView(RecordModel recordModel) {
        RecordItemView recordView = new RecordItemView(mContext, recordModel);
        recordView.setCallBack(this);
        mAudioLayout.addView(recordView, 0);
    }

    /**
     * 保存数据到数据库
     *
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
        mRecordModels.add(model);
        RecordDao.getInstance().createOrUpdate(model);
        mCurrentAudioLength = 0;
    }

    @Override
    public void delete(RecordModel model) { //删除的回调
        if (mRecordModels != null) {
            mRecordModels.remove(model);
        }
    }

    @Override
    public boolean isRecording() {
        return mIsRecording;
    }
}
