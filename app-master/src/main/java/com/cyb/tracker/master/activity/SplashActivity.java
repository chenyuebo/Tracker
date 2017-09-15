package com.cyb.tracker.master.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyb.log.Logger;
import com.cyb.tracker.master.R;
import com.hyphenate.chat.EMClient;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(EMClient.getInstance().isLoggedInBefore()){
            Logger.d("cybHost", "环信已登录");
            startActivity(new Intent(this, MainActivity.class));
        }else {
            Logger.d("cybHost", "环信未登录");
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
