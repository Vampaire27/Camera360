package com.wwc2.camera360.module;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.wwc2.camera360.manager.CameraSettings;
import com.wwc2.camera360.manager.ViewKit;
import com.wwc2.camera360.ui.main.AppBaseUI;
import com.wwc2.camera360.util.CameraUtil;
import com.wwc2.camera360.util.Config;


public abstract class ViewModule {

    private static final String TAG = Config.getTag(ViewModule.class);

    private int mCameraState = CameraUtil.CAMERA_MODULE_STOP;

    Handler mainHandler;
    Context appContext;
    RelativeLayout rootView;
    ViewKit mViewKit;

    public void init(Context context, ViewKit viewkit) {
        // just need init once

        appContext = context;
        mViewKit = viewkit;
        rootView = mViewKit.getBaseUI().getRootView();
        mainHandler = mViewKit.getMainHandler();
        // call subclass init()
        init();
    }


    boolean isAndTrue(int param1, int param2) {
        return (param1 & param2) != 0;
    }

    void enableState(int state) {
        mCameraState = mCameraState | state;
    }

    void disableState(int state) {
        mCameraState = mCameraState & (~state);
    }

    boolean stateEnabled(int state) {
        return isAndTrue(mCameraState, state);
    }

    public void startModule() {
        if (isAndTrue(mCameraState, CameraUtil.CAMERA_MODULE_STOP)) {
            disableState(CameraUtil.CAMERA_MODULE_STOP);
            enableState(CameraUtil.CAMERA_MODULE_RUNNING);
            start();
        }
    }

    public void stopModule() {
        if (isAndTrue(mCameraState, CameraUtil.CAMERA_MODULE_RUNNING)) {
            disableState(CameraUtil.CAMERA_MODULE_RUNNING);
            enableState(CameraUtil.CAMERA_MODULE_STOP);
            stop();
        }
    }




    protected abstract void init();

    protected abstract void start();

    protected abstract void stop();

    void addModuleView(View view) {
        if (rootView.getChildAt(0) != view) {
            if (rootView.getChildCount() > 1) {
                rootView.removeViewAt(0);
            }
            rootView.addView(view, 0);
        }
    }

    AppBaseUI getBaseUI() {
        return mViewKit.getBaseUI();
    }

    protected CameraSettings getSettings() {
        return mViewKit.getCameraSettings(appContext);
    }

    protected void runOnUiThread(Runnable runnable) {
        mViewKit.getMainHandler().post(runnable);
    }

}
