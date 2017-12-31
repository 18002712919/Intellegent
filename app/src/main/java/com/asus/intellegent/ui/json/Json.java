package com.asus.intellegent.ui.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.asus.intellegent.R;
import com.asus.intellegent.open.ContactInfo;

public class Json {

    private Context context;
    private ContactInfo contactInfo1, contactInfo2;
    private List<ContactInfo> contactLists;
    private final static String fileName = "Json.json";

    public Json(Context context, List<ContactInfo> contactLists) {
        this.context = context;
        this.contactLists = contactLists;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    private ContactInfo getInfo() {
        ContactInfo contactInfo = null;
        if (!contactLists.isEmpty()) {
            int temp = (int) (Math.random() * contactLists.size());
            contactInfo = contactLists.get(temp);
        }
        return contactInfo;
    }

    private int getRes(int position) {

        final int[] res = {
                R.mipmap.logo_phone,
                R.mipmap.logo_translation,
                R.mipmap.logo_alarm_clock,
                R.mipmap.logo_calendar,
                R.mipmap.logo_memorandum,
                R.mipmap.logo_didi,
                R.mipmap.logo_sina,
                R.mipmap.logo_life,
                R.mipmap.logo_short_message,
                R.mipmap.logo_bus,
                R.mipmap.logo_set_up,
                R.mipmap.logo_music,
                R.mipmap.logo_reastaurant,
                R.mipmap.logo_encyclopedia,
                R.mipmap.logo_menu,
                R.mipmap.logo_gaode,
                R.mipmap.logo_weather,
                R.mipmap.logo_application_store,
                R.mipmap.logo_map,
                R.mipmap.logo_search,
                R.mipmap.logo_browser,
                R.mipmap.logo_stock,
                R.mipmap.logo_help
        };
        return res[position];
    }

    /**
     * 将JSON字符串转化为Adapter数据
     *
     * @param
     * @throws JSONException
     */
    public JSONArray getJsonArray() throws JSONException {
        StringBuilder str = getJson();
        //Log.e("222222", "bbbbbbbbbbbbbbbb");
        JSONObject jsonObject = new JSONObject(str.toString());
        //Log.v(str+"", str.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        //Log.e("1111111111", "aaaaaaaaaa");
        return jsonArray;
    }

    public List<Map<String, Object>> listViewData() throws JSONException {
        List<Map<String, Object>> data = new ArrayList<>();
        JSONArray jsonArray = getJsonArray();
        int len = jsonArray.length();
        Map<String, Object> map[] = new HashMap[len];
        //Log.i(len+"", jsonArray+"");
        for (int i = 0; i < len; i++) {
            map[i] = new HashMap<>();
            JSONObject obj = jsonArray.getJSONObject(i);
            Object title = obj.get("title");
            String text = (String) obj.getJSONArray("text").get(0);
            if (i == 0) {
                contactInfo1 = getInfo();
                if (contactInfo1 != null) {
                    text = "打电话给" + contactInfo1.getName();
                }
            } else if (i == 8) {
                contactInfo2 = getInfo();
                if (contactInfo2 != null) {
                    text = "发短信给" + contactInfo2.getName();
                }
            } else {
                JSONArray array = obj.getJSONArray("text");
                text = array.getString(0);
            }
            map[i].put("ivUserAvatar", getRes(i));
            map[i].put("tvComment", title);
            map[i].put("tvprompt", text);
            data.add(map[i]);
        }
        return data;
    }


    public List<String> commandList(int position) throws JSONException {
        List<String> list = new ArrayList<>();
        //Log.e("33333333333", "cccccccccccc");
        JSONArray jsonArray = getJsonArray();
        JSONObject obj = jsonArray.getJSONObject(position);
        JSONArray array = obj.getJSONArray("text");
        //Log.e("555555555", "eeeeeeeeee");
         {
            for (int i = 0; i < array.length(); i++) {
                //Log.e("666666666", "fffffffff");
                String str = array.getString(i);
                //Log.e(str+"", str.toString()+"rrrrrrrrrr");
                list.add(" " + (i + 1) + "  " + "“" + str + "”");
            }
        }
        if (position == 0) {
            if (contactInfo1 != null) {
                String name = contactInfo1.getName();
                String number = contactInfo1.getNumber();
                list.add(" " + 1 + "  “打电话给" + name + "”");
                list.add(" " + 2 + "  “呼叫" + name + "”");
                list.add(" " + 3 + "  “给" + name + "打电话”");
                list.add(" " + 4 + "  “给 " + number + " 打电话”");
                list.add(" " + 5 + "  “" + name + "”");
            }
        } else if (position == 8) {
            if (contactInfo2 != null) {
                String name = contactInfo2.getName();
                String number = contactInfo2.getNumber();
                list.add(" " + 1 + "  ”发短信给" + name + "”");
                list.add(" " + 2 + "  “给" + name + "发短信”");
                list.add(" " + 3 + "  “给 " + number + " 发短信”");
                list.add(" " + 4 + "  “发短信”");
            }
        }
        return list;
    }
}
