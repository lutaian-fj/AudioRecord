package lta.com.audioRecord.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author: LuTaiAn
 * @className: BaseFragment
 * @description: Fragment基类
 * @date: 2017/4/24
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected String mTAG;

    /**
     * 初始化布局资源文件
     * @param:
     * @return:
     */
    public abstract int initResource();

    /**
     * 初始化组件
     * @param: view
     * @return:
     */
    public abstract void initComponent(View view);

    /**
     * 初始化数据,在此请求网络数据
     * @param:
     * @return:
     */
    public abstract void initData();

    /**
     * 添加监听
     * @param:
     * @return:
     */
    public abstract void addListener();

    /**
     * 创建初始化
     * @param:
     * @return:
     */
    public void onCreate(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mTAG = getClassName();
        super.onCreate(savedInstanceState);
        onCreate();
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(initResource(), null);
        initComponent(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addListener();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * @Title:
     * @Description: 打印调试
     * @param:
     * @return:
     * @throws:
     */
    public void outPut(String dsc, String str) {
        Log.e("lta",dsc+":"+str);
    }

    /**
     * @Title:
     * @Description: 吐司调试
     * @param:
     * @return:
     * @throws:
     */
    public void toast(String str) {
        Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
    }
}
