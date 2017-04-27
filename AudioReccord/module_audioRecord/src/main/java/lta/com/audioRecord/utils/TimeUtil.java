package lta.com.audioRecord.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: LuTaiAn
 * @className: TimeUtil
 * @description: 日期工具类
 * @date: 2017/4/27
 */
public class TimeUtil {
    public static final long MINUTE_MILLIS = 60 * 1000l;
    public static final long DAY_MILLIS = 24 * 60 * MINUTE_MILLIS;
    public final static String FORMAT_DATE = "yyyy年MM月dd日 HH:mm";
    public final static String FORMAT_DATE1 = "HH时mm分ss秒_yyyy-MM-dd";
    public final static String FORMAT_DATE2 = "yyyy-MM-dd";
    public final static String FORMAT_DATE3 = "yyMMdd";
    public final static String FORMAT_DATE4 = "HH:mm";
    public final static String FORMAT_DATE5 = "yyyy年 MM月dd日 ";
    public final static String FORMAT_DATE6 = "yyyy年MM月dd日";
    public final static String FORMAT_DATE8 = "yyyy";
    public final static String FORMAT_DATE7 = "MM-dd";
    public final static String FORMAT_DATE9 = "yyyy/MM/dd";
    public final static String FORMAT_DATE10 = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE11 = "MM月dd日";
    public final static String FORMAT_DATE12 = "yyyyMMdd";
    public final static String FORMAT_DATE13 = "MM月dd日 hh:mm";
    public final static String FORMAT_DATE14 = "yyyy年  MM月dd日  HH时mm分";
    public final static String FORMAT_DATE15 = "yyyy-MM-dd HH:mm:ss";


    /**
     * @Title: formatDate
     * @Description: 时间格式转换
     * @param: timestamp
     * @param: dateFormat
     * @return:
     * @throws:
     */
    public static String formatDate(long timestamp, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            if (timestamp != 0)
                return format.format(timestamp);
        } catch (Exception e) {
            Log.e("TimeUtil", e.toString());
        }
        return "";
    }

    public static String convertTimeToFormat(long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        long curTime = System.currentTimeMillis();
        long time = (curTime - timeStamp) / (long) 1000;
        String result = "";
        if (time < 60 && time >= 0) { // 小于1分钟
            result = "刚刚";
        } else if (time >= 60 && time < 3600) { // 1-60分钟
            result = time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) { // 1-24小时
            result = time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 7) { // 一周之内
            result = time / (3600 * 24) + "天前";
        } else if (time >= 3600 * 24 * 7 && time < 3600 * 24 * 365) { // 1年之内
            result = formatDate(timeStamp, FORMAT_DATE13);
        } else if (time >= 3600 * 24 * 365) { // 超过一年
            result = formatDate(timeStamp, FORMAT_DATE);
        }
        return result;
    }

    /**
     * 时间戳转化成对应的日期格式
     */
    public static String convertTimeToFormatByTrain(long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        long curTime = System.currentTimeMillis();
        long time = (curTime - timeStamp) / (long) 1000;
        String result = "";
        if (time < 60 && time >= 0) { // 小于1分钟
            result = "刚刚";
        } else if (time >= 60 && time < 3600) { // 1-60分钟
            result = time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) { // 1-24小时
            result = time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 7) { // 一周之内
            result = time / (3600 * 24) + "天前";
        } else {
            result = formatDate(timeStamp, FORMAT_DATE6);
        }
//        else if (time >= 3600 * 24 * 7 && time < 3600 * 24 * 365) { // 1年之内
//            result = formatDate(timeStamp, FORMAT_DATE13);
//        } else if (time >= 3600 * 24 * 365) { // 超过一年
//            result = formatDate(timeStamp, FORMAT_DATE);
//        }
        return result;
    }

    /**
     * 毫秒转成分:秒
     *
     * @return
     */
    public static String getMSSTime(long time) {
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        final long m = seconds / 60;
        final long s = seconds % 60;
        if (s > 9) {
            return m + ":" + s;
        } else {
            return m + ":0" + s;
        }
    }

    /**
     * 毫秒转成时|分|秒
     *
     * @param time 要转换的毫秒
     * @return 转化之后的分
     */
    public static String getHMS(long time) {
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        if (seconds < 60) {
            return seconds + "秒";
        }
        final long minute = seconds / 60;
        if (minute < 60) {
            return minute + "分钟";
        }
        final long hour = minute / 60;
        return hour + "小时";
    }

}
