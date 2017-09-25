package com.cyb.tracker.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyb.log.Logger;
import com.cyb.tracker.R;
import com.cyb.tracker.receiver.UniversalReceiver;
import com.hyphenate.chat.EMClient;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(EMClient.getInstance().isLoggedInBefore()){
            Logger.d("cybClient", "环信已登录");

            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();

            registeUniversalReceiver();

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }else {
            Logger.d("cybClient", "环信未登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void registeUniversalReceiver() {
        UniversalReceiver universalReceiver = new UniversalReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.getApplicationContext().registerReceiver(universalReceiver, intentFilter);
    }
}
