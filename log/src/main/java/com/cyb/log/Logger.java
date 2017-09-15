package com.cyb.log;

import android.util.Log;

/**
 * Created by cyb on 2017/8/29.
 */

public class Logger {

    public static final boolean showLog = true;

    public static void v(String tag, String message){
        if(showLog){
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message){
        if(showLog){
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message){
        if(showLog){
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message){
        if(showLog){
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message){
        if(showLog){
            Log.e(tag, message);
        }
    }
}
