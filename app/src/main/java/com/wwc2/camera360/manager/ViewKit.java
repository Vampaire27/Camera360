package com.wwc2.camera360.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.wwc2.camera360.ui.main.AppBaseUI;
import com.wwc2.camera360.util.JobExecutor;


public class ViewKit {

    private Context mContext;
    private Handler mMainHandler;
    private JobExecutor mJobExecutor;
    private AppBaseUI mAppBaseUi;
    private CameraSettings mSettings;

    public ViewKit(Context context, AppBaseUI baseui) {
        mContext = context;
        mAppBaseUi = baseui;
        mMainHandler = new Handler(Looper.getMainLooper());
        mJobExecutor = new JobExecutor();
    }

    public void destroy() {
        mJobExecutor.destroy();
    }

    public AppBaseUI getBaseUI() {
        return mAppBaseUi;
    }

    public Handler getMainHandler() {
        return mMainHandler;
    }

    public JobExecutor getExecutor() {
        return mJobExecutor;
    }


    public CameraSettings getCameraSettings(Context context) {
        if (mSettings == null) {
            mSettings = new CameraSettings(context);
        }
        return mSettings;
    }


}
