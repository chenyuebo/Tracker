package com.cyb.tracker.master.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cyb.log.Logger;
import com.cyb.tracker.master.R;
import com.cyb.tracker.master.adapter.MessageAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cyb on 2017/9/5.
 */
public class ChatFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener2<ListView> {

    PullToRefreshListView pl_refresh_message;
    ListView lv_message;
    MessageAdapter messageAdapter;
    List<EMMessage> messageList;

    private String hxUserId;
    private String userNickName;

    public ChatFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        EMClient.getInstance().addConnectionListener(emConnectionListener);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        initData();
    }

    private void initView() {
        hxUserId = getArguments().getString("hxUserId");
        userNickName = getArguments().getString("userNickName");

        pl_refresh_message = (PullToRefreshListView) getView().findViewById(R.id.pl_refresh_message);
        pl_refresh_message.setMode(PullToRefreshBase.Mode.BOTH);
        pl_refresh_message.setOnRefreshListener(this);
        lv_message = pl_refresh_message.getRefreshableView();
        lv_message.setDividerHeight(0);

        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_empty_message, null);
        lv_message.setEmptyView(emptyView);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, getContext());
        lv_message.setAdapter(messageAdapter);
    }

    private void initData() {
        messageList.clear();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Logger.d("cybHost", "ChatFragment conversations=" + conversations);

        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(hxUserId);
        Logger.d("cybHost", "ChatFragment emConversation=" + emConversation);
        if(emConversation != null) {
            int allMsgCount = emConversation.getAllMsgCount();
            Logger.d("cybHost", "ChatFragment allMsgCount=" + allMsgCount);
            final int pageSize = 1000;
            emConversation.loadMoreMsgFromDB(emConversation.getLastMessage().getMsgId(), pageSize);

            List<EMMessage> messages = emConversation.getAllMessages();
            messageList.addAll(messages);
            messageAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pl_refresh_message.onRefreshComplete();
            }
        }, 500);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pl_refresh_message.onRefreshComplete();
            }
        }, 500);
    }

    EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(hxUserId);
            Logger.d("cybHost", "ChatFragment emConversation=" + emConversation);
            if (emConversation != null) {
                messageList.clear();
                messageList.addAll(emConversation.getAllMessages());
                messageAdapter.notifyDataSetChangedOnUIThread();
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

            if (messageAdapter != null) {
                messageAdapter.notifyDataSetChangedOnUIThread();
            }
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };

    EMConnectionListener emConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {
            Logger.d("cybHost", "emConnectionListener onConnected");
        }

        @Override
        public void onDisconnected(int errorCode) {
            Logger.d("cybHost", "emConnectionListener onDisconnected errorCode=" + errorCode);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
        EMClient.getInstance().removeConnectionListener(emConnectionListener);
    }

}
