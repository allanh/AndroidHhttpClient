package com.fuhu.test.smarthubtest;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SmartHubApp extends Application {
    public static final String TAG = SmartHubApp.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.acrodeus.mercurius/";

    private static SmartHubApp instance;
    private static String DATA_DIR_PATH;

    public SmartHubApp() {
        super();
    }

    public static SmartHubApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initial();
    }

    public void initial() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            DATA_DIR_PATH = packageInfo.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getDataDirPath() {
        return DATA_DIR_PATH;
    }
}
