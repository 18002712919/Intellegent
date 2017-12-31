package com.asus.intellegent.call;

import android.util.Log;

import com.asus.intellegent.understandjson.AnswerJson;
import com.asus.intellegent.understandjson.TextJson;
import com.asus.intellegent.understandjson.UnderstandResultJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Json结果解析类
 */
public class JsonParser {

    /**
     * 听写结果的Json格式解析
     *
     * @param json
     * @return
     */
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
//				JSONObject obj = items.getJSONObject(0);
//				ret.append(obj.getString("w"));
//				如果需要多候选结果，解析数组其他字段
                for (int j = 0; j < items.length(); j++) {
                    JSONObject obj = items.getJSONObject(j);
                    ret.append(obj.getString("w"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 识别结果的Json格式解析
     *
     * @param json
     * @return
     */
    public static String parseGrammarResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject obj = items.getJSONObject(j);
                    if (obj.getString("w").contains("nomatch")) {
                        ret.append("没有匹配结果.");
                        return ret.toString();
                    }
                    ret.append("【结果】" + obj.getString("w"));
                    ret.append("【置信度】" + obj.getInt("sc"));
                    ret.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.append("没有匹配结果.");
        }
        return ret.toString();
    }

    public static String parseLocalGrammarResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject obj = items.getJSONObject(j);
                    if (obj.getString("w").contains("nomatch")) {
                        ret.append("没有匹配结果.");
                        return ret.toString();
                    }
                    ret.append("【结果】" + obj.getString("w"));
                    ret.append("\n");
                }
            }
            ret.append("【置信度】" + joResult.optInt("sc"));

        } catch (Exception e) {
            e.printStackTrace();
            ret.append("没有匹配结果.");
        }
        return ret.toString();
    }

    /**
     * 语义结果的Json格式解析
     *
     * @param json
     * @return
     */
    public static String parseUnderstandResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONObject answer = joResult.getJSONObject("answer");
            String text = answer.getString("text");
            ret.append(text);
            /*
            ret.append("【应答码】" + joResult.getString("rc") + "\n");
			ret.append("【转写结果】" + joResult.getString("text") + "\n");
			ret.append("【服务名称】" + joResult.getString("service") + "\n");
			ret.append("【操作名称】" + joResult.getString("operation") + "\n");
			ret.append("【完整结果】" + com.intellegent.json);
			*/
        } catch (Exception e) {
            e.printStackTrace();
            ret.append("没有匹配结果.");
        }
        return ret.toString();
    }

    /**
     * 获得Json中的进行匹配信息
     *
     * @param json
     */

    public static TextJson jsRusultT(String json) {
        TextJson textJson = new TextJson();
        TextJson.SemanticBean semanticBean = new TextJson.SemanticBean();
        //TextJson.MoreResultsBean moreResultsBean = new TextJson.MoreResultsBean();
        TextJson.SemanticBean.SlotsBean slotsBean = new TextJson.SemanticBean.SlotsBean();

        List<TextJson.MoreResultsBean> moreResults = null;
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            if (!joResult.isNull("semantic")) {
                JSONObject slots = joResult.getJSONObject("semantic").
                        getJSONObject("slots");
                if (!slots.isNull("name")) {
                    slotsBean.setName(slots.getString("name"));
                }
                if (!slots.isNull("code")) {
                    slotsBean.setCode(slots.getString("code"));
                }
                if (!slots.isNull("url")){
                    slotsBean.setUrl(slots.getString("url"));
                }
            }
            textJson.setOperation(joResult.getString("operation"));
            textJson.setService(joResult.getString("service"));
            textJson.setText(joResult.getString("text"));

            /*if (!joResult.isNull("moreResults")) {
                moreResults = new ArrayList<>();
                JSONArray array = joResult.getJSONArray("moreResults");
                JSONObject jsonObject = null;
                for (int i = 0; i < array.length(); i++) {
                    TextJson.MoreResultsBean.SemanticBeanX semanticBeanX
                            = new TextJson.MoreResultsBean.SemanticBeanX();
                    TextJson.MoreResultsBean.SemanticBeanX.SlotsBeanX slotsBeanX
                            = new TextJson.MoreResultsBean.SemanticBeanX.SlotsBeanX();
                    jsonObject = array.getJSONObject(i);
                    JSONObject slotsM = jsonObject.getJSONObject("semantic").
                            getJSONObject("slots");
                    slotsBeanX.setName(slotsM.getString("name"));
                    slotsBeanX.setUrl(slotsM.getString("url"));
                    semanticBeanX.setSlots(slotsBeanX);
                    moreResultsBean.setSemantic(semanticBeanX);
                    moreResults.add(moreResultsBean);
                }
                moreResultsBean.setText(jsonObject.getString("text"));
                moreResultsBean.setOperation(jsonObject.getString("operation"));
                moreResultsBean.setService(jsonObject.getString("service"));
            }*/
            semanticBean.setSlots(slotsBean);
            textJson.setMoreResults(moreResults);
            textJson.setSemantic(semanticBean);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TextJson   json解析失败！！！",""+json);
        }
        return textJson;
    }

    public static AnswerJson jsRusultA(String json) {
        AnswerJson answerJson = new AnswerJson();
        AnswerJson.AnswerBean answerBean = new AnswerJson.AnswerBean();
        AnswerJson.MoreResultsBean moreResultsBean = new AnswerJson.MoreResultsBean();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            answerJson.setOperation(joResult.getString("operation"));
            answerJson.setService(joResult.getString("service"));
            answerJson.setText(joResult.getString("text"));
            answerBean.setText(joResult.getJSONObject("answer").getString("text"));
            answerJson.setAnswer(answerBean);

            if (!joResult.isNull("moreResults")) {
                List<AnswerJson.MoreResultsBean> moreResults = new ArrayList<>();
                JSONArray array = joResult.getJSONArray("moreResults");
                JSONObject jsonObject = null;
                for (int i = 0; i < array.length(); i++) {
                    AnswerJson.MoreResultsBean.AnswerBeanX answerBeanX
                            = new AnswerJson.MoreResultsBean.AnswerBeanX();
                    jsonObject = array.getJSONObject(i);
                    moreResultsBean.setText(jsonObject.getString("text"));
                    moreResultsBean.setOperation(jsonObject.getString("operation"));
                    moreResultsBean.setService(jsonObject.getString("service"));
                    answerBeanX.setText(jsonObject.getJSONObject("answer").getString("text"));
                    moreResultsBean.setAnswer(answerBeanX);
                    moreResults.add(moreResultsBean);
                }
                answerJson.setMoreResults(moreResults);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("AnswerJson  json解析失败！！！",""+json);
        }
        return answerJson;
    }

    public static UnderstandResultJson jsRusultU(String json) {
        UnderstandResultJson understandResult = new UnderstandResultJson();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            String text = null;
            Log.e("444" + joResult.isNull("answer"), "555" + joResult.isNull("data"));
            /*if (!joResult.isNull("answer")) {
                JSONObject answer = joResult.getJSONObject("answer");
                text = answer.getString("text");
                Log.e("99999999999", text);
            }*/
            if (!joResult.isNull("data")) {
                JSONArray result = joResult.getJSONObject("data").
                        getJSONArray("result");
                UnderstandResultJson.DataBean dataBean = new UnderstandResultJson.DataBean();
                List<UnderstandResultJson.DataBean.ResultBean> list = new ArrayList<>();
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.getJSONObject(i);
                    UnderstandResultJson.DataBean.ResultBean resultBean
                            = new UnderstandResultJson.DataBean.ResultBean();
                    if (i == 0) {
                        resultBean.setAirQuality(object.getString("airQuality"));
                        resultBean.setHumidity(object.getString("humidity"));
                        resultBean.setPm25(object.getString("pm25"));
                    }
                    resultBean.setSourceName(object.getString("sourceName"));
                    resultBean.setDate(object.getString("date"));
                    resultBean.setLastUpdateTime(object.getString("lastUpdateTime"));
                    resultBean.setCity(object.getString("city"));
                    resultBean.setWindLevel(object.getInt("windLevel"));
                    resultBean.setWeather(object.getString("weather"));
                    resultBean.setTempRange(object.getString("tempRange"));
                    resultBean.setWind(object.getString("wind"));
                    list.add(resultBean);
                }
                dataBean.setResult(list);
                understandResult.setData(dataBean);
            }

            if (!joResult.isNull("webPage")) {
                UnderstandResultJson.WebPageBean webPageBean = new UnderstandResultJson.WebPageBean();
                webPageBean.setUrl(joResult.getJSONObject("webPage").
                        getString("url"));
                understandResult.setWebPage(webPageBean);
                Log.e("33333", joResult.getJSONObject("webPage").
                        getString("url"));
            }
            Log.e("444" + joResult.isNull("webPage"),
                    "555" + joResult.isNull("moreResults"));

            if (!joResult.isNull("moreResults")) {
                List<UnderstandResultJson.MoreResultsBean> moreResults = new ArrayList<>();
                JSONArray array = joResult.getJSONArray("moreResults");
                for (int a = 0; a < array.length(); a++) {
                    UnderstandResultJson.MoreResultsBean moreResultsBean = new UnderstandResultJson.MoreResultsBean();
                    UnderstandResultJson.MoreResultsBean.AnswerBean answerBean = new UnderstandResultJson.MoreResultsBean.AnswerBean();
                    JSONObject object = array.getJSONObject(a);
                    if (!object.isNull("answer")) {
                        JSONObject answer = object.getJSONObject("answer");
                        answerBean.setText(answer.getString("text"));
                        moreResultsBean.setAnswer(answerBean);
                        moreResults.add(moreResultsBean);
                        moreResultsBean.setService(object.getString("service"));
                        moreResultsBean.setOperation(object.getString("operation"));
                    }
                    understandResult.setMoreResults(moreResults);
                }
            }
            understandResult.setText(joResult.getString("text"));
            understandResult.setService(joResult.getString("service"));
            understandResult.setOperation(joResult.getString("operation"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("UnderstandResultJson", " json解析失败！！！ :" + json);
        }
        return understandResult;
    }

}
