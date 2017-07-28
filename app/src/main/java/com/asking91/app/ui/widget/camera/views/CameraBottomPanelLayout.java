package com.asking91.app.ui.widget.camera.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;


import com.asking91.app.R;


/**
 * Created by jswang on 2017/2/16.
 */

public class CameraBottomPanelLayout extends RelativeLayout implements OnClickListener, OnTouchListener {
    private View c_take;
    private View c_cancel;
    private View c_album;
    private OnCameraBottomPanelListener mListener;

    public CameraBottomPanelLayout(Context context) {
        super(context);
        init(context);
    }

    public CameraBottomPanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CameraBottomPanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public View getC_take(){
        return c_take;
    }

    public View getC_cancel(){
        return c_cancel;
    }

    public View getC_album(){
        return c_album;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.camera_view_bottom_panel_layout, this);
        c_take = findViewById(R.id.camera_control_panel_take);
        c_cancel = findViewById(R.id.camera_control_panel_cancel);
        c_album = findViewById(R.id.camera_control_panel_album);

        c_take.setOnClickListener(this);
        c_cancel.setOnClickListener(this);
        c_album.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.camera_control_panel_take:
                mListener.OnCameraTake();
                break;
            case R.id.camera_control_panel_album://选择从图库选择
                mListener.OnCameraAlbum();
                break;
            case R.id.camera_control_panel_cancel:
                mListener.OnCameraCancel();
                break;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    public void setOnCameraBottomPanelListener(OnCameraBottomPanelListener mListener) {
        this.mListener = mListener;
    }


    public interface OnCameraBottomPanelListener{
        void OnCameraTake();

        void OnCameraCancel();

        void OnCameraAlbum();

    }
}