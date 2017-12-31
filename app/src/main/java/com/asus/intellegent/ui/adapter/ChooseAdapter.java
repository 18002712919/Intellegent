package com.asus.intellegent.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.intellegent.R;
import com.library.checkbox.SmoothCheckBox;
import com.library.widget.TriangleLabelView;

import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2016/5/9 0009.
 */
public class ChooseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int lastAnimatedPosition = -1;
    private int mSelectedItem = -1; //保存当前选中的position 重点！
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private OnItemClickListener onItemClickListener;
    private List<Map<String, Object>> data;

    public ChooseAdapter(List<Map<String, Object>> data, int position) {
        this.data = data;
        this.mSelectedItem = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.item_choose, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindData(position);
    }

    //局部刷新关键：带payloads的这个onBindViewHolder方法必须实现
    /*@Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        //super.onBindViewHolder(holder, position, payloads);
        /**
         * payloads即有效负载，当首次加载或调用notifyDatasetChanged(),
         * notifyItemChange(int position)进行刷新时，payloads为empty 即空
         */

        /*if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {

            /**
             * 当调用notifyItemChange(int position, Object payload)进行布局刷新时，
             * payloads不会empty ，所以真正的布局刷新应该在这里实现 重点！
             */
    //注意：payloads的size总是1
            /*String payload = (String) payloads.get(0);
            Log.e("payload ="," "+payload);
            //需要更新的控件
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bindData(position);
        }
    }*/

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
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView voice;
        public TextView informant;
        public TextView timbre;
        public ImageView imageView;
        public SmoothCheckBox checkBox;
        public TriangleLabelView labelView;
        private OnItemClickListener mListener;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            this.mListener = listener;
            voice = view.findViewById(R.id.voicea);
            informant = view.findViewById(R.id.informanta);
            timbre = view.findViewById(R.id.timbrea);
            imageView = view.findViewById(R.id.imageView);
            checkBox = view.findViewById(R.id.checkBox);
            labelView = view.findViewById(R.id.labelView);
            view.setOnClickListener(this);
            checkBox.setOnClickListener(this);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }

        public void bindData(int position) {
            final boolean isPosition = position == mSelectedItem;
            Map map = data.get(position);
            informant.setText((String) map.get("informant"));
            timbre.setText((String) map.get("timbre"));
            voice.setText((String) map.get("voice"));
            checkBox.setId(position);
            checkBox.setChecked(isPosition, isPosition);
            labelView.setLabelView(isPosition);
        }
        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            mSelectedItem = getAdapterPosition();
            notifyItemRangeChanged(0, data.size());
            mListener.onItemClick(v, mSelectedItem);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onClick(int position);
    }

}
