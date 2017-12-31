package com.asus.intellegent.open;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.ui.activity.MainActivity;
import com.asus.intellegent.understandjson.TextJson;
import com.asus.intellegent.voice.MessageHandler;
import com.asus.intellegent.voice.StaticDate;

import java.util.List;

/**
 * Created by ASUS on 2017/6/13.
 */

public class Phone implements Open {

    private MainActivity main;
    private MessageHandler handler;
    private List<ContactInfo> contactLists;
    private Intent intent;
    private String contacts = null;

    private static String TAG = Phone.class.getSimpleName();

    public Phone(Context context, MessageHandler handler) {
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
            Log.d("Phone", contactLists.toString());
            main.updateView("通讯录为空！", FeedAdapter.FeedItem.TYPE_RECEIVE);
            //main.app.setTts("通讯录为空！");
            return 0;
        }
        TextJson.SemanticBean.SlotsBean slots = handler.textJson.getSemantic().getSlots();
        Log.e(TAG, "111111111");
        return call(slots);
    }

    /**
     * 打电话
     */
    private int call(TextJson.SemanticBean.SlotsBean slots) {
        int flag = 0;
        intent = new Intent();
        String name = slots.getName();
        String code = slots.getCode();
        String number = null;
        Log.e("333333333", "222222222" + name);
        for (ContactInfo contactInfo : contactLists) {
            if (name != null) {
                if (name.contains(contactInfo.getName())) {
                    contacts = contactInfo.getName();
                    number = contactInfo.getNumber();
                    intent.setData(Uri.parse("tel:" + number));
                    break;
                }
            } else {
                number = handler.validPhoneNum(2, code);
                if (number != null) {
                    contacts = getContactNameByPhoneNumber(main, number);
                    if (contacts == null) {
                        contacts = code;
                    }
                    intent.setData(Uri.parse("tel:" + code));
                }
                break;
            }
        }
        if (contacts != null) {
            main.updateView("已经为您拨通" + contacts + "的电话。",
                    FeedAdapter.FeedItem.TYPE_RECEIVE);
            //main.app.setTts("已经为您拨通" + contacts + "的电话。");
            intent.setAction("android.intent.action.CALL");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            //main.startActivity(intent);
            flag = StaticDate.PHONE;
        } else {
            if (name != null) {
                main.updateView("通讯录中没有此人！",
                        FeedAdapter.FeedItem.TYPE_RECEIVE);
                //main.app.setTts("通讯录中没有此人！");
            } else {
                if (number != null) {
                    main.updateView("无法拨通此人的电话！", FeedAdapter.FeedItem.TYPE_RECEIVE);
                    //main.app.setTts("无法拨通此人的电话！");
                } else {
                    main.updateView("此电话号码不正确！", FeedAdapter.FeedItem.TYPE_RECEIVE);
                    //main.app.setTts("此电话号码不正确！");
                }
            }
        }
        Log.e("contacts = " + contacts + ", name=" + name, "number=" + number + ", code=" + code);
        return flag;
    }

    private void start() {
        if (contacts != null) {
            main.startActivity(intent);
        }
    }

    /**
     * 根据电话号码取得联系人姓名
     *
     * @param context
     * @param address
     * @return
     */
    private String getContactNameByPhoneNumber(Context context, String address) {
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 将自己添加到 msPeers 中
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, // Which columns to return.
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
                        + address + "'", // WHERE clause.
                null, // WHERE clause value substitution
                null); // Sort order.

        if (cursor == null) {
            Log.d("44444444", "getPeople null");
            return null;
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            // 取得联系人名字
            int nameFieldColumnIndex = cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String name = cursor.getString(nameFieldColumnIndex);
            return name;
        }
        return null;
    }
}
