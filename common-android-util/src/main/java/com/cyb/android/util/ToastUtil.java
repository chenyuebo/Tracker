package com.cyb.android.util;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cyb on 2017/9/15.
 */
public class ToastUtil {

    private static Toast mToast;
    private static TextView mContentView;

    public static void init(Context appContext) {
        final Context applicationContext = appContext.getApplicationContext();
        mToast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT);
        final int messageId = applicationContext.getResources().getIdentifier("message", "id", "android");
        mContentView = (TextView) mToast.getView().findViewById(messageId);
    }

    public static void showToast(String message) {
        mContentView.setText(message);
        mToast.show();
    }
}
