package com.asus.intellegent.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.view.setting.LineSettingView;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.voice.StaticDate;
import com.library.widget.SwitchButton;

public class AwakenActivity extends BaseSettingActivity implements View.OnClickListener {

    private LineSettingView awaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awaken);
        super.setLeftText("语音唤醒",null);
        initView();
    }

    private void initView() {
        awaken = findViewById(R.id.awaken);
        awaken.setOnClickListener(this);

        //awaken.setClickable(true);  //设置点击一次
        awaken.setEnabled(false);    //是否可点击
        awaken.getSwitch_nav().setEnabled(false);
        awaken.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e("11  " + on, "2222222");
                if (on) {
                    session.putString(StaticDate.AWAKEN, "开启");
                }else {
                    session.putString(StaticDate.AWAKEN, "关闭");
                }
            }
        });
    }

    @Override
    protected boolean setSetting() {
        boolean a = false;
        String string = session.getString(StaticDate.AWAKEN, "关闭");
        if ("开启".contains(string)){
            a = true;
        }
        awaken.getSwitch_nav().setDefaultOn(a);
        awaken.getSwitch_nav().isToggle();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.awaken:
                awaken.getSwitch_nav().toggle();
                break;
        }
    }

}
