package com.fuhu.test.smarthub.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.widget.TextView;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.IManager;
import com.fuhu.test.smarthub.receiver.GoogleRecognizeReceiver;
import com.fuhu.test.smarthub.service.GoogleRecognizeService;

/**
 * Helper to recognize speech using google recognize service
 */
public class SpeechRecognizeManager implements IManager {
    private static final String TAG = SpeechRecognizeManager.class.getSimpleName();

    private static SpeechRecognizeManager INSTANCE;

    private static Intent mParseService;

    private static BroadcastReceiver mGoogleRecognizeReceiver;

    /** Whether or not the broadcast receiver has been registered **/
    private static boolean isRegistered;

    /** Whether or not the recognize service has been started **/
    private static boolean isServiceRunning;

    private static Activity mActivity;

    /**
     * Default constructor
     */
    private SpeechRecognizeManager() {
        mParseService = new Intent(mActivity, GoogleRecognizeService.class);
        isRegistered = false;
        isServiceRunning = false;
    }

    public static synchronized SpeechRecognizeManager getInstance(Activity activity) {
        mActivity = activity;

        if (INSTANCE == null) {
            INSTANCE = new SpeechRecognizeManager();
        }
        return INSTANCE;
    }

    /**
     * Start parsing service
     */
    @Override
    public synchronized void startService() {
        Log.d(TAG, " start service: " + isServiceRunning);
        if (!isServiceRunning) {
            mActivity.startService(mParseService);
            isServiceRunning = true;
        }
    }

    /**
     * Stop parsing service
     */
    @Override
    public synchronized void stopService() {
        Log.d(TAG, " stop service: " + isServiceRunning);

        mActivity.stopService(mParseService);
        isServiceRunning = false;
    }

    /**
     * Register recognize receiver
     */
    @Override
    public synchronized void registerReceiver() {
        registerReceiver(null, null);
    }


    /**
     * Register recognize receiver with SpeechManager and TextView
     */
    public synchronized void registerReceiver(TextToSpeechManager speechManager, TextView textView) {
        Log.d(TAG, "register receiver: " + isRegistered);

        // Google recognize server
        if (!isRegistered) {
            if (mGoogleRecognizeReceiver == null) {
                mGoogleRecognizeReceiver = new GoogleRecognizeReceiver(mActivity, speechManager, textView);
            }

            mActivity.registerReceiver(mGoogleRecognizeReceiver, GoogleRecognizeReceiver.getFilter());
            isRegistered = true;
        }
    }

    /**
     * UnRegister recognize receiver
     */
    @Override
    public synchronized void unregisterReceiver() {
        Log.d(TAG, "unregister receiver");
        if (isRegistered) {
            try {
                mActivity.unregisterReceiver(mGoogleRecognizeReceiver);
                isRegistered = false;
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void stop() {
        unregisterReceiver();
        stopService();
    }
}
