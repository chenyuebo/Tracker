package com.cyb.tracker.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cyb.tracker.master.R;
import com.cyb.tracker.master.fragment.ChatFragment;


public class ChatActivity extends AppCompatActivity {

    public static Intent getlaunchIntent(Context context, String hxUserId, String userNickName){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("hxUserId", hxUserId);
        intent.putExtra("userNickName", userNickName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String hxUserId = getIntent().getStringExtra("hxUserId");
        String userNickName = getIntent().getStringExtra("userNickName");

        setTitle(hxUserId);

        ChatFragment chatFragment = new ChatFragment();
        Bundle arguments = new Bundle();
        arguments.putString("hxUserId", hxUserId);
        arguments.putString("userNickName", userNickName);
        chatFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chatFragment).commit();
    }
}
