package com.cyb.tracker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cyb.android.util.ToastUtil;
import com.cyb.easemob.utils.EMClientUtil;
import com.cyb.log.Logger;
import com.cyb.tracker.R;
import com.cyb.tracker.adapter.MessageAdapter;
import com.hyphenate.EMCallBack;
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
public class ChatFragment extends Fragment implements View.OnClickListener {

    ListView lv_message;
    MessageAdapter messageAdapter;
    List<EMMessage> messageList;

    EditText et_msg_input;

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
        lv_message = (ListView) getView().findViewById(R.id.lv_message);

        et_msg_input = (EditText) getView().findViewById(R.id.et_msg_input);
        getView().findViewById(R.id.btn_send).setOnClickListener(this);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, getContext());
        lv_message.setAdapter(messageAdapter);

        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Logger.d("cybClient", "ChatFragment conversations=" + conversations);

        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation("trackermaster");
        Logger.d("cybClient", "ChatFragment emConversation=" + emConversation);
        if(emConversation != null) {
            List<EMMessage> messages = emConversation.getAllMessages();
            messageList.addAll(messages);
            messageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                String msgContent = et_msg_input.getText().toString();
                EMClientUtil.sendTextMsg(msgContent, "trackermaster", new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast("发送成功");
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {

                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
                break;
        }
    }

    EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            EMConversation emConversation = EMClient.getInstance().chatManager().getConversation("trackermaster");
            Logger.d("cybClient", "ChatFragment emConversation=" + emConversation);
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
            Logger.d("cybClient", "emConnectionListener onConnected");
        }

        @Override
        public void onDisconnected(int errorCode) {
            Logger.d("cybClient", "emConnectionListener onDisconnected errorCode=" + errorCode);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
        EMClient.getInstance().removeConnectionListener(emConnectionListener);
    }

}
