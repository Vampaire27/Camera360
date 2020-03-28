package com.wwc2.camera360.module;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wwc2.camera360.R;
import com.wwc2.camera360.manager.ModuleManager;
import com.wwc2.camera360.manager.ViewKit;
import com.wwc2.camera360.ui.main.AppBaseUI;
import com.wwc2.camera360.util.Config;
import androidx.annotation.Nullable;


public class CameraFragment extends Fragment {

    private static final String TAG = Config.getTag(CameraFragment.class);

    private AppBaseUI mBaseUI;
    private ViewKit mViewKit;
    private Context mAppContext;
    private View mRootView;
    private ModuleManager mModuleManager;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = getActivity().getApplicationContext();
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.camera_fragment_layout, null);
        mBaseUI = new AppBaseUI(mAppContext, mRootView);
        mViewKit = new ViewKit(mAppContext,mBaseUI );
        mModuleManager = new ModuleManager(mAppContext);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mModuleManager.getCurrentModule() == null) {
            Log.d(TAG, "init module");

            ViewModule cameraModule = mModuleManager.getNewModule();
            cameraModule.init(mAppContext, mViewKit);
        }
        mModuleManager.getCurrentModule().startModule();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mModuleManager.getCurrentModule() != null) {
            mModuleManager.getCurrentModule().stopModule();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewKit.destroy();
    }


}
