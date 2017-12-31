package com.asus.intellegent.call;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SpeechRecoListener implements RecognizerListener {

    private Toast mToast;
    private BaseDrawerActivity baseDrawerActivity;
    private static HashMap<String, String> mIatResults;    // 用HashMap存储听写结果
    private static String TAG = SpeechRecoListener.class.getSimpleName();


    @SuppressLint("ShowToast")
    public SpeechRecoListener(Context context) {
        baseDrawerActivity = (BaseDrawerActivity) context;
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mIatResults = new LinkedHashMap<>();
    }

    //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
    //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
    //关于解析Json的代码可参见MscDemo中JsonParser类；
    //isLast等于true时会话结束。
    @Override
    public void onBeginOfSpeech() {
        // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        showTip("开始说话");
    }

    @Override
    public void onError(SpeechError error) {
        // Tips：
        // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
        // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
        showTip(error.getPlainDescription(true));
        baseDrawerActivity.updateView("您没有说话,试试帮助中的说法吧！", FeedAdapter.FeedItem.TYPE_RECEIVE);
        //baseDrawerActivity.app.setTts("您没有说话,试试帮助中的说法吧！");
    }

    @Override
    public void onEndOfSpeech() {
        // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
        showTip("结束说话");
    }

    @Override
    public void onResult(RecognizerResult results, boolean isLast) {
        Log.e("讯飞语音结果", results.getResultString());
        String string = printResult(results);
        if (isLast) {
            // TODO 最后的结果
            baseDrawerActivity.mHandler.onResult(string);

        }
    }

    protected static String printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        return resultBuffer.toString();
    }

    @Override
    public void onVolumeChanged(int volume, byte[] data) {
        showTip("当前正在说话，音量大小：" + volume);
        Log.d("讯飞", "返回音频数据："+data.length);
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
        //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
        //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
        //		Log.d(TAG, "session id =" + sid);
        //	}
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
