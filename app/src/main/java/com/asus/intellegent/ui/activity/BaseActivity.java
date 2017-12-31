package com.asus.intellegent.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.asus.intellegent.R;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.util.LogcatHelper;
import com.library.widget.TitleBar;
import com.library.widget.StatusBarUtil;

import static com.asus.intellegent.ui.activity.Utils.hasKitKat;

/**
 * Created by ASUS on 2017/8/11.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Session session;
    private TitleBar titleBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        session = new Session(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogcatHelper.getInstance(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        session.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.commit();
        session.clear();
        LogcatHelper.getInstance(this).stop();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        titleBar = findViewById(R.id.title_bar);
    }

    protected boolean getSetting() {
        return false;
    }

    protected boolean setSetting() {
        return false;
    }

    public void setupFeed(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    /**
     * 沉浸式状态栏
     * 链接：http://www.jianshu.com/p/be2b7be418d7
     */
    protected void initState(int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
         if (hasKitKat()) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        /*
        /*boolean isImmersive = false;
        if (hasKitKat() && !hasLollipop()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != -1) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        isImmersive =false;
        titleBar.setImmersive(isImmersive);       //是否沉浸在状态栏*/
        StatusBarUtil.setTranslucent(this, 0);

        Window window = getWindow();
        if (SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != -1) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

    }

    public void setBackgroundColor(int color) {
        titleBar.setBackgroundColor(color);
    }

    public void setLeftText(String text, View.OnClickListener l) {
        titleBar.setLeftImageResource(R.drawable.back_blue);
        titleBar.setLeftText("   " + text);
        titleBar.setLeftTextSize(17);
        titleBar.setLeftTextColor(Color.WHITE);
        if (l == null) {
            titleBar.setLeftTextColor(Color.BLACK);
            titleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppManager.getAppManager().getActivity(BaseActivity.this);
                    BaseActivity.super.overridePendingTransition(R.anim.in_down, R.anim.out_up);
                }
            });
        } else {
            titleBar.setLeftClickListener(l);
        }
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
        titleBar.setTitleSize(15);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
    }

    public void addAction(TitleBar.Action action) {
        titleBar.addAction(action);
    }

    public void removeAllActions() {
        titleBar.removeAllActions();
    }

    public void setLeftVisible(boolean visible) {
        titleBar.setLeftVisible(visible);
    }

    public void setLeftImageResource(int leftImageResource) {
        titleBar.setLeftImageResource(leftImageResource);
    }
}
