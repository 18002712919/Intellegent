package com.asus.intellegent.ui.json;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2017/7/4.
 */

public class Voice {
    private Context context;
    private List<Map<String, Object>> data;
    private final static String fileName = "voice.json";

    public Voice(Context context) {
        this.context = context;
        data = new ArrayList();
    }

    /**
     * 读取本地文件中JSON字符串
     *
     * @param
     * @return
     */
    private StringBuilder getJson() {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader buf;
        try {
            buf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(fileName)));
            String line;
            while ((line = buf.readLine()) != null) {
                stringBuilder.append(line);
            }
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringBuilder;
    }

    /**
     * 将JSON字符串转化为Adapter数据
     *
     * @param
     * @throws JSONException
     */
    private JSONObject[] getJsonobject() throws JSONException {
        StringBuilder str = getJson();
        JSONArray jsonArray = new JSONArray(str.toString());
        Log.v(str+"", str.toString());
        JSONObject[] jsonObject = new JSONObject[jsonArray.length()];
        for (int i=0; i<jsonArray.length(); i++){
            jsonObject[i] = jsonArray.getJSONObject(i);
        }
        return jsonObject;
    }

    public List<Map<String, Object>> voiecViewData() throws JSONException {
        JSONObject[] jsonObject = getJsonobject();
        int len = jsonObject.length;
        Map<String, Object> map[] = new HashMap[len];
        for (int i=0; i<len; i++) {
            map[i] = new HashMap<>();
            String informant = jsonObject[i].getString("informant");
            String timbre = jsonObject[i].getString("timbre");
            String voice = jsonObject[i].getString("voice");
            String voice_name = jsonObject[i].getString("voice_name");

            map[i].put("informant",informant);
            map[i].put("timbre",timbre);
            map[i].put("voice",voice);
            map[i].put("voice_name",voice_name);
            Log.e(i+": 发音人:"+informant+"  音色"+timbre,"语言"+voice+"  "+voice_name);
            data.add(map[i]);
        }
        Log.e("111111111","length"+data.size());
        return data;
    }
}
