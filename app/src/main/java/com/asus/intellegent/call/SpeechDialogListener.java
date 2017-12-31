package com.asus.intellegent.call;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.ui.adapter.FeedAdapter;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;


/**
 * Created by ASUS on 2017/7/30.
 */

public class SpeechDialogListener implements RecognizerDialogListener {

    private Toast mToast;
    private BaseDrawerActivity baseDrawerActivity;
    private static String TAG = SpeechDialogListener.class.getSimpleName();

    @SuppressLint("ShowToast")
    public SpeechDialogListener(Context context) {
        baseDrawerActivity = (BaseDrawerActivity) context;
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public void onResult(RecognizerResult results, boolean isLast) {
        Log.e("讯飞语音结果", results.getResultString());
        String string = SpeechRecoListener.printResult(results);
        if (isLast) {
            // TODO 最后的结果
            baseDrawerActivity.mHandler.onResult(string);
        }
    }

    @Override
    public void onError(SpeechError speechError) {
        showTip(speechError.getPlainDescription(true));
        baseDrawerActivity.updateView("您没有说话,试试帮助中的说法吧！", FeedAdapter.FeedItem.TYPE_RECEIVE);
        //baseDrawerActivity.app.setTts("您没有说话,试试帮助中的说法吧！");
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
