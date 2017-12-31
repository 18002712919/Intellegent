package com.asus.intellegent.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.asus.intellegent.R;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.library.widget.wow.WowSplashView;
import com.library.widget.wow.WowView;

import static com.asus.intellegent.ui.activity.Utils.hasKitKat;

/**
 * Created by ASUS on 2017/12/9.
 */

public class SplashActivity extends AppCompatActivity {

    private WowView mWowView;
    private WowSplashView mWowSplashView;

    ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator() {

        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            objAnimator.setDuration(2000);
            objAnimator.start();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        initStatus();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mWowSplashView = findViewById(R.id.wowSplash);
        mWowView = findViewById(R.id.wowView);

        /*ImgMark.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(SplashActivity.this).load(R.drawable.tree)
                        .animate(animator).into(img);
                startAnimat();
            }
        });*/
        startAnimat();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decoderView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decoderView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //or ?
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (hasKitKat()) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void startAnimat() {
        mWowSplashView.startAnimate();

        mWowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {
                mWowSplashView.setVisibility(View.GONE);
                mWowView.setVisibility(View.VISIBLE);
                mWowView.startAnimate(wowSplashView.getDrawingCache());
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                SplashActivity.super.overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
    }

}
