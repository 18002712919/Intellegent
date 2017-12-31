package com.asus.intellegent.open;

import android.content.Context;
import android.util.Log;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.call.JsonParser;
import com.asus.intellegent.understandjson.AnswerJson;
import com.asus.intellegent.understandjson.UnderstandResultJson;
import com.asus.intellegent.understandjson.UnderstandResultJson.*;
import com.asus.intellegent.voice.MessageHandler;
import com.asus.intellegent.voice.StaticDate;

import java.util.List;

/**
 * Created by ASUS on 2017/7/16.
 */

public class Understander implements Open {

    private BaseDrawerActivity baseDrawerActivity;
    private MessageHandler handler;
    private static String TAG = Understander.class.getSimpleName();

    public Understander(Context context, MessageHandler handler) {
        this.baseDrawerActivity = (BaseDrawerActivity) context;
        this.handler = handler;
    }

    @Override
    public int open(String resultString) {
        Log.e(TAG, "1111111111");
        String string = understandr(resultString);
        baseDrawerActivity.updateView(string, FeedAdapter.FeedItem.TYPE_RECEIVE);
        //baseDrawerActivity.app.setTts(string);
        return StaticDate.TEXTUNDER;
    }

    private String understandr(String resultString) {
        StringBuffer ret = new StringBuffer();
        UnderstandResultJson understandResult = JsonParser.jsRusultU(resultString);
        Log.e(TAG, "33333333333"+understandResult.getService());
        Log.e(TAG+ret.length(),TAG+"  "+understandResult.getMoreResults());
        if(understandResult.getMoreResults() != null){
            if ("datetime".equals(understandResult.getMoreResults().get(0).getService())) {
                ret.append(understandResult.getMoreResults().get(0).getAnswer().getText());
            }
        }
        if ("weather".equals(understandResult.getService())) {
            List<DataBean.ResultBean> result = understandResult.getData().getResult();
            WebPageBean webPageBean = understandResult.getWebPage();
            DataBean.ResultBean resultBean = result.get(0);
            if (ret.length() != 0){Log.e(TAG,"ret.length()="+ret.length());
                ret.append("。\n");
            }
            ret.append(resultBean.getCity() + "的");
            ret.append("天气" + resultBean.getWeather());
            ret.append("，温度范围: " + resultBean.getTempRange());
            ret.append("，湿度: " + resultBean.getHumidity());
            ret.append("，空气质量: " + resultBean.getAirQuality());
            ret.append("，" + resultBean.getWind());
            if (!(resultBean.getWindLevel() == 0)) {
                ret.append("，等级: " + resultBean.getWindLevel());
            }
            ret.append("，pm2.5: " + resultBean.getPm25());
            ret.append("，更新时间: " + resultBean.getLastUpdateTime().substring(10));
            ret.append("，来源于: " + resultBean.getSourceName());
            //ret.append("\nURL: " + webPageBean.getUrl());
            Log.e(TAG, webPageBean.getUrl());
        } else {
            AnswerJson answerJson = JsonParser.jsRusultA(resultString);
            Log.e(TAG, "22222222222" + answerJson.getAnswer());
            if (answerJson.getAnswer() != null) {
                ret.append(answerJson.getAnswer().getText());
            } else {
                ret.append("请说出正确的语法!");
            }
        }
        return ret.toString();
    }

}