package com.fuhu.test.smarthub.middleware.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fuhu.test.smarthub.middleware.componet.ActionPreferences;
import com.fuhu.test.smarthub.middleware.componet.IManager;
import com.fuhu.test.smarthub.middleware.receiver.RegistrationGCMReceiver;
import com.fuhu.test.smarthub.middleware.service.gcm.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class GCMManager implements IManager {
    private static final String TAG = GCMManager.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static GCMManager INSTANCE;

    private static BroadcastReceiver mRegistrationBroadcastReceiver;

    /** Whether or not the broadcast receiver has been registered **/
    private static boolean isRegistered;

    /** Whether or not the recognize service has been started **/
    private static boolean isServiceRunning;

    private static Activity mActivity;

    /**
     * Default constructor
     */
    private GCMManager() {
        isRegistered = false;
        isServiceRunning = false;
    }

    public static synchronized GCMManager getInstance(Activity activity) {
        mActivity = activity;

        if (INSTANCE == null) {
            INSTANCE = new GCMManager();
        }
        return INSTANCE;
    }

    /**
     * Start parsing service
     */
    @Override
    public synchronized void startService() {
        Log.d(TAG, "start service: " + isServiceRunning);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(mActivity, RegistrationIntentService.class);
            mActivity.startService(intent);

            isServiceRunning = true;
        }
    }

    /**
     * Stop parsing service
     */
    @Override
    public synchronized void stopService() {
        Log.d(TAG, "stop service: " + isServiceRunning);

        if (isServiceRunning) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(mActivity, RegistrationIntentService.class);
            mActivity.stopService(intent);

            isServiceRunning = false;
        }
    }

    /**
     * Register recognize receiver
     */
    @Override
    public synchronized void registerReceiver() {
        Log.d(TAG, "register receiver: " + isRegistered);

        // GCM
        if(!isRegistered) {
            if (mRegistrationBroadcastReceiver == null) {
                mRegistrationBroadcastReceiver = new RegistrationGCMReceiver();
            }

            LocalBroadcastManager.getInstance(mActivity).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(ActionPreferences.REGISTRATION_COMPLETE));
            isRegistered = true;
        }
    }

    /**
     * UnRegister recognize receiver
     */
    @Override
    public synchronized void unregisterReceiver() {
        Log.d(TAG, "unregister receiver: " + isRegistered);
        if (isRegistered) {
            try {
                mActivity.unregisterReceiver(mRegistrationBroadcastReceiver);
                isRegistered = false;
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(mActivity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                mActivity.finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        unregisterReceiver();
        stopService();
    }
}
