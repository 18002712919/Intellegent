package com.asus.intellegent.open;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;

import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.ui.activity.MainActivity;
import com.asus.intellegent.understandjson.TextJson;
import com.asus.intellegent.voice.MessageHandler;
import com.asus.intellegent.voice.StaticDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/6/13.
 */

public class SendMessage implements Open {

    private static MainActivity main;
    private MessageHandler handler;
    private static String msg_number;//发短信时的联系人电话
    private static String msg_name;//发短信时的联系人名字
    private List<ContactInfo> contactLists;
    private static String TAG = MessageHandler.class.getSimpleName();

    public SendMessage(Context context, MessageHandler handler) {
        main = (MainActivity) context;
        contactLists = main.getContactLists(context);
        this.handler = handler;
        handler.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                start();
            }
        };
    }

    @Override
    public int open(String resultString) {
        if (contactLists.isEmpty()) {
            main.updateView("通讯录为空", FeedAdapter.FeedItem.TYPE_RECEIVE);
            //main.app.setTts("通讯录为空");
            return 0;
        }
        TextJson.SemanticBean.SlotsBean slots = handler.textJson.getSemantic().getSlots();
        String name = slots.getName();
        String code = slots.getCode();
        if (name != null) {
            boolean isMessage = getSendMsgContactInfo(name);
            if (isMessage) {
                return StaticDate.SENDMESSAGE;
            }
        }
        return 0;
    }

    public static void sendMessage(String content) {
        if ("是".equals(content)) {
            //sendMessage(content);
            return;
        } else {
            if (("否".equals(content))) {
                content = null;
            }
            if (content == null) {
                main.updateView("请重新说要发送的内容。", FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("请重新说要发送的内容。");
                return;
            }
            if(!StaticDate.isMessage) {
                Log.e(TAG + msg_name, content + "  " + msg_number);
                main.updateView("是否发送内容为：" + content + "给" + msg_name + "。",
                        FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("是否发送内容为：" + content + "给" + msg_name + "。");
                StaticDate.isMessage = false;
                //main.isIat();
            }
        }
    }
    private void start() {
        main.isIat();
    }
    //发送短信的内容
    private void send_out(String content) {
        SmsManager manager = SmsManager.getDefault();
        //因为一条短信有字数限制，因此要将长短信拆分
        ArrayList<String> list = manager.divideMessage(content);
        for (String text : list) {
            manager.sendTextMessage(msg_number, null, text,
                    null, null);
        }
        main.updateView("已经发送...", FeedAdapter.FeedItem.TYPE_RECEIVE);
        //main.app.setTts("已经发送..." + msg_name);
    }

    //发送短信的联系人信息
    private boolean getSendMsgContactInfo(String string) {
        for (ContactInfo contactInfo : contactLists) {
            if (string.contains(contactInfo.getName())) {
                msg_name = contactInfo.getName();
                msg_number = contactInfo.getNumber();
                main.updateView("请问您要发送什么给" + msg_name,
                        FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("请问您要发送什么给" + msg_name);
                StaticDate.isMessage = false;
                if (true) {
                    main.getApp().getIatDialog();
                } else {
                    main.getApp().getIat();
                }
                Log.e(TAG, "111111111111" + StaticDate.isMessage);
                return true;
            }
        }
        Log.e(TAG, "" + StaticDate.isMessage);
        main.updateView("通讯录中没有此人", FeedAdapter.FeedItem.TYPE_RECEIVE);
        //main.app.setTts("通讯录中没有此人");
        return false;
    }

}
