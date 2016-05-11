package com.fuhu.test.smarthub;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fuhu.test.smarthub.middleware.componet.Log;

public class SmartHubApp extends Application {
    public static final String TAG = SmartHubApp.class.getSimpleName();

    private static SmartHubApp INSTANCE;
    private static String DATA_DIR_PATH;

    public SmartHubApp() {
        super();
    }

    public static synchronized SmartHubApp getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " initial");
        INSTANCE = this;
        initial();
    }

    private void initial() {
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

    public static String getApplicationName() {
        int stringId = INSTANCE.getApplicationInfo().labelRes;
        return INSTANCE.getString(stringId);
    }
}
