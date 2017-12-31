package com.asus.intellegent.voice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;

import com.asus.intellegent.R;
import com.asus.intellegent.open.ContactInfo;
import com.asus.intellegent.ui.activity.BaseDrawerActivity;
import com.asus.intellegent.ui.adapter.CommentsAdapter;
import com.asus.intellegent.ui.json.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ASUS on 2017/8/12.
 */

public class Comments implements CommentsAdapter.OnItemClickListener {

    private BaseDrawerActivity baseDrawerActivity;
    private CommentsAdapter commentsAdapter;
    private Json json;

    private static final String TAG = Comments.class.getSimpleName();

    public Comments(Context context, List<ContactInfo> contactLists) {
        this.baseDrawerActivity = (BaseDrawerActivity) context;

        json = new Json(context, contactLists);
        /*baseDrawerActivity.contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                baseDrawerActivity.contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                startIntroAnimation();
                return true;
            }
        });*/
    }

    public void onComments() {
        setupComments();
        baseDrawerActivity.contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                baseDrawerActivity.contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                startIntroAnimation();
                return true;
            }
        });
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseDrawerActivity);
        baseDrawerActivity.rvComments.setLayoutManager(linearLayoutManager);
        //baseDrawerActivity.rvComments.setLayoutManager(new VegaLayoutManager());
        baseDrawerActivity.rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(baseDrawerActivity, json);
        commentsAdapter.setOnItemClickListener(this);
        baseDrawerActivity.rvComments.setAdapter(commentsAdapter);
        baseDrawerActivity.rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        baseDrawerActivity.rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });
        commentsAdapter.notifyDataSetChanged();
    }


    private void startIntroAnimation() {
        baseDrawerActivity.contentRoot.setScaleY(0.1f);
        baseDrawerActivity.contentRoot.setPivotY(0.2f);

        baseDrawerActivity.contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        commentsAdapter.updateItems();
    }


    /**
     * Item click
     */
    @Override
    public void onItemClick(View view, int postion) {
        List<String> list = null;
        try {
            list = json.commandList(postion);
            JSONArray jsonArray = json.getJsonArray();
            JSONObject obj = jsonArray.getJSONObject(postion);
            Object title = obj.get("title");
            baseDrawerActivity.removeAllActions();
            baseDrawerActivity.setTitle("");
            baseDrawerActivity.setLeftVisible(false);
            baseDrawerActivity.setLeftText((String) title, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseDrawerActivity.click.isLayout();
                }
            });
            baseDrawerActivity.setLeftImageResource(R.mipmap.back_green);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter
                (baseDrawerActivity, R.layout.simple_listview_item, list);
        baseDrawerActivity.listView.setAdapter(adapter);
        baseDrawerActivity.click.isLayout();
    }

}
