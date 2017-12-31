package com.asus.intellegent.ui.view.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asus.intellegent.R;
import com.library.widget.SwitchButton;

/**
 * 线性设置View * Created by ASUS on 2017/8/29.
 */

public class LineSettingView extends LinearLayout {
    //主题
    private String subject;
    //标题
    private String title;
    //副标题
    private String titles;
    //标题对应的内容
    private String content;
    private boolean canNav;
    //是否用此布局
    private boolean isLayout;
    //是否有副标题
    private boolean is_title;
    //是否有内容
    private boolean isLinear;
    //是否位于此主题下的最后一行
    private boolean isBottom;
    //控件height
    private int layout_height;

    private TextView tv_title;
    private TextView tv_titles;
    private TextView tv_title1;
    private TextView tv_titles1;
    private TextView tv_content;
    private SwitchButton switch_nav;

    public LineSettingView(Context context,
                           boolean is_title,boolean canNav,boolean isLinear,boolean isLayout,int layout_height) {
        super(context);
        this.is_title = is_title;
        this.canNav = canNav;
        this.isLinear = isLinear;
        this.isLayout = isLayout;
        this.layout_height =layout_height;
    }
    public LineSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_line_setting, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineSettingView, 0, 0);
        try {
            subject = ta.getString(R.styleable.LineSettingView_subject);    //主题
            title = ta.getString(R.styleable.LineSettingView_title);        //标题
            titles = ta.getString(R.styleable.LineSettingView_titles);      //副标题
            content = ta.getString(R.styleable.LineSettingView_content);    //内容
            is_title = ta.getBoolean(R.styleable.LineSettingView_is_title, false);  //是否有副标题
            canNav = ta.getBoolean(R.styleable.LineSettingView_canNav, false);      //是否有开关
            isLayout = ta.getBoolean(R.styleable.LineSettingView_isLayout, false);  //是否用此布局
            isLinear = ta.getBoolean(R.styleable.LineSettingView_isLinear, false);  //是否有内容
            isBottom = ta.getBoolean(R.styleable.LineSettingView_isBottom, false);  //是否是列表中最后一个
            layout_height = ta.getInteger(R.styleable.LineSettingView_layout_height, 52);
            initView();
        } finally {
            ta.recycle();
        }
    }

    private void initView() {
        TextView tv_subject = findViewById(R.id.tv_subject);
        tv_title =  findViewById(R.id.tv_title);
        tv_titles =  findViewById(R.id.tv_titles);
        tv_title1 =  findViewById(R.id.tv_title1);
        tv_titles1 =  findViewById(R.id.tv_titles1);
        tv_content = findViewById(R.id.tv_content);
        LinearLayout tv_linear = findViewById(R.id.tv_linear);
        LinearLayout layout = findViewById(R.id.layout);
        LinearLayout layout1 = findViewById(R.id.layout1);
        switch_nav = findViewById(R.id.switch_nav);
        View view_bottomLine = findViewById(R.id.view_bottomLine);
        if (subject == null || subject.length() == 0) {
            tv_subject.setVisibility(GONE);
        } else {
            tv_subject.setText(subject);
        }
        tv_title.setText(title);
        tv_titles.setText(titles);
        tv_title1.setText(title);
        tv_titles1.setText(titles);
        tv_content.setText(content);
        tv_titles.setVisibility(is_title ? VISIBLE : GONE);
        tv_titles1.setVisibility(is_title ? VISIBLE : GONE);
        switch_nav.setVisibility(canNav ? VISIBLE : INVISIBLE);
        tv_linear.setVisibility(isLinear ? VISIBLE : INVISIBLE);
        view_bottomLine.setVisibility(isBottom ? VISIBLE : GONE);
        if (!isLayout){
            layout.setVisibility(VISIBLE);
            layout1.setVisibility(GONE);
            layout.setMinimumHeight(layout_height);
        }else {
            layout.setVisibility(GONE);
            layout1.setVisibility(VISIBLE);
            layout1.setMinimumHeight(layout_height);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
    }

    public void setTitles(String titles) {
        this.titles = titles;
        tv_titles.setText(titles);
    }

    public String getTitle() {
        return title;
    }

    public SwitchButton getSwitch_nav() {
        return switch_nav;
    }

    public void setContent(String content) {
        this.content = content;
        tv_content.setText(content);
    }

    public String getContent() {
        return content;
    }
}
