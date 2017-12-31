package com.asus.intellegent.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.view.iosdialog.IOSDialogFragment;
import com.asus.intellegent.ui.view.setting.LineSettingView;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.voice.OnListener;
import com.asus.intellegent.voice.StaticDate;
import com.library.widget.SwitchButton;

public class SettingActivity extends BaseSettingActivity {

    private LineSettingView announcements_Setting;
    private LineSettingView broadcast_Characters_Setting;
    private LineSettingView dictation_dialog_box_Setting;
    private LineSettingView awaken_Setting;
    private LineSettingView welcome_Setting;
    private LineSettingView upload_contacts__Setting;
    private LineSettingView geographical_position_Setting;
    private LineSettingView about_Setting;
    private String[] items = {"确定"};
    private static final String DIALOG_TAG = "buttom_dialog";
    private static final String TAG = SettingActivity.class.getCanonicalName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        super.setLeftText("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().getActivity(SettingActivity.this);
                SettingActivity.super.overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
        initView();
    }

    @Override
    protected boolean setSetting() {
        anInt = 1;
        Session session = new Session(this);
        announcements_Setting.getSwitch_nav().setDefaultOn(
                session.getBoolean(StaticDate.ANNOUNCEMENTS_SETTING, true));
        dictation_dialog_box_Setting.getSwitch_nav().setDefaultOn(
                session.getBoolean(StaticDate.DICTATION_DIALOG_BOX_SETTING, false));
        welcome_Setting.getSwitch_nav().setDefaultOn(
                session.getBoolean(StaticDate.WELCOME_SETTING, false));
        Log.e(TAG,session.getBoolean(StaticDate.WELCOME_SETTING, false)+"");
        awaken_Setting.setContent(session.getString(StaticDate.AWAKEN, "关闭"));
        upload_contacts__Setting.getSwitch_nav().setDefaultOn(
                session.getBoolean(StaticDate.UPLOAD_CONTACTS__SETTING, false));
        geographical_position_Setting.getSwitch_nav().setDefaultOn(
                session.getBoolean(StaticDate.GEOGRAPHICAL_POSITION_SETTING, true));
        announcements_Setting.getSwitch_nav().isToggle();
        dictation_dialog_box_Setting.getSwitch_nav().isToggle();
        welcome_Setting.getSwitch_nav().isToggle();
        upload_contacts__Setting.getSwitch_nav().isToggle();
        geographical_position_Setting.getSwitch_nav().isToggle();
        broadcast_Characters_Setting.setContent(session.getString(StaticDate.BROADCAST_CHARACTERS, "小燕"));
        return true;
    }

    private void initView() {
        OnListener onListener = new OnListener(this);
        announcements_Setting = findViewById(R.id.announcements_Setting);
        broadcast_Characters_Setting = findViewById(R.id.broadcast_Characters_Setting);
        dictation_dialog_box_Setting = findViewById(R.id.dictation_dialog_box_Setting);
        awaken_Setting = findViewById(R.id.awaken_Setting);
        welcome_Setting = findViewById(R.id.welcome_Setting);
        upload_contacts__Setting = findViewById(R.id.upload_contacts__Setting);
        geographical_position_Setting = findViewById(R.id.geographical_position_Setting);
        about_Setting = findViewById(R.id.about_Setting);

        final IOSDialogFragment fragment = IOSDialogFragment.newInstance(items, dialogListener);

        announcements_Setting.setOnClickListener(onListener);
        broadcast_Characters_Setting.setOnClickListener(onListener);
        dictation_dialog_box_Setting.setOnClickListener(onListener);
        awaken_Setting.setOnClickListener(onListener);
        welcome_Setting.setOnClickListener(onListener);
        upload_contacts__Setting.setOnClickListener(onListener);
        geographical_position_Setting.setOnClickListener(onListener);
        about_Setting.setOnClickListener(onListener);

        announcements_Setting.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e(TAG, "ANNOUNCEMENTS_SETTING=" + on);
                session.putBoolean(StaticDate.ANNOUNCEMENTS_SETTING, on);
                broadcast_Characters_Setting.setEnabled(on);
            }
        });
        dictation_dialog_box_Setting.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e(TAG, "DICTATION_DIALOG_BOX_SETTING=" + on);
                session.putBoolean(StaticDate.DICTATION_DIALOG_BOX_SETTING, on);
            }
        });
        welcome_Setting.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e(TAG, "WELCOME_SETTING=" + on);
                session.putBoolean(StaticDate.WELCOME_SETTING, on);
            }
        });
        upload_contacts__Setting.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e(TAG, "UPLOAD_CONTACTS__SETTING=" + on);
                session.putBoolean(StaticDate.UPLOAD_CONTACTS__SETTING, on);
                if (on && anInt != 1) {
                    fragment
                            .setTitle("允许语音助手使用您的本地联系人以提高识别准确度");
                    if (!fragment.isAdded()) {
                        fragment.show(getFragmentManager(), DIALOG_TAG);
                    }
                }
                anInt = 2;
            }
        });
        geographical_position_Setting.getSwitch_nav().setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.e(TAG,"GEOGRAPHICAL_POSITION_SETTING=" + on);
                session.putBoolean(StaticDate.GEOGRAPHICAL_POSITION_SETTING, on);
            }
        });
    }

    private IOSDialogFragment.ButtomDialogListener dialogListener = new IOSDialogFragment.ButtomDialogListener() {
        @Override
        public void onClick(int position) {
            //Toast.makeText(SettingActivity.this, items[position] + "被点击了。", Toast.LENGTH_SHORT).show();
            String item = items[position];
            switch (item){
                case "确定":
                    /*if (app != null) {
                        app.getMgr();
                        Log.e(" 1111",position+"");
                    }*/
            }
        }

        @Override
        public void onClick(View v) {
            onView(StaticDate.UPLOAD_CONTACTS__SETTING);
        }
    };

    private int anInt;

    public void onView(String str) {
        switch (str) {
            case StaticDate.ANNOUNCEMENTS_SETTING:
                announcements_Setting.getSwitch_nav().toggle();
                break;
            case StaticDate.DICTATION_DIALOG_BOX_SETTING:
                dictation_dialog_box_Setting.getSwitch_nav().toggle();
                break;
            case StaticDate.WELCOME_SETTING:
                welcome_Setting.getSwitch_nav().toggle();
                break;
            case StaticDate.UPLOAD_CONTACTS__SETTING:
                upload_contacts__Setting.getSwitch_nav().toggle();
                break;
            case StaticDate.GEOGRAPHICAL_POSITION_SETTING:
                geographical_position_Setting.getSwitch_nav().toggle();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().getActivity(SettingActivity.this);
            //第一个参数为进入的目标activity动画效果，第二个参数为退出的activity动画
            super.overridePendingTransition(R.anim.fade, R.anim.hold);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
