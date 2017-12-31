package com.asus.intellegent.call;

import android.content.Context;
import android.util.Log;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

public class SpeechTextUnderStander implements TextUnderstanderListener {

    private final String TAG = "文本理解";

    private BaseDrawerActivity baseDrawerActivity;

    public SpeechTextUnderStander(Context context) {
        baseDrawerActivity = (BaseDrawerActivity) context;
    }

    //语义结果回调
    @Override
    public void onResult(UnderstanderResult result) {
        Log.e(TAG, "语义结果回调:  " + result.getResultString());
        baseDrawerActivity.mHandler.is(result.getResultString());
    }

    //语义错误回调
    @Override
    public void onError(SpeechError error) {
        Log.e(TAG,"语义错误回调");
    }

}
