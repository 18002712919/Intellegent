package com.asus.intellegent.ui.activity;

import android.view.View;

/**
 * Created by ASUS on 2017/10/15.
 */

public class BaseSettingActivity extends BaseActivity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        super.initState(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

}
