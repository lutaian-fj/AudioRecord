package lta.com.audioRecord.utils;

import android.content.Context;

/**
 * @author: LuTaiAn
 * @className: AppContextUtils
 * @description:
 * @date: 2017/4/26
 */
public class AppContextUtils {
    private AppContextUtils(){}

    private static Context sAppContext;

    /**
     * 初始化
     */
    public static void init(Context ctx) {
        sAppContext = ctx.getApplicationContext();
    }

    /**
     * 获得 Application Context
     */
    public static Context getContext() {
        return sAppContext;
    }

    /**
     * 获得Context<br>
     * 如果传入的context为空则返回当前Application Context
     */
    public static Context getContext(Context context) {
        return context != null ? context : sAppContext;
    }
}
