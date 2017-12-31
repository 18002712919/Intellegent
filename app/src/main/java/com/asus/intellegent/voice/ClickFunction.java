package com.asus.intellegent.voice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.call.SpeechApp;
import com.asus.intellegent.ui.activity.MTActivity;
import com.library.widget.TitleBar;

public class ClickFunction implements OnClickListener {

    private BaseDrawerActivity baseDrawerActivity;
    private SpeechApp app;
    private static final String TAG = ClickFunction.class.getSimpleName();

    public ClickFunction(Context context, SpeechApp app) {
        baseDrawerActivity = (BaseDrawerActivity) context;
        this.app = app;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.icon_help:
                isLayout(StaticDate.FALSE);
                break;

            case R.id.icon_translation:
                Intent intent = new Intent(baseDrawerActivity, MTActivity.class);
                baseDrawerActivity.startActivity(intent);
                //设置切换动画
                baseDrawerActivity.overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
                break;

            case R.id.icon_voice_normal:
                if (!baseDrawerActivity.checkNetworkState()) {
                    Toast.makeText(baseDrawerActivity, "没有网络", Toast.LENGTH_LONG).show();
                    return;
                }
                /*if (baseDrawerActivity.get()) {
                    dialog();
                }*/
                baseDrawerActivity.isIat();
                break;
        }
    }
    public void isLayout() {
        baseDrawerActivity.layout.setVisibility(View.GONE);
        if (baseDrawerActivity.outView.getVisibility() == View.VISIBLE) {
            baseDrawerActivity.outView.setVisibility(View.GONE);
            onClick_LampOff();
            baseDrawerActivity.removeAllActions();
            baseDrawerActivity.addAction(new TitleBar.ImageAction(R.drawable.set_up) {
                @Override
                public void performAction(View view) {
                    baseDrawerActivity.startIntent();
                }
            });
            //baseDrawerActivity.contentRoot.setVisibility(View.VISIBLE);
            animateClose(baseDrawerActivity.contentRoot);
        }else if (baseDrawerActivity.contentRoot.getVisibility() == View.VISIBLE) {
            baseDrawerActivity.outView.setVisibility(View.VISIBLE);
            //baseDrawerActivity.contentRoot.setVisibility(View.GONE);
            animateOpen(baseDrawerActivity.contentRoot);
        }
    }

    public void isLayout(int i) {
        if (baseDrawerActivity.layout.getVisibility() == View.VISIBLE) {
            if (i == StaticDate.TRUE) {
                return;
            }
            baseDrawerActivity.layout.setVisibility(View.GONE);
            baseDrawerActivity.outView.setVisibility(View.GONE);
            baseDrawerActivity.linearLayout.setVisibility(View.VISIBLE);
            onClick_LampOff();
            //animateOpen(baseDrawerActivity.layout,origHeight);
        } else if (baseDrawerActivity.linearLayout.getVisibility() == View.VISIBLE) {
            baseDrawerActivity.layout.setVisibility(View.VISIBLE);
            baseDrawerActivity.linearLayout.setVisibility(View.GONE);
            onClick_LampOn();
           //animateClose(baseDrawerActivity.linearLayout,origHeight);
        }
    }

    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseDrawerActivity);
        builder.setTitle("是否上传联系人？");
        builder.setIcon(R.mipmap.ic_backup_black);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                app.getMgr();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void animateClose(final View view) {
        view.setVisibility(View.VISIBLE);
        int origHeight = (int) ((baseDrawerActivity.getResources().getDisplayMetrics().density*40)+.5);
        //int origHeight=view.getHeight();
        ValueAnimator animator=createDropAnimator(view,0,origHeight);
        animator.start();
    }

    private void animateOpen(final View view) {
        int origHeight=view.getHeight();
        ValueAnimator animator=createDropAnimator(view,origHeight,0);
        animator.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view,int start,int end)
    {
        ValueAnimator animator=ValueAnimator.ofInt(start,end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                int value=(Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
                layoutParams.height=value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    //“开灯”按钮的单击事件方法
    private void onClick_LampOn() {
        //设置level为15，显示help_on.png
        baseDrawerActivity.icon_help.setImageLevel(15);
        baseDrawerActivity.setTitle("智能语音改变你的生活");
        baseDrawerActivity.setLeftText("",null);
        baseDrawerActivity.setLeftVisible(true);
    }

    //“关灯”按钮的单击事件方法
    private void onClick_LampOff() {
        //设置level为6，显示help_off.png
        baseDrawerActivity.icon_help.getDrawable().setLevel(6);
        baseDrawerActivity.setTitle("您好，请问需要什么帮助");
        baseDrawerActivity.setLeftText("",null);
        baseDrawerActivity.setLeftVisible(true);
        baseDrawerActivity.contentRoot.setVisibility(View.VISIBLE);
        baseDrawerActivity.comments.onComments();
    }
}
