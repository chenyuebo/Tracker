package com.cyb.tracker.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cyb.android.util.DateUtil;
import com.cyb.tracker.R;
import com.hyphenate.chat.EMMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by cyb on 2017/9/5.
 */

public class MessageAdapter extends BaseAdapter {

    List<EMMessage> messageList;
    Context context;

    public MessageAdapter(List<EMMessage> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    public void notifyDataSetChangedOnUIThread() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return messageList == null ? 0 : messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_message_text = (TextView) convertView.findViewById(R.id.tv_message_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EMMessage emMessage = (EMMessage) getItem(position);
        String msgTime = DateUtil.date2Str(new Date(emMessage.getMsgTime()), "yyyy-MM-dd hh:mm:ss");
        viewHolder.tv_message_text.setText("[" + msgTime + "] " + emMessage.getBody());
        return convertView;
    }

    public static class ViewHolder{
        TextView tv_message_text;
    }
}
