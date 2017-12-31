package com.asus.intellegent.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.adapter.MTAdapter;

import java.util.ArrayList;
import java.util.List;

public class MTActivity extends BaseDrawerActivity {

    private ImageView mtout;
    private EditText alertDialog_et;
    private ImageView mt;
    private RecyclerView rView;
    private Toolbar tool;
    private final List<MTAdapter.FeedItem> mtItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt);
        mtAdapter = new MTAdapter(mtItems);
        super.setupFeed(rView, mtAdapter);
        super.welcome();
    }

    /*public void setupFeed() {
        mtAdapter = new MTAdapter(this, mtItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rView.setLayoutManager(linearLayoutManager);

        rView.setAdapter(mtAdapter);
    }*/

    public void updateView(String result, int type) {
        mtItems.add(new MTAdapter.FeedItem(result, type));
        mtAdapter.notifyItemInserted(mtItems.size() - 1);
        rView.scrollToPosition(mtItems.size() - 1);
        //设置recycler的Item分割线
        //listview转gridview没有列的分割
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rView.setItemAnimator(new DefaultItemAnimator());
    }

    public void initView() {
        super.initView();
        //mtout = findViewById(R.id.mtout);
        alertDialog_et = findViewById(R.id.alertDialog_et);
        mt = findViewById(R.id.mt);
        rView = findViewById(R.id.rView);
        //mtout.setOnClickListener(onClickListener);
       // mt.setOnClickListener(onClickListener);
        //tool = findViewById(R.id.tool);
    }

    private String submit() {
        // validate
        String et = alertDialog_et.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }else {
            Log.e("et"+et,"111111111");
            return et;
        }

        // TODO validate success, do something

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                //case R.id.mtout:
                    /*Intent intent = null;
                    Bundle bundle = getIntent().getExtras();
                    String activity = bundle.getString("activity");
                    if(activity.contains("MainActivity")){
                        intent = new Intent(MTActivity.this,MainActivity.class);
                    }else if(activity.contains("CommentsActivity")){
                        intent = new Intent(MTActivity.this,CommentsActivity.class);
                    }
                    startActivity(intent);
                    break;*/
                case R.id.mt:

                    break;
            }
        }
    };

}
