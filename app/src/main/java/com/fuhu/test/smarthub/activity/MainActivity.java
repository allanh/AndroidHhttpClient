package com.fuhu.test.smarthub.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuhu.test.smarthub.R;
import com.fuhu.test.smarthub.SmartHubConfig;
import com.fuhu.test.smarthub.middleware.componet.ActionPreferences;
import com.fuhu.test.smarthub.middleware.manager.SpeechRecognizeManager;
import com.fuhu.test.smarthub.middleware.manager.TextToSpeechManager;
import com.fuhu.test.smarthub.middleware.receiver.RegistrationGCMReceiver;
import com.fuhu.test.smarthub.middleware.receiver.WiFiDirectBroadcastReceiver;
import com.fuhu.test.smarthub.middleware.service.gcm.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.fuhu.test.smarthub/";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private TextView tv_response;
    private ImageView iv_play;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private BroadcastReceiver mWiFiDirectBroadcastReceiver;

    private TextToSpeechManager mSpeechManager;
    private boolean isGCMRegistered, isWifiP2PRegistered;

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_response = (TextView) findViewById(R.id.result);

        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_play.setVisibility(View.GONE);

        init();
    }

    private void init() {
        // create a new SpeechManager
        mSpeechManager = new TextToSpeechManager(this);

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
        }

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
            SpeechRecognizeManager.getInstance(this).registerReceiver(mSpeechManager, tv_response);
        }
        registerReceivers();
    }

    private void registerReceivers(){
        // GCM
        if(!isGCMRegistered) {
            if (mRegistrationBroadcastReceiver == null) {
                mRegistrationBroadcastReceiver = new RegistrationGCMReceiver();
            }

            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(ActionPreferences.REGISTRATION_COMPLETE));
            isGCMRegistered = true;
        }

        if (!isWifiP2PRegistered) {
            if (mManager == null || mChannel == null || mWiFiDirectBroadcastReceiver == null) {
                // Wifi Direct
                mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
                mChannel = mManager.initialize(this, getMainLooper(), null);
                mWiFiDirectBroadcastReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
            }

            registerReceiver(mWiFiDirectBroadcastReceiver, WiFiDirectBroadcastReceiver.getFilter());
            isWifiP2PRegistered = true;
        }
    }

    private void unregisterReceivers(){
        // GCM
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isGCMRegistered = false;

        unregisterReceiver(mWiFiDirectBroadcastReceiver);
        isWifiP2PRegistered = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceivers();

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).unregisterReceiver();
            SpeechRecognizeManager.getInstance(this).stopService();
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpeechManager != null) {
            mSpeechManager.shutdown();
        }

        super.onDestroy();
    }
}
