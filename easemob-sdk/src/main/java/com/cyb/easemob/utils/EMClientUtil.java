package com.cyb.easemob.utils;

import com.cyb.log.Logger;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * Created by cyb on 2017/9/15.
 */

public class EMClientUtil {

    /**
     * 发送文本消息
     * @param content 发送的文本内容
     * @param toChatUsername 接收方环信ID
     */
    public static void sendTextMsg(String content, String toChatUsername, final EMCallBack emCallBack){
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Logger.d("cybCommon", "EMClientUtil sendTextMsg onSuccess");
                if(emCallBack != null){
                    emCallBack.onSuccess();
                }
            }

            @Override
            public void onError(int code, String error) {
                Logger.d("cybCommon", "EMClientUtil sendTextMsg onError code=" + code + " error=" + error);
                if(emCallBack != null){
                    emCallBack.onError(code, error);
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                if(emCallBack != null){
                    emCallBack.onProgress(progress, status);
                }
            }
        });
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
