package com.asus.intellegent.voice;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.activity.AboutActivity;
import com.asus.intellegent.ui.activity.AppManager;
import com.asus.intellegent.ui.activity.AwakenActivity;
import com.asus.intellegent.ui.activity.RobtainActivity;
import com.asus.intellegent.ui.activity.SettingActivity;

/**
 * Created by ASUS on 2017/8/30.
 */

public class OnListener implements View.OnClickListener {

    private Context context;
    private SettingActivity settingActivity;

    public OnListener(Context context) {
        this.context = context;
        this.settingActivity = (SettingActivity) context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.announcements_Setting:
                settingActivity.onView(StaticDate.ANNOUNCEMENTS_SETTING);
                break;
            case R.id.broadcast_Characters_Setting:
                Intent intent = new Intent(context, RobtainActivity.class);
                context.startActivity(intent);
                settingActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
                break;
            case R.id.dictation_dialog_box_Setting:
                settingActivity.onView(StaticDate.DICTATION_DIALOG_BOX_SETTING);
                break;
            case R.id.awaken_Setting:
                Intent intent1 = new Intent(context, AwakenActivity.class);
                context.startActivity(intent1);
                settingActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
                break;
            case R.id.welcome_Setting:
                settingActivity.onView(StaticDate.WELCOME_SETTING);
                break;
            case  R.id.upload_contacts__Setting:
                settingActivity.onView(StaticDate.UPLOAD_CONTACTS__SETTING);
                break;
            case R.id.geographical_position_Setting:
                settingActivity.onView(StaticDate.GEOGRAPHICAL_POSITION_SETTING);
                break;
            case R.id.about_Setting:
                Intent intent2 = new Intent(context, AboutActivity.class);
                context.startActivity(intent2);
                settingActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
                break;
        }
    }


}
