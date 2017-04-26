package lta.com.audioRecord.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lta.com.audioRecord.R;
import lta.com.audioRecord.utils.AudioUtil;

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

    public RecordItemView(Context context) {
        super(context);
        init(context);
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
        mIvDelete = (ImageView) findViewById(R.id.iv_remove);
        animationDrawable = AudioUtil.getAnimationDrawable(getContext());
        mIvDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
