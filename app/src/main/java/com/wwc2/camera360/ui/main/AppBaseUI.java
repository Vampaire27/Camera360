package com.wwc2.camera360.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.wwc2.camera360.R;
import com.wwc2.camera360.manager.CameraUiEvent;


/**
 * Created by wenzhe on 9/15/17.
 */

public class AppBaseUI implements View.OnClickListener {
    private RelativeLayout mPreviewRootView;

    private ImageView capture_button;
    private ImageView photo_button;
    private ImageView three_button;
    private ImageView file_button;
    private ImageView setting_button;
    private CameraUiEvent mEvent;

    public AppBaseUI(Context context, View rootView) {

        mPreviewRootView = rootView.findViewById(R.id.preview_root_view);

        capture_button = rootView.findViewById(R.id.capture_button);
        capture_button.setOnClickListener(this);

        photo_button = rootView.findViewById(R.id.photo_button);
        photo_button.setOnClickListener(this);

        three_button = rootView.findViewById(R.id.three_button);
        three_button.setOnClickListener(this);

        file_button = rootView.findViewById(R.id.file_button);
        file_button.setOnClickListener(this);

        setting_button = rootView.findViewById(R.id.setting_button);
        setting_button.setOnClickListener(this);

    }

    public RelativeLayout getRootView() {
        return mPreviewRootView;
    }

    public void setCameraUiEvent(CameraUiEvent event) {
        mEvent = event;
    }

    @Override
    public void onClick(View v) {
        if (mEvent != null) {
            mEvent.onAction(CameraUiEvent.ACTION_CLICK, v);
        }
    }
}
