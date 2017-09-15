package com.cyb.tracker.master.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cyb.log.Logger;
import com.cyb.tracker.master.R;
import com.cyb.tracker.master.activity.ChatActivity;
import com.cyb.tracker.master.adapter.ConversationAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cyb on 2017/9/15.
 */

public class ConversationFragment extends BaseFragment implements AdapterView.OnItemClickListener, PullToRefreshListView.OnRefreshListener2<ListView>{

    PullToRefreshListView pl_refresh_conversations;
    ListView lv_conversations;
    ConversationAdapter adapter;
    List<EMConversation> conversationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView(){
        pl_refresh_conversations = (PullToRefreshListView) getView().findViewById(R.id.pl_refresh_conversations);
        pl_refresh_conversations.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pl_refresh_conversations.setOnRefreshListener(this);
        lv_conversations = pl_refresh_conversations.getRefreshableView();
        lv_conversations.setDividerHeight(0);
        lv_conversations.setOnItemClickListener(this);

        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_empty_message, null);
        lv_conversations.setEmptyView(emptyView);

        conversationList = new ArrayList<>();
        adapter = new ConversationAdapter(getContext(), conversationList);
        lv_conversations.setAdapter(adapter);
    }

    private void initData(){
        Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
        Logger.d("cybHost", conversationMap.toString());
        conversationList.clear();

        for(String key : conversationMap.keySet()){
            conversationList.add(conversationMap.get(key));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int headerViewsCount = lv_conversations.getHeaderViewsCount();
        EMConversation emConversation = conversationList.get(position - headerViewsCount);

        final EMMessage lastMessage = emConversation.getLastMessage();
        Intent intent = ChatActivity.getlaunchIntent(getContext(), lastMessage.getFrom(), lastMessage.getUserName());
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pl_refresh_conversations.onRefreshComplete();
            }
        }, 500);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
