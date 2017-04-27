package lta.com.audioRecord.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import lta.com.audioRecord.R;
import lta.com.audioRecord.data.db.model.RecordModel;
import lta.com.audioRecord.utils.AudioPlayerUtil;
import lta.com.audioRecord.utils.AudioUtil;
import lta.com.audioRecord.utils.TimeUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: LuTaiAn
 * @className: RecordItemView
 * @description: 录音的Item
 * @date: 2017/4/25
 */
public class RecordItemView extends LinearLayout implements View.OnClickListener{

    private Context mContext; //上下文
    private TextView mTvAudioTime; //录音失常
    private TextView mTvAudioCreateTime; //录音创建时间
    private TextView mTvAudioPlay; //录音播放按钮
    private ImageView mIvDelete; //录音删除按钮
    private AnimationDrawable animationDrawable; //播放的动画
    private RecordModel mRecordModel; //录音实体类
    private AudioPlayerUtil mPlayer;

    public RecordItemView(Context context,RecordModel recordModel) {
        super(context);
        mRecordModel = recordModel;
        init(context);
        bindData();
    }

    public RecordItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecordItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     * @param: context
     * @return:
     */
    private void init(Context context) {
        mContext = context;
        View.inflate(getContext(), R.layout.item_record, this);
        mTvAudioTime = (TextView) findViewById(R.id.tv_audio_time);
        mTvAudioCreateTime = (TextView) findViewById(R.id.tv_audio_create_time);
        mTvAudioPlay = (TextView) findViewById(R.id.tv_audio_play);
        mTvAudioPlay.setOnClickListener(this);
        mIvDelete = (ImageView) findViewById(R.id.iv_remove);
        animationDrawable = AudioUtil.getAnimationDrawable(getContext());
        mIvDelete.setOnClickListener(this);
    }

    /**
     * 绑定数据
     * @param: model
     * @return:
     */
    private void bindData() {
        mTvAudioCreateTime.setText(TimeUtil.convertTimeToFormat(mRecordModel.getCreateTime()));
        mTvAudioTime.setText(AudioUtil.formatToAudioShowTime(mRecordModel.getRecordLength()));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.tv_audio_play) { //播放录音
            if(mPlayer == null) {
                mPlayer = new AudioPlayerUtil(mContext);
            }

            Observable.create(new Observable.OnSubscribe<Object>(){
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    String recordName = mRecordModel.getRecordName();
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecord",recordName);
                    mPlayer.play(file);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {

                        }
                    });
        }
    }
}
