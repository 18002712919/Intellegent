package com.asus.intellegent.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.intellegent.R;
import com.asus.intellegent.ui.json.Voice;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.voice.StaticDate;
import com.library.checkbox.SmoothCheckBox;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2017/7/4.
 */

public class VoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int lastAnimatedPosition = -1;
    private int mSelectedPos = -1;//保存当前选中的position 重点！
    private ArrayList<Bean> mList = new ArrayList<>();
    private Session session;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private List<Map<String, Object>> data = null;
    private MyItemClickListener mItemClickListener;

    public VoiceAdapter(Voice json) {
        try {
            data = json.voiecViewData();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        session = new Session(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.item_choose, parent, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, List payloads) {
        runEnterAnimation(viewHolder.itemView, position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        /**
         * payloads即有效负载，当首次加载或调用notifyDatasetChanged(),
         * notifyItemChange(int position)进行刷新时，payloads为empty 即空
         */

        final Bean bean = mList.get(position);
        if (payloads.isEmpty()) {
            Map map = data.get(position);
            holder.informant.setText((String) map.get("informant"));
            holder.timbre.setText((String) map.get("timbre"));
            holder.voice.setText((String) map.get("voice"));
            holder.checkBox.setChecked(mSelectedPos == position);
        } else {

            /**
             * 当调用notifyItemChange(int position, Object payload)进行布局刷新时，
             * payloads不会empty ，所以真正的布局刷新应该在这里实现 重点！
             */

            holder.checkBox.setChecked(mSelectedPos == position);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {
                    //当前选中的position和上次选中不是同一个position 执行
                    holder.checkBox.setChecked(true, true);
                    if (mSelectedPos != -1) {
                        //判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos, 0);
                    }
                    mSelectedPos = position;
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                bean.isChecked = isChecked;
            }
        });
        holder.checkBox.setChecked(bean.isChecked);
    }

    private int setSetting(int position) {
        Map map = data.get(position);
        String voice_name = (String) map.get("voice_name");
        session.putInt(StaticDate.POSITION, position);
        session.putString(StaticDate.VOICE_NAME, voice_name);
        int temp = session.getInt(StaticDate.POSITION,position);
        return temp;
    }

    class Bean implements Serializable {
        boolean isChecked;
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
        for (int i = 0; i < data.size(); i++) {
            mList.add(new Bean());
        }
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView voice;
        public TextView informant;
        public TextView timbre;
        public ImageView imageView;
        public SmoothCheckBox checkBox;
        private MyItemClickListener mListener;

        public ViewHolder(View view, MyItemClickListener listener) {
            super(view);
            voice = view.findViewById(R.id.voicea);
            informant = view.findViewById(R.id.informanta);
            timbre = view.findViewById(R.id.timbrea);
            imageView = view.findViewById(R.id.imageView);
            checkBox = view.findViewById(R.id.checkBox);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            Bean bean = mList.get(getAdapterPosition());
            boolean isChecked = !bean.isChecked;
            mListener.onItemClick(v, isChecked, getAdapterPosition());
        }

    }

    public interface MyItemClickListener {
        void onItemClick(View view, final boolean isChecked, int position);
    }

}
