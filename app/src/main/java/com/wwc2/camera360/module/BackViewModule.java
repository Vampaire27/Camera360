package com.wwc2.camera360.module;


import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import android.view.View;
import com.wwc2.camera360.manager.CameraSession;
import com.wwc2.camera360.manager.CameraUiEvent;
import com.wwc2.camera360.manager.DeviceManager;
import com.wwc2.camera360.manager.RequestCallback;
import com.wwc2.camera360.manager.Session;
import com.wwc2.camera360.ui.main.BackUI;
import com.wwc2.camera360.util.CameraUtil;
import com.wwc2.camera360.util.Config;

public class BackViewModule extends ViewModule {

    private SurfaceTexture mSurfaceTexture;
    private BackUI mUI;
    private CameraSession mSession;
    private SingleDeviceManager mDeviceMgr;


    private static final String TAG = Config.getTag(BackViewModule.class);

    @Override
    protected void init() {
        mUI = new BackUI(appContext, mainHandler, mCameraUiEvent);
        mDeviceMgr = new SingleDeviceManager(appContext, mViewKit.getExecutor(), mCameraEvent);

        mSession = new CameraSession(appContext, mainHandler, getSettings());
    }

    @Override
    public void start() {
        String cameraId =  mDeviceMgr.getCameraIdList()[0];
        mDeviceMgr.setCameraId(cameraId);
        mDeviceMgr.openCamera(mainHandler);
        // when module changed , need update listener
        getBaseUI().setCameraUiEvent(mCameraUiEvent);
        addModuleView(mUI.getRootView());
        Log.d(TAG, "start module");
    }

    private DeviceManager.CameraEvent mCameraEvent = new DeviceManager.CameraEvent() {
        @Override
        public void onDeviceOpened(CameraDevice device) {
            super.onDeviceOpened(device);
            Log.d(TAG, "camera opened");
            mSession.applyRequest(Session.RQ_SET_DEVICE, device);

            enableState(CameraUtil.CAMERA_STATE_OPENED);
            if (stateEnabled(CameraUtil.CAMERA_STATE_UI_READY)) {
                mSession.applyRequest(Session.RQ_START_PREVIEW, mSurfaceTexture, mRequestCallback);
            }


        }

        @Override
        public void onDeviceClosed() {
            super.onDeviceClosed();
            disableState(CameraUtil.CAMERA_STATE_OPENED);
            if (mUI != null) {
                mUI.resetFrameCount();
            }
            Log.d(TAG, "camera closed");
        }
    };

    private RequestCallback mRequestCallback = new RequestCallback() {
        @Override
        public void onDataBack(byte[] data, int width, int height) {
            super.onDataBack(data, width, height);

            mSession.applyRequest(Session.RQ_RESTART_PREVIEW);
        }

        @Override
        public void onViewChange(int width, int height) {
            super.onViewChange(width, height);

        }

        @Override
        public void onAFStateChanged(int state) {
            super.onAFStateChanged(state);

        }
    };

    @Override
    public void stop() {
        getBaseUI().setCameraUiEvent(null);

        mSession.release();
        mDeviceMgr.releaseCamera();
        Log.d(TAG, "stop module");
    }

    private void takePicture() {
        mUI.setUIClickable(false);

        mSession.applyRequest(Session.RQ_TAKE_PICTURE,0);
    }



    private CameraUiEvent mCameraUiEvent = new CameraUiEvent() {

        @Override
        public void onPreviewUiReady(SurfaceTexture mainSurface, SurfaceTexture auxSurface) {
            Log.d(TAG, "onSurfaceTextureAvailable");
            mSurfaceTexture = mainSurface;
            enableState(CameraUtil.CAMERA_STATE_UI_READY);
            if (stateEnabled(CameraUtil.CAMERA_STATE_OPENED)) {
                mSession.applyRequest(Session.RQ_START_PREVIEW, mSurfaceTexture, mRequestCallback);
            }

        }

        @Override
        public void onPreviewUiDestroy() {
            disableState(CameraUtil.CAMERA_STATE_UI_READY);
            Log.d(TAG, "onSurfaceTextureDestroyed");
        }

        @Override
        public void onTouchToFocus(float x, float y) {
            // close all menu when touch to focus

        }

        @Override
        public void resetTouchToFocus() {
                mSession.applyRequest(Session.RQ_FOCUS_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
        }

        @Override
        public <T> void onSettingChange(CaptureRequest.Key<T> key, T value) {
            if (key == CaptureRequest.LENS_FOCUS_DISTANCE) {
                mSession.applyRequest(Session.RQ_FOCUS_DISTANCE, value);
            }
        }

        @Override
        public <T> void onAction(String type, T value) {
            // close all menu when ui click

            switch (type) {
                case CameraUiEvent.ACTION_CLICK:
                    handleClick((View) value);
                    break;
                case CameraUiEvent.ACTION_CHANGE_MODULE:

                    break;
                case CameraUiEvent.ACTION_SWITCH_CAMERA:
                    break;
                case CameraUiEvent.ACTION_PREVIEW_READY:

                    break;
                default:
                    break;
            }
        }
    };



    private void handleClick(View view) {

    }


}
