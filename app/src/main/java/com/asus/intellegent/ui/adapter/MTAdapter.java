package com.asus.intellegent.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asus.intellegent.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 2017/8/20.
 */

public class MTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private List<FeedItem> feedItems;

    private boolean showLoadingView = false;

    public MTAdapter(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    protected MTAdapter(Parcel in) {
        showLoadingView = in.readByte() != 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_mt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FeedItem feedItem = feedItems.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        if (feedItem.getType() == FeedItem.TYPE_RECEIVE) {
            holder.left_Layout_mt.setVisibility(View.VISIBLE);
            holder.right_Layout_mt.setVisibility(View.GONE);
            holder.left_mt.setText(feedItem.getContent());
        } else if (feedItem.getType() == FeedItem.TYPE_SEND) {
            holder.right_Layout_mt.setVisibility(View.VISIBLE);
            holder.left_Layout_mt.setVisibility(View.GONE);
            holder.right_mt.setText(feedItem.getContent());
        }

    }

    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        if (feedItem.getType() == FeedItem.TYPE_RECEIVE) {
            holder.left_Layout_mt.setVisibility(View.VISIBLE);
            holder.right_Layout_mt.setVisibility(View.GONE);
            holder.left_mt.setText(feedItem.getContent());
        } else if (feedItem.getType() == FeedItem.TYPE_SEND) {
            holder.right_Layout_mt.setVisibility(View.VISIBLE);
            holder.left_Layout_mt.setVisibility(View.GONE);
            holder.right_mt.setText(feedItem.getContent());
        }

    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout left_Layout_mt;
        public LinearLayout right_Layout_mt;
        public TextView left_mt;
        public TextView right_mt;
        public ImageView voice_left_mt;
        public ImageView voice_right_mt;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            left_Layout_mt = view.findViewById(R.id.left_layoutmt);
            right_Layout_mt = view.findViewById(R.id.right_layoutmt);
            left_mt = view.findViewById(R.id.leftmt);
            right_mt = view.findViewById(R.id.rightmt);
            voice_left_mt = view.findViewById(R.id.voice_leftmt);
            voice_right_mt = view.findViewById(R.id.voice_rightmt);
            layout = view.findViewById(R.id.card_view);
            layout.setBackgroundColor(Color.alpha(0));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class FeedItem {

        private String content;
        private int type;
        public static final int TYPE_RECEIVE = 0;
        public static final int TYPE_SEND = 1;

        public FeedItem(String content, int type) {
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public int getType() {
            return type;
        }
    }

}
