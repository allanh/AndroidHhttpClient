package com.fuhu.test.smarthub.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuhu.test.smarthub.R;
import com.fuhu.test.smarthub.SmartHubConfig;
import com.fuhu.test.smarthub.middleware.manager.GCMManager;
import com.fuhu.test.smarthub.middleware.manager.SpeechRecognizeManager;
import com.fuhu.test.smarthub.middleware.manager.TextToSpeechManager;
import com.fuhu.test.smarthub.middleware.receiver.WiFiDirectBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.fuhu.test.smarthub/";

    private TextView tv_response;
    private ImageView iv_play;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private BroadcastReceiver mWiFiDirectBroadcastReceiver;

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
        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
            SpeechRecognizeManager.getInstance(this).registerReceiver(
                    TextToSpeechManager.getInstance(this), tv_response);
        }

        if (SmartHubConfig.enableGCM) {
            GCMManager.getInstance(this).startService();
            GCMManager.getInstance(this).unregisterReceiver();
        }

        registerReceivers();
    }

    private void registerReceivers(){

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

        unregisterReceiver(mWiFiDirectBroadcastReceiver);
        isWifiP2PRegistered = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceivers();

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).stop();
        }

        if (SmartHubConfig.enableGCM) {
            GCMManager.getInstance(this).stop();
        }
    }

    @Override
    protected void onDestroy() {
        if (SmartHubConfig.isRecognizingSpeech) {
            TextToSpeechManager.getInstance(this).shutdown();
        }

        super.onDestroy();
    }
}
