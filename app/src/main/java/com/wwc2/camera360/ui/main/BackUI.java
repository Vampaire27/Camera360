package com.wwc2.camera360.ui.main;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.widget.RelativeLayout;

import com.wwc2.camera360.R;
import com.wwc2.camera360.manager.CameraUiEvent;


public class BackUI extends CameraBaseUI implements TextureView.SurfaceTextureListener {

    private final String TAG = this.getClass().getSimpleName();

    private RelativeLayout mRootView;
    private GestureTextureView mPreviewTexture;


    public BackUI(Context context, Handler handler, CameraUiEvent event) {
        super(event);
        mRootView = (RelativeLayout) LayoutInflater.from(context)
                .inflate(R.layout.module_backview_layout, null);
        mPreviewTexture = mRootView.findViewById(R.id.texture_preview);
        mPreviewTexture.setSurfaceTextureListener(this);
        mPreviewTexture.setGestureListener(this);
    }

    @Override
    public void setUIClickable(boolean clickable) {
        super.setUIClickable(clickable);
        mPreviewTexture.setClickable(clickable);
    }



    @Override
    public RelativeLayout getRootView() {
        return mRootView;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        uiEvent.onPreviewUiReady(surface, null);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        uiEvent.onPreviewUiDestroy();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // preview frame is ready when receive second frame
        if (frameCount == 2) {return;}
        frameCount++;
        if (frameCount == 2) {
            uiEvent.onAction(CameraUiEvent.ACTION_PREVIEW_READY, null);
        }
    }

}
