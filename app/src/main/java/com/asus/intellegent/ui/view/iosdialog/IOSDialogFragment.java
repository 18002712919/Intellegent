package com.asus.intellegent.ui.view.iosdialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.intellegent.R;

/**
 * Created by willy on 17/10/8.
 */

public class IOSDialogFragment extends DialogFragment {

    private View rootView;
    private ButtomDialogListener mListener;
    private String[] items;
    private String title;

    public static IOSDialogFragment newInstance(String[] items, ButtomDialogListener mListener) {
        IOSDialogFragment fragment = new IOSDialogFragment();
        fragment.setButtomDialogListener(mListener);
        fragment.setItems(items);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = inflater.inflate(R.layout.fragment_ios_dialog, container, false);
        AnimationUtil.slideToUp(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        View decorView = window.getDecorView();
        decorView.setPadding(0, 100, 0, 0);
        decorView.setBackground(new ColorDrawable(Color.TRANSPARENT));
        decorView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //slideDown();
                }
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleView = view.findViewById(R.id.tv_title);
        titleView.setText(title);
        ListView listView = view.findViewById(R.id.lv_menu);
        listView.setAdapter(new ArrayAdapter(getActivity(), R.layout.bottom_item, R.id.tv_item, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
                slideDown();
            }
        });


        Button cancel = view.findViewById(R.id.btn_cancel);
        cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v);
                    slideDown();
                }
            }
        });
    }

    private void setButtomDialogListener(ButtomDialogListener listener) {
        this.mListener = listener;
    }

    private void setItems(String[] items) {
        this.items = items;
    }

    private void slideDown() {
        AnimationUtil.slideToDown(rootView, new AnimationUtil.AnimationEndListener() {
            @Override
            public void onFinish() {
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        if (title != null)
            this.title = title;
    }

    public interface ButtomDialogListener {

        void onClick(int position);

        void onClick(View v);
    }
}
