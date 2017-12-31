package com.asus.intellegent.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2017/7/4.
 */

public abstract class Base extends BaseAdapter {


    protected Context context;
    protected List<Map<String,Object>> list;
    protected LayoutInflater inflater;      //用于将布局文件转化为视图

    public Base(Context context, List<Map<String,Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        ImageView icon;
        TextView title;
        TextView prompt;
        TextView informant;
        TextView timbre;
        TextView voice;
        TextView timeTextView;
        TextView contentTextView;
        RadioButton radioButton;
    }
}
