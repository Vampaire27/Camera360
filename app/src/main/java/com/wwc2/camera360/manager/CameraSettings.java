package com.wwc2.camera360.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import android.preference.PreferenceManager;
import android.util.Size;
import android.view.WindowManager;

import com.wwc2.camera360.R;
import com.wwc2.camera360.util.Config;
import java.util.ArrayList;



public class CameraSettings {
    private final String TAG = Config.getTag(CameraSettings.class);

    public static final String KEY_PICTURE_SIZE = "pref_picture_size";
    public static final String KEY_PREVIEW_SIZE = "pref_preview_size";
    public static final String KEY_CAMERA_ID = "pref_default_backview";

    public static final String KEY_PICTURE_FORMAT = "pref_picture_format";

    private static final ArrayList<String> SPEC_KEY = new ArrayList<>(3);

    static {
        SPEC_KEY.add(KEY_PICTURE_SIZE);
        SPEC_KEY.add(KEY_PREVIEW_SIZE);
    }

    private SharedPreferences mSharedPreference;
    private Context mContext;
    private Point mRealDisplaySize = new Point();

    public CameraSettings(Context context) {
        PreferenceManager.setDefaultValues(context, R.xml.camera_setting, false);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(mRealDisplaySize);
        mContext = context;
    }



    public boolean setGlobalPref(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getGlobalPref(String key, String defaultValue) {
        return mSharedPreference.getString(key, defaultValue);
    }



    public Size getPreviewSize() {
           return Config.preview_size;
    }

    public int getPicFormat() {
        return 0x100;
    }



}
