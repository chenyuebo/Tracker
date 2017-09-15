package com.cyb.tracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyb.tracker.R;
import com.cyb.tracker.fragment.ChatFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatFragment chatFragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chatFragment).commit();
    }
}
