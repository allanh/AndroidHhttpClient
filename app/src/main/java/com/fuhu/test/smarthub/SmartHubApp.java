package com.fuhu.test.smarthub;

import android.app.Application;

import com.fuhu.middleware.MiddlewareApp;
import com.fuhu.middleware.componet.Log;

public class SmartHubApp extends Application {
    public static final String TAG = SmartHubApp.class.getSimpleName();

    private static SmartHubApp INSTANCE;

    public SmartHubApp() {
        super();
    }

    public static SmartHubApp getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " initial");
        INSTANCE = this;

        // Initializing middleware
        MiddlewareApp.getInstance(this).initial();
    }
}
