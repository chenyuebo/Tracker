package com.cyb.tracker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cyb.easemob.utils.EMClientUtil;
import com.cyb.log.Logger;

/**
 * Created by cyb on 2017/9/15.
 */

public class BootCompleteReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Logger.d("cybClient", "BootCompleteReceiver onReceive action=" + action);

        EMClientUtil.sendTextMsg("系统启动完成", "trackermaster", null);
    }
}
