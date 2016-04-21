package com.fuhu.test.smarthub.middleware.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.fuhu.test.smarthub.middleware.componet.IManager;
import com.fuhu.test.smarthub.middleware.receiver.WiFiDirectBroadcastReceiver;

public class WifiP2PHandler implements IManager {
    private static final String TAG = WifiP2PHandler.class.getSimpleName();

    private static WifiP2PHandler INSTANCE;

    private static BroadcastReceiver mWiFiDirectBroadcastReceiver;

    /** Whether or not the broadcast receiver has been registered **/
    private static boolean isRegistered;

    private static Activity mActivity;

    private static WifiP2pManager mManager;
    private static WifiP2pManager.Channel mChannel;
    
    /**
     * Default constructor
     */
    private WifiP2PHandler(Activity activity) {
        mManager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(activity, activity.getMainLooper(), null);
        mWiFiDirectBroadcastReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, activity);
        isRegistered = false;
    }

    public static synchronized WifiP2PHandler getInstance(Activity activity) {
        mActivity = activity;

        if (INSTANCE == null) {
            INSTANCE = new WifiP2PHandler(activity);
        }
        return INSTANCE;
    }

    @Override
    public synchronized void startService() {}

    @Override
    public synchronized void stopService() {}

    /**
     * Register wifi direct receiver
     */
    @Override
    public synchronized void registerReceiver() {
        Log.d(TAG, "register receiver: " + isRegistered);

        if (!isRegistered) {
            mActivity.registerReceiver(mWiFiDirectBroadcastReceiver, WiFiDirectBroadcastReceiver.getFilter());
            isRegistered = true;
        }
    }

    /**
     * Unregister wifi direct receiver
     */
    @Override
    public synchronized void unregisterReceiver() {
        Log.d(TAG, "unregister receiver: " + isRegistered);
        if (isRegistered) {
            try {
                mActivity.unregisterReceiver(mWiFiDirectBroadcastReceiver);
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            } finally {
                isRegistered = false;
            }
        }
    }

    @Override
    public void stop() {
        unregisterReceiver();
        stopService();
    }
}
