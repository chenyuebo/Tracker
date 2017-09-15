package com.cyb.tracker.master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cyb.android.util.DateUtil;
import com.cyb.tracker.master.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by cyb on 2017/9/15.
 */

public class ConversationAdapter extends BaseAdapter {

    Context context;
    List<EMConversation> list;

    public ConversationAdapter(Context context, List<EMConversation> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_conversation, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);

            viewHolder.tv_client_name = (TextView) convertView.findViewById(R.id.tv_client_name);
            viewHolder.tv_last_msg_time = (TextView) convertView.findViewById(R.id.tv_last_msg_time);
            viewHolder.tv_last_msg = (TextView) convertView.findViewById(R.id.tv_last_msg);
            viewHolder.tv_unread_count = (TextView) convertView.findViewById(R.id.tv_unread_count);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            EMConversation conversation = (EMConversation) getItem(position);
            final EMMessage lastMessage = conversation.getLastMessage();
            viewHolder.tv_client_name.setText(lastMessage.getFrom());
            viewHolder.tv_last_msg_time.setText(DateUtil.date2Str(new Date(lastMessage.getMsgTime()), "yyyyMMdd hh:mm:ss"));
            viewHolder.tv_last_msg.setText(lastMessage.getBody().toString());
            viewHolder.tv_unread_count.setText(conversation.getUnreadMsgCount() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHolder{
        TextView tv_client_name;
        TextView tv_last_msg_time;
        TextView tv_last_msg;
        TextView tv_unread_count;
    }
}
