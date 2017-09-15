package com.cyb.tracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyb.log.Logger;
import com.cyb.tracker.R;
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
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else {
            Logger.d("cybClient", "环信未登录");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
