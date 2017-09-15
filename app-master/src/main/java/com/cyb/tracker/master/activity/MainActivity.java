package com.cyb.tracker.master.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cyb.tracker.master.R;
import com.cyb.tracker.master.fragment.ConversationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConversationFragment conversationFragment = new ConversationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, conversationFragment).commit();
    }
}
