package lta.com.audioRecord;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

import lta.com.audioRecord.utils.AppContextUtils;

/**
 * @author: LuTaiAn
 * @className: ContainerApplication
 * @description: 初始化
 * @date: 2017/4/26
 */
public class ContainerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtils.init(getApplicationContext());
        SpeechUtility.createUtility(ContainerApplication.this, "appid=590edd12");
    }
}
