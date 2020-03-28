package com.wwc2.camera360.manager;

import android.content.Context;

import com.wwc2.camera360.R;
import com.wwc2.camera360.data.PreferenceGroup;
import com.wwc2.camera360.module.ViewModule;
import com.wwc2.camera360.util.XmlInflater;


public class ModuleManager  {
    private static int sModuleNum = 1;
    private static int mCurrentIndex = 0;
    private ViewModule mCurrentModule;
    private Class<?>[] mModulesClass;

    /**
     * Manage all module, use reflection to create module instance
     * @param context used to init ModuleIndicator
     */
    public ModuleManager(Context context) {

        XmlInflater inflater = new XmlInflater(context);
        PreferenceGroup group = inflater.inflate(R.xml.module_preference);

        mModulesClass = getModuleClass(group);
        sModuleNum = mModulesClass.length;


    }

    private Class<?>[] getModuleClass(PreferenceGroup group) {

        Class<?>[] moduleCls = new Class[group.size()];
        for (int i = 0; i < group.size(); i++) {
            // use reflection to get module class
            try {
                moduleCls[i] = Class.forName(group.get(i).getKey());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // add indicator item
        }
        group.clear();
        return moduleCls;
    }

    public boolean needChangeModule(int index) {
        if (index < 0 || index >= sModuleNum || mCurrentIndex == index) {
            return false;
        } else {
            mCurrentIndex = index;
            return true;
        }
    }

    public ViewModule getNewModule() {
        try {
            mCurrentModule = (ViewModule) mModulesClass[mCurrentIndex].newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return mCurrentModule;
    }

    public ViewModule getCurrentModule() {
        return mCurrentModule;
    }

    public static int getCurrentIndex() {
        return mCurrentIndex;
    }

    public static boolean isValidIndex(int index) {
        return (index >= 0 && index < sModuleNum);
    }

    public static int getModuleCount() {
        return sModuleNum;
    }

}
