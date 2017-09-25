package com.cyb.tracker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.cyb.easemob.utils.EMClientUtil;
import com.cyb.log.Logger;

/**
 * Created by cyb on 2017/9/18.
 */

public class UniversalReceiver extends BroadcastReceiver {

    private boolean isRegisted = false;

    public void setRegisted(boolean registed) {
        isRegisted = registed;
    }

    public boolean isRegisted() {
        return isRegisted;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Logger.d("cybClient", "UniversalReceiver onReceive action=" + action);

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int level = intent.getIntExtra("level", -1); // 获得当前电量
            int scale = intent.getIntExtra("scale", -1); // 获得总电量
            Logger.d("cybClient", "UniversalReceiver onReceive 当前电量level=" + level + " 总电量scale="
                    + scale + " 百分比=" + String.format("%.02f%%", 100.0f * level / scale));
            if (level % 10 == 0 && level != 100) {
                EMClientUtil.sendTextMsg(String.format("当前电量：%d", level), "trackermaster", null);
            }
        } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {

            EMClientUtil.sendTextMsg(action, "trackermaster", null);
        } else {
            EMClientUtil.sendTextMsg(action, "trackermaster", null);
        }
    }
}
