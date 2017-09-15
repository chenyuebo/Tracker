package com.cyb.tracker.master.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cyb.log.Logger;
import com.cyb.tracker.master.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Logger.d("cybHost", "username=" + username);
                Logger.d("cybHost", "password=" + password);

                loginEM(username, password);
                break;
        }
    }

    private void loginEM(String username, String password){
        EMClient.getInstance().login(username, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Logger.d("main", "登录聊天服务器失败！");
            }
        });
    }
}

