package com.wwc2.camera360.ui.main;

import android.view.View;
import android.widget.RelativeLayout;
import com.wwc2.camera360.manager.CameraUiEvent;
import com.wwc2.camera360.manager.ModuleManager;


public abstract class CameraBaseUI implements GestureTextureView.GestureListener, View
        .OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    CameraUiEvent uiEvent;
    int frameCount = 0;


    CameraBaseUI(CameraUiEvent event) {
        uiEvent = event;
    }

    public abstract RelativeLayout getRootView();

    public void setUIClickable(boolean clickable) {
    }

    public void resetFrameCount() {
        frameCount = 0;
    }



    /* View.OnClickListener*/
    @Override
    public void onClick(View v) {
        uiEvent.onAction(CameraUiEvent.ACTION_CLICK, v);
    }

    /* GestureTextureView.GestureListener */
    @Override
    public void onClick(float x, float y) {
        uiEvent.onTouchToFocus(x, y);
    }

    @Override
    public void onSwipeLeft() {
        int newIndex = ModuleManager.getCurrentIndex() + 1;
        if (ModuleManager.isValidIndex(newIndex)) {
            uiEvent.onAction(CameraUiEvent.ACTION_CHANGE_MODULE, newIndex);
        }
    }

    @Override
    public void onSwipeRight() {
        int newIndex = ModuleManager.getCurrentIndex() - 1;
        if (ModuleManager.isValidIndex(newIndex)) {
            uiEvent.onAction(CameraUiEvent.ACTION_CHANGE_MODULE, newIndex);
        }
    }

    @Override
    public void onSwipe(float percent) {
        int newIndex;
        if (percent < 0) {
            newIndex = ModuleManager.getCurrentIndex() + 1;
        } else {
            newIndex = ModuleManager.getCurrentIndex() - 1;
        }
        if (ModuleManager.isValidIndex(newIndex)) {

        }
    }

    @Override
    public void onCancel() {

    }
}
