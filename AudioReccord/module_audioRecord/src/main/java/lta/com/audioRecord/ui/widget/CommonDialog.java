package lta.com.audioRecord.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import lta.com.audioRecord.R;

/**
 * @author: LuTaiAn
 * @className: CommonDialog
 * @description: 弹出框
 * @date: 2017/5/10
 */
public class CommonDialog extends Dialog {

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * title容器
     */
    protected FrameLayout mTitleContainer;
    /**
     * 弹窗的标题TextView
     */
    protected TextView mTvTitle;
    /**
     * 弹窗的标题
     */
    protected String mStrTitle;

    /**
     * 内容容器
     */
    protected FrameLayout mContentContainer;
    /**
     * 提示信息文字textview
     */
    protected TextView mTvTip;
    /**
     * 提示信息文字
     */
    protected String mStrTip;

    /**
     * button容器
     */
    protected FrameLayout mButtonContainer;
    /**
     * 左边按钮
     */
    protected TextView mBtnLeft;
    /**
     * 左边按钮文字
     */
    protected String mStrLeft;
    /**
     * 右边按钮
     */
    protected TextView mBtnRight;
    /**
     * 右边按钮文字
     */
    protected String mStrRight;

    /**
     * 分隔线
     */
    protected View mDividerBottom;
    /**
     * 分隔线
     */
    protected View mDividerTop;

    /**
     * 按钮点击监听
     */
    protected OnCommonDlgClickListener mListener;

    /**
     * 所加载的layout资源ID
     */
    private int mLayoutResID = 0;

    /**
     * 按钮点击监听接口
     *
     * @author chenyw
     */
    public interface OnCommonDlgClickListener {
        /**
         * 左按钮点击处理
         */
        void onLeftBtnClicked();

        /**
         * 右按钮点击处理
         */
        void onRightBtnClicked();
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setOnClickListener(OnCommonDlgClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Creates a new instance of CommonTipDlg.
     *
     * @param context     　上下文
     * @param strTip      　　提示信息
     * @param strLeftBtn  　左边按钮文字
     * @param strRightBtn 　右边按钮文字
     * @param styleResId  　　　对话框样式ID
     */
    public CommonDialog(Context context, String strTip, String strLeftBtn,
                        String strRightBtn, int styleResId) {
        super(context, styleResId);
        mContext = context;
        mStrTip = strTip;
        mStrLeft = strLeftBtn;
        mStrRight = strRightBtn;
        mLayoutResID = R.layout.common_dlg;
    }

    /**
     * Creates a new instance of CommonTipDlg.
     *
     * @param context     　上下文
     * @param strTipTitle 　提示信息标题
     * @param strTip      　提示信息
     * @param strLeftBtn  　左边按钮文字
     * @param strRightBtn 　右边按钮文字
     * @param styleResId  对话框样式ID
     */
    public CommonDialog(Context context, String strTipTitle, String strTip, String strLeftBtn,
                        String strRightBtn, int styleResId) {
        super(context, styleResId);
        mContext = context;
        mStrTitle = strTipTitle;
        mStrTip = strTip;
        mStrLeft = strLeftBtn;
        mStrRight = strRightBtn;
        mLayoutResID = R.layout.common_dlg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(mLayoutResID);

        setupView();
    }

    /**
     * setup view
     */
    private void setupView() {
        //宽度设置为屏幕的7 / 8
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        final int SEVEN = 8;
        final int EIGHT = 8;
        //竖屏时，取屏幕宽度
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            lp.width = (int) (wm.getDefaultDisplay().getWidth() * SEVEN / EIGHT);
        } else {
            //横屏时，取屏幕高度
            lp.width = (int) (wm.getDefaultDisplay().getHeight() * SEVEN / EIGHT);
        }
        getWindow().setAttributes(lp);

        mDividerBottom = findViewById(R.id.view_divider_bottom);
        mDividerTop = findViewById(R.id.view_divider_top);

        //title
        View titleView = getTitleView();
        mTitleContainer = (FrameLayout) findViewById(R.id.container_title);
        if (titleView != null) {
            mTitleContainer.removeAllViews();
            mTitleContainer.addView(titleView);
        } else {
            mTvTitle = (TextView) findViewById(R.id.tv_title);

            if (!TextUtils.isEmpty(mStrTitle)) {
                mTvTitle.setText(mStrTitle);
                mTitleContainer.setVisibility(View.VISIBLE);
            } else {
                mTitleContainer.setVisibility(View.GONE);
            }
        }



        //button
        View buttonView = getButtonView();
        mButtonContainer = (FrameLayout) findViewById(R.id.container_button);
        if (buttonView != null) {
            mButtonContainer.removeAllViews();
            mButtonContainer.addView(buttonView);
        } else {
            mBtnLeft = (TextView) findViewById(R.id.btn_left);
            if (TextUtils.isEmpty(mStrLeft)) {
                mBtnLeft.setVisibility(View.GONE);
            } else {
                mBtnLeft.setText(mStrLeft);
            }

            mBtnRight = (TextView) findViewById(R.id.btn_right);
            if (!TextUtils.isEmpty(mStrRight)) {
                mBtnRight.setText(mStrRight);
            }

            mBtnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftBtnClick();
                }
            });
            mBtnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightBtnClick();
                }
            });

            //content
            View contentView = getContentView();
            mContentContainer = (FrameLayout) findViewById(R.id.container_content);
            if (contentView != null) {
                mContentContainer.removeAllViews();
                mContentContainer.addView(contentView);
            } else {
                mTvTip = (TextView) findViewById(R.id.tv_content);
                if (!TextUtils.isEmpty(mStrTip)) {
                    mTvTip.setText(mStrTip);
                }
            }
        }
    }

    /**
     * title view 钩子函数,可以重写方法自定义title布局
     *
     * @return
     */
    protected View getTitleView() {
        return null;
    }

    /**
     * content view 钩子函数，可以重写方法自定义content布局
     *
     * @return
     */
    protected View getContentView() {
        return null;
    }

    /**
     * button view 钩子函数,可以重写方法自定义button布局
     *
     * @return
     */
    protected View getButtonView() {
        return null;
    }

    /**
     * 右边按钮点击事件
     */
    protected void onRightBtnClick() {
        if (null != mListener) {
            mListener.onRightBtnClicked();
        }
        dismiss();
    }

    /**
     * 左边按钮点击事件
     */
    protected void onLeftBtnClick() {
        if (null != mListener) {
            mListener.onLeftBtnClicked();
        }
        dismiss();
    }

    /**
     * set tip
     *
     * @param tip
     */
    public void setTip(String tip) {
        if (mTvTip == null) {
            return;
        }

        this.mStrTip = tip;
        mTvTip.setText(mStrTip);
    }

    /**
     * set title
     *
     * @param title
     */
    public void setTitle(String title) {
        if (mTvTitle == null) {
            return;
        }

        this.mStrTitle = title;
        mTvTitle.setText(mStrTitle);
    }

    /**
     * 设置左边的按钮可见
     */
    public void setLeftButtonVisible(boolean visible) {
        if (mBtnLeft == null) {
            return;
        }

        if (visible) {
            mBtnLeft.setVisibility(View.VISIBLE);
        } else {
            mBtnLeft.setVisibility(View.GONE);
        }
    }


    /**
     * 设置title和tip之间的divider可见
     */
    public void setTopDividerVisible(boolean visible) {
        if (mDividerTop == null) {
            return;
        }

        mDividerTop.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置tip和button之间的divider可见
     */
    public void setBottomDividerVisible(boolean visible) {
        if (mDividerBottom == null) {
            return;
        }

        mDividerBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
