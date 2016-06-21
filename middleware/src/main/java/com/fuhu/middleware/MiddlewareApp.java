package com.fuhu.middleware;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fuhu.middleware.control.MailBox;
import com.fuhu.middleware.control.PostOfficeProxy;

public class MiddlewareApp {
    public static final String TAG = MiddlewareApp.class.getSimpleName();
    private static MiddlewareApp INSTANCE;

    private static Context mContext;
    private String DATA_DIR_PATH;

    public MiddlewareApp(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
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

    /**
     * Clear
     */
    public void destroy() {
        PostOfficeProxy.clear();
        MailBox.clearMailBox();
        INSTANCE = null;
    }

    /**
     * Full path to a directory assigned to the package for its persistent
     * data.
     */
    public String getDataDirPath() {
        return DATA_DIR_PATH;
    }

    /**
     * A string resource identifier (in the package's resources) of this
     * component's label.
     */
    public static String getApplicationName() {
        if (mContext != null) {
            int stringId = mContext.getApplicationInfo().labelRes;
            return mContext.getString(stringId);
        } else {
            return TAG;
        }
    }

    /**
     * Return the context of the single, global Application object of the
     * current process.
     */
    public Context getApplicationContext() {
        if (mContext != null) {
            return mContext.getApplicationContext();
        }

        return null;
    }
}
