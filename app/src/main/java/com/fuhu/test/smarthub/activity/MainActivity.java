package com.fuhu.test.smarthub.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuhu.test.smarthub.R;
import com.fuhu.test.smarthub.callback.IFTTTCallback;
import com.fuhu.test.smarthub.middleware.componet.MailItem;
import com.fuhu.test.smarthub.middleware.contract.CheckUtil;
import com.fuhu.test.smarthub.middleware.manager.IFTTTManager;
import com.fuhu.test.smarthub.middleware.manager.SpeechManager;
import com.fuhu.test.smarthub.middleware.service.GoogleRecognizeService;
import com.fuhu.test.smarthub.middleware.service.gcm.ActionPreferences;
import com.fuhu.test.smarthub.middleware.service.gcm.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.fuhu.test.smarthub/";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private TextView tv_response;
    private ImageView iv_play;
    private Intent mParseService;

    private IntentFilter filter;
    private BroadcastReceiver receiver;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private SpeechManager mSpeechManager;
    private boolean isReceiverRegistered;

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
        mParseService = new Intent(this, GoogleRecognizeService.class);

        // create a new SpeechManager
        mSpeechManager = new SpeechManager(this);

        // create a new BroadcastReceiver
        filter = new IntentFilter();
        filter.addAction(GoogleRecognizeService.ACTION_RECEIVE_RESPONSE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "receive broadcast");
                if (intent != null && intent.getExtras() != null) {
                    String response = intent.getStringExtra("result");
                    Log.d(TAG, "result: " + response);

                    tv_response.setText(response);

                    // speak response
                    mSpeechManager.speakOut(response);

                    // Send to IFTTT
                    IFTTTManager.sendToIFTTT(getApplicationContext(),
                        new IFTTTCallback() {
                            public void onIftttReceived(MailItem mailItem) {

                            };
                            public void onFailed(String status, String message) {

                            };
                        }, response);
                }
            }
        };

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(ActionPreferences.SENT_TOKEN_TO_SERVER, false);

                Log.d(TAG, "send Token: " + sentToken);
//                if (sentToken) {
//                    mInformationTextView.setText(getString(R.string.gcm_send_message));
//                } else {
//                    mInformationTextView.setText(getString(R.string.token_error_message));
//                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

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
        registerReceiver(receiver, filter);
        registerReceiver();

        if (mParseService == null) {
            mParseService = new Intent(this, GoogleRecognizeService.class);
        }

        if (!CheckUtil.isRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " start service");
            startService(mParseService);
        }
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(ActionPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;

        unregisterReceiver(receiver);
        if (CheckUtil.isRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " stop service");
            stopService(mParseService);
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mSpeechManager != null) {
            mSpeechManager.shutdown();
        }

        super.onDestroy();
    }
}
