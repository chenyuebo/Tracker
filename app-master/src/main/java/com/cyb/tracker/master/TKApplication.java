package com.cyb.tracker.master;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.cyb.android.util.ProcessUtil;
import com.cyb.android.util.ToastUtil;
import com.cyb.log.Logger;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;

import java.util.List;

/**
 * Created by cyb on 2017/9/4.
 */

public class TKApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtil.init(this);
        String processAppName = ProcessUtil.getProcessName(this, Process.myPid());
        if (this.getPackageName().equals(processAppName)) { // 在主进程初始化环信
            EMOptions options = new EMOptions();
            // 默认添加好友时，是不需要验证的，改成需要验证
            options.setAcceptInvitationAlways(false);
            options.setAutoLogin(true); // 
            //初始化
            EMClient.getInstance().init(this, options);
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(false);

            EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Logger.d("cybHost", "onMessageReceived " + messages);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            Logger.d("cybHost", "onCmdMessageReceived " + messages);
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            Logger.d("cybHost", "onMessageRead " + messages);
        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {
            Logger.d("cybHost", "onMessageDelivered " + messages);
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };
}
