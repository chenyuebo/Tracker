package com.cyb.android.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cyb on 2017/9/5.
 */

public class DateUtil {

    /**
     * 将Date对象转换成指定格式的字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将指定格式的字符串转成date对象
     *
     * @param date
     * @param format
     * @return
     */
    public static Date str2Date(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
