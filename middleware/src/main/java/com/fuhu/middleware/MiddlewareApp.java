package com.fuhu.middleware;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class MiddlewareApp {
    public static final String TAG = MiddlewareApp.class.getSimpleName();
    private static Context mContext;
    private static MiddlewareApp INSTANCE;
    private static String DATA_DIR_PATH;

    public MiddlewareApp(Context context) {
        this.mContext = context;
    }

    public static synchronized MiddlewareApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MiddlewareApp(context);
        }
        return INSTANCE;
    }

    public void initial() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            DATA_DIR_PATH = packageInfo.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getDataDirPath() {
        return DATA_DIR_PATH;
    }

    public static String getApplicationName() {
        if (mContext != null) {
            int stringId = mContext.getApplicationInfo().labelRes;
            return mContext.getString(stringId);
        } else {
            return TAG;
        }
    }
}
