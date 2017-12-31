package com.asus.intellegent.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asus.intellegent.R;

public class AboutActivity extends BaseSettingActivity {

    private TextView clause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.setLeftText("关于",null);
        initView();

        //创建一个 SpannableString对象     
        SpannableString sp = new SpannableString("智能语音服务条款");
        //设置超链接     
        sp.setSpan(new URLSpanNoUnderline("http://www.xfyun.cn/"), 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //SpannableString对象设置给TextView
        clause.setText(sp);
        //设置TextView可点击
        clause.setClickable(true);
        clause.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initView() {
        clause = findViewById(R.id.clause);
    }

    /**
     * Created by cpj on 2016/4/26.
     * 超链接无下划线
     */
    public class URLSpanNoUnderline extends URLSpan{

        public URLSpanNoUnderline(String url) {
            super(url);
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);//无下划线
            ds.setColor(Color.parseColor("#007dff"));//字体颜色
        }

    }

}
