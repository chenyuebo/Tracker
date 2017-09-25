package com.cyb.tracker.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cyb.easemob.utils.EMClientUtil;
import com.cyb.log.Logger;

/**
 *
 * Created by chenyuebo on 2016/11/17 0017.
 */

public class TKDeviceAdminReceiver extends DeviceAdminReceiver {

    private static final String TAG = "TKDeviceAdminReceiver";

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Logger.d("cybClient", TAG + " onDisabled 设备管理器已取消激活");
        EMClientUtil.sendTextMsg("设备管理器已取消激活", "trackermaster", null);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Logger.d("cybClient", TAG + " onEnabled 设备管理器已激活");
        EMClientUtil.sendTextMsg("设备管理器已激活", "trackermaster", null);
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        Logger.d("cybClient", TAG + " onDisableRequested");
        return super.onDisableRequested(context, intent);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        Logger.d("cybClient", TAG + " onPasswordChanged 设备密码已修改");
        EMClientUtil.sendTextMsg("设备密码已修改", "trackermaster", null);
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        Logger.d("cybClient", TAG + " onPasswordFailed 设备解锁失败");
        EMClientUtil.sendTextMsg("设备解锁失败", "trackermaster", null);
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
        Logger.d("cybClient", TAG + " onPasswordSucceeded 设备解锁成功");
        EMClientUtil.sendTextMsg("设备解锁成功", "trackermaster", null);
    }


}
