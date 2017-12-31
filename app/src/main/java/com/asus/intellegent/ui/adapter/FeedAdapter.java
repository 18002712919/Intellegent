package com.asus.intellegent.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asus.intellegent.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 2017/8/10.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private List<FeedItem> feedItems;

    private boolean showLoadingView = false;

    public FeedAdapter(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    /**
     * 视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        FeedItem feedItem = feedItems.get(position);
        if (feedItem.getType() == FeedItem.TYPE_RECEIVE) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.left.setText(feedItem.getContent());
        } else if (feedItem.getType() == FeedItem.TYPE_SEND) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.right.setText(feedItem.getContent());
        }
        holder.time.setVisibility(View.GONE);
        holder.time.setText(feedItem.getChatTime());
    }

    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        if (feedItem.getType() == FeedItem.TYPE_RECEIVE) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.left.setText(feedItem.getContent());
        } else if (feedItem.getType() == FeedItem.TYPE_SEND) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.right.setText(feedItem.getContent());
        }
        holder.time.setVisibility(View.GONE);
        holder.time.setText(feedItem.getChatTime());
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout leftLayout;
        public LinearLayout rightLayout;
        public TextView left;
        public TextView right;
        public TextView time;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            left = view.findViewById(R.id.left);
            right = view.findViewById(R.id.right);
            time = view.findViewById(R.id.time);
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
        private String chatTime;
        private int type;
        private final SimpleDateFormat
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static final int TYPE_RECEIVE = 0;
        public static final int TYPE_SEND = 1;

        public FeedItem(String content, int type) {
            this.chatTime = sdf.format(new Date(System.currentTimeMillis()));
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public String getChatTime() {
            return chatTime;
        }

        public int getType() {
            return type;
        }
    }

}
