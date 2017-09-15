package com.cyb.tracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cyb.log.Logger;
import com.cyb.tracker.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

                EMClient.getInstance().login(username, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Logger.d("main", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onError(int code, String error) {
                        Logger.d("main", "登录聊天服务器失败！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
                break;
        }
    }
}
