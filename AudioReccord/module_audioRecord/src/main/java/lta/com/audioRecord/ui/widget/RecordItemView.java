package lta.com.audioRecord.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import lta.com.audioRecord.R;
import lta.com.audioRecord.data.db.dao.RecordDao;
import lta.com.audioRecord.data.db.model.RecordModel;
import lta.com.audioRecord.utils.AudioPlayerUtil;
import lta.com.audioRecord.utils.AudioUtil;
import lta.com.audioRecord.utils.FileUtil;
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
public class RecordItemView extends LinearLayout implements View.OnClickListener {
    private Context mContext; //上下文
    private TextView mTvAudioTime; //录音失常
    private TextView mTvAudioCreateTime; //录音创建时间
    private TextView mTvAudioPlay; //录音播放按钮
    private ImageView mIvDelete; //录音删除按钮
    private AnimationDrawable animationDrawable; //播放的动画
    private RecordModel mRecordModel; //录音实体类
    private AudioPlayerUtil mPlayer;
    private CallBack mCallBack; //回调
    private CommonDialog mDeleteDialog;

    public RecordItemView(Context context, RecordModel recordModel) {
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
     *
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
     *
     * @param: model
     * @return:
     */
    private void bindData() {
        mTvAudioCreateTime.setText(TimeUtil.convertTimeToFormat(mRecordModel.getCreateTime()));
        mTvAudioTime.setText(AudioUtil.formatToAudioShowTime(mRecordModel.getRecordLength()));
    }

    /**
     * 设置播放按钮背景
     *
     * @param:
     * @return:
     */
    public void setPlayIconStop() {
        mTvAudioPlay.setBackgroundResource(R.mipmap.audio_play0);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_audio_play) { //播放录音
            if(mCallBack.isRecording()) {
                Toast.makeText(mContext,"正在录音无法播放",Toast.LENGTH_SHORT).show();
                return;
            }
            if (mPlayer == null) {
                mPlayer = AudioPlayerUtil.getInstance(mContext);
            }
            final String recordName = mRecordModel.getRecordName();
            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ziliaoyinsong", recordName);
            if (!file.exists()) {
                Toast.makeText(mContext, "录音不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            mTvAudioPlay.setBackgroundDrawable(animationDrawable);
            animationDrawable.start();
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
//                    mPlayer.stopPlayer(new OnStopCallPack(){
//                        @Override
//                        public void onStopCallBack() {
//                            animationDrawable.stop();
////                            setPlayIconStop();
//                        }
//                    });
                    mPlayer.play(file);
                    mPlayer.setCompleteListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            animationDrawable.stop();
                            setPlayIconStop();
                        }
                    });
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {

                        }
                    });
        } else if (id == R.id.iv_remove) { //删除录音
            showDelDialog();
        }
    }

    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    /**
     * 显示删除对话框
     *
     * @param:
     * @return:
     */
    private void showDelDialog() {
        if (mDeleteDialog == null) {
            String deleteTip = mContext.getString(R.string.delete_record_tip);
            String okText = mContext.getString(R.string.ok);
            String cancelText = mContext.getString(R.string.cancel);
            mDeleteDialog = new CommonDialog(mContext, mContext.getString(R.string.tip), deleteTip, cancelText, okText, R.style.common_dialog_style);
            mDeleteDialog.setOnClickListener(new CommonDialog.OnCommonDlgClickListener() {
                @Override
                public void onLeftBtnClicked() {

                }

                @Override
                public void onRightBtnClicked() {
                    if (mCallBack != null) {
                        /********************删除布局中的记录**************************/
                        ViewParent parent = RecordItemView.this.getParent();
                        if (parent instanceof ViewManager) {
                            ((ViewManager) parent).removeView(RecordItemView.this);
                        }
                        /**********************删除录音文件***************************/
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ziliaoyinsong/" + mRecordModel.getRecordName();
                        FileUtil.deleteFile(path);
                        /***********************删除数据库****************************/
                        RecordDao.getInstance().delete(mRecordModel);
                        mCallBack.delete(mRecordModel);
                    }
                }
            });
        }
        mDeleteDialog.show();
    }

    /**
     * @author: LuTaiAn
     * @className: RecordItemView
     * @description: 删除回调
     * @date: 2017/5/10
     */
    public interface CallBack {
        void delete(RecordModel model);

        boolean isRecording();
    }
}
