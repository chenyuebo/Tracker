package com.cyb.android.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by cyb on 2017/9/18.
 */

public class VibrateUtil {

    public static Vibrator vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {50, 50};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);
        return vibrator;
    }

    public static void cancel(Vibrator vibrator) {
        vibrator.cancel();
    }
}
