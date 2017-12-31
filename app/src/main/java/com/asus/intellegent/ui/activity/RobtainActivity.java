package com.asus.intellegent.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.adapter.ChooseAdapter;
import com.asus.intellegent.ui.json.Voice;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.voice.StaticDate;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import static com.asus.intellegent.ui.activity.BaseDrawerActivity.app;

public class RobtainActivity extends BaseSettingActivity {

    private RecyclerView robtain;
    private List<Map<String,Object>> data;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robtain);
        super.setLeftText("语音播报角色",null);
        Voice voice = new Voice(this);
        try {
            this.data = voice.voiecViewData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupFeed();
    }

    private boolean setSetting(int position) {
        Map map = data.get(position);
        String informant = (String) map.get("informant");
        String name = (String) map.get("voice_name");
        session.putInt(StaticDate.POSITION,position);
        session.putString(StaticDate.BROADCAST_CHARACTERS,informant);
        session.putString(StaticDate.VOICE_NAME,name);
        session.commit();
        Log.e("voice_name:"+name,"position:"+position);
        Log.e(" "+session.getString(StaticDate.VOICE_NAME,"xiaoyuan"),
                " "+session.getString(StaticDate.BROADCAST_CHARACTERS,"小燕"));
        return true;
    }

    private void initView() {
        robtain = findViewById(R.id.robtain);
    }

    public void setupFeed() {
        int position = session.getInt(StaticDate.POSITION,0);
        ChooseAdapter chooseAdapter = new ChooseAdapter(data, position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        robtain.setLayoutManager(linearLayoutManager);
        //robtain.setLayoutManager(new VegaLayoutManager());
        //chooseAdapter.notifyDataSetChanged();
        chooseAdapter.notifyItemChanged(position);
        //((SimpleItemAnimator)robtain.getItemAnimator()).setSupportsChangeAnimations(false);
        robtain.getItemAnimator().setChangeDuration(0);
        //chooseAdapter.notifyItemChanged(position, "引起图片闪烁的根本原因");
        //robtain.smoothScrollToPosition(position);
        ((LinearLayoutManager)robtain.getLayoutManager()).scrollToPositionWithOffset(position,0);
        robtain.setAdapter(chooseAdapter);
        chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("www"+position,"111111111");
                setSetting(position);
            }

            @Override
            public void onClick(int position) {
                Map map = data.get(position);
                String name = (String) map.get("voice_name");
                Log.e("  "+name,position+"");
                if (app != null) {
                    app.speaking(name);
                }
                //Toast.makeText(RobtainActivity.this,position+"",Toast.LENGTH_LONG).show();
            }
        });
    }

}
