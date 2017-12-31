package com.asus.intellegent.voice;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.call.JsonParser;
import com.asus.intellegent.open.Application;
import com.asus.intellegent.open.Open;
import com.asus.intellegent.open.Phone;
import com.asus.intellegent.open.SendMessage;
import com.asus.intellegent.understandjson.TextJson;
import com.asus.intellegent.open.Understander;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler {

    private BaseDrawerActivity baseDrawerActivity;
    private int results;
    public TextJson textJson;
    public Handler handler;
    private static String TAG = MessageHandler.class.getSimpleName();


    public MessageHandler(Context context) {
        baseDrawerActivity = (BaseDrawerActivity) context;
    }

    public void is(String resultString) {
        switch (results) {
            case StaticDate.APPLICATION:
                baseDrawerActivity.updateView("你要打开什么应用！", FeedAdapter.FeedItem.TYPE_RECEIVE);
                //baseDrawerActivity.app.setTts("你要打开什么应用！");
                results = 0;
                return;
            case StaticDate.PHONE:
                baseDrawerActivity.updateView("你想打给谁！", FeedAdapter.FeedItem.TYPE_RECEIVE);
                //baseDrawerActivity.app.setTts("你想打给谁！");
                results = 0;
                return;
            case StaticDate.SENDMESSAGE:
                baseDrawerActivity.updateView("你给谁发短信！", FeedAdapter.FeedItem.TYPE_RECEIVE);
                //baseDrawerActivity.app.setTts("你给谁发短信！");
                results = 0;
                return;
        }
        int temp;
        textJson = JsonParser.jsRusultT(resultString);
        String service = textJson.getService();
        Log.e(TAG, "textJson.getService()= " + service);
        if ("app".equals(service) || "website".equals(service)) {
            temp = StaticDate.APPLICATION;
        } else if ("telephone".equals(service)) {
            temp = StaticDate.PHONE;
        } else if ("message".equals(service)) {
            temp = StaticDate.SENDMESSAGE;
        } else {
            temp = StaticDate.TEXTUNDER;
        }
        judge(temp, resultString);
    }

    private int judge(int falg, String resultString) {
        Open open = null;
        handler = null;
        switch (falg) {
            case StaticDate.APPLICATION:
                open = new Application(baseDrawerActivity, this);
                break;
            case StaticDate.PHONE:
                open = new Phone(baseDrawerActivity, this);
                break;
            case StaticDate.SENDMESSAGE:
                open = new SendMessage(baseDrawerActivity, this);
                break;
            case StaticDate.TEXTUNDER:
                Log.e(TAG, "1111111111");
                open = new Understander(baseDrawerActivity, this);
                break;
        }
        if (open != null) {
            return open.open(resultString);
        } else {
            return 0;
        }

    }

    private void start() {
        baseDrawerActivity.isIat();
    }

    public void onResult(String results) {
        if (StaticDate.isMessage) {
            Pattern pattern = Pattern.compile("^(打开|打电话|发短信)[.,!?;:。，！？；：、]*$");
            Matcher matcher = pattern.matcher(results);
            if (matcher.matches()) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // TODO Auto-generated method stub
                        super.handleMessage(msg);
                        start();
                    }
                };
                if (results.startsWith("打开")) {
                    this.results = StaticDate.APPLICATION;
                } else if (results.startsWith("打电话")) {
                    this.results = StaticDate.PHONE;
                } else if (results.startsWith("发短信")) {
                    this.results = StaticDate.SENDMESSAGE;
                }
                baseDrawerActivity.updateView(results, FeedAdapter.FeedItem.TYPE_SEND);
                is(results);
            } else {
                baseDrawerActivity.getSoundPool().play(baseDrawerActivity.getSoundProcessing(),
                        0.7f, 0.7f, 0, 0, 1);
                baseDrawerActivity.updateView(results, FeedAdapter.FeedItem.TYPE_SEND);
                //开始语义理解
                baseDrawerActivity.getApp().setTextUnderstander(results);
            }
            Log.e(TAG + "  " + matcher.matches(), results);
        } else {
            Log.e(TAG, "1111111111111" + StaticDate.isMessage);
            SendMessage.sendMessage(results);
        }
    }

    public void isHandler() {

    }

    /**
     * @param checkType 校验类型：0校验手机号码，1校验座机号码，2两者都校验满足其一就可
     * @param phoneNum
     */
    public String validPhoneNum(int checkType, String phoneNum) {
        Pattern p1;
        Pattern p2;
        Matcher m;
        StringBuffer ret = null;
        p1 = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$");
        p2 = Pattern.compile("^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$");
        switch (checkType) {
            case 0:
                if (phoneNum.length() == 11) {
                    m = p1.matcher(phoneNum);
                    if (m.matches()) {
                        ret = new StringBuffer();
                        ret.append(phoneNum.substring(0, 3));
                        ret.append(" ");
                        ret.append(phoneNum.substring(3, 7));
                        ret.append(" ");
                        ret.append(phoneNum.substring(7));
                    }
                }
                break;
            case 1:
                if (phoneNum.length() == 8) {
                    m = p2.matcher(phoneNum);
                    if (m.matches()) {
                        ret = new StringBuffer();
                        ret.append(phoneNum.substring(0, 4));
                        ret.append(" ");
                        ret.append(phoneNum.substring(4));
                        Log.e("2222222222", "444444444  " + ret);
                    }
                }
                break;
            case 2:
                if ((phoneNum.length() == 11 && p1.matcher(phoneNum).matches())) {
                    ret = new StringBuffer();
                    ret.append(phoneNum.substring(0, 3));
                    ret.append(" ");
                    ret.append(phoneNum.substring(3, 7));
                    ret.append(" ");
                    ret.append(phoneNum.substring(7));
                } else if (phoneNum.length() == 8 && p2.matcher(phoneNum).matches()) {
                    ret = new StringBuffer();
                    ret.append(phoneNum.substring(0, 4));
                    ret.append(" ");
                    ret.append(phoneNum.substring(4));
                }
                break;

        }
        if (ret != null) {
            return ret.toString();
        } else {
            return null;
        }
    }

}
