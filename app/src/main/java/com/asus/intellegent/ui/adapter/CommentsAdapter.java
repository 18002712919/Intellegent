package com.asus.intellegent.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.adapter.utils.RoundedTransformation;
import com.asus.intellegent.ui.json.Json;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Created by froger_mcs on 11.11.14.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private List<Map<String, Object>> data = null;
    private OnItemClickListener onItemClickListener;

    public CommentsAdapter(Context context,Json json) {
        this.context = context;
        avatarSize = 56;
        try {
            data = json.listViewData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view,onItemClickListener);
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        CommentViewHolder holder = (CommentViewHolder) viewHolder;
        Map map = data.get(position);
        holder.tvComment.setText((String) map.get("tvComment"));
        holder.tvprompt.setText((String) map.get("tvprompt"));
        //holder.ivUserAvatar.setImageResource((Integer) map.get("ivUserAvatar"));
        Picasso.with(context)
                .load((Integer) map.get("ivUserAvatar"))
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new RoundedTransformation())
                .into(holder.ivUserAvatar);
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    /**
     * 总条目数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public void updateItems() {
        itemsCount = data.size();
        notifyDataSetChanged();
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView ivUserAvatar;
        public TextView tvComment;
        public TextView tvprompt;
        private OnItemClickListener mListener;

        public CommentViewHolder(View view,OnItemClickListener listener) {
            super(view);
            ivUserAvatar = view.findViewById(R.id.ivUserAvatar);
            tvComment = view.findViewById(R.id.tvComment);
            tvprompt = view.findViewById(R.id.tvprompt);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getAdapterPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view,int postion);
    }

}
