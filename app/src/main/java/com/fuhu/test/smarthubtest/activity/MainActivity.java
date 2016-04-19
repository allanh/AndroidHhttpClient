package com.fuhu.test.smarthubtest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuhu.test.smarthubtest.R;
import com.fuhu.test.smarthubtest.callback.IFTTTCallback;
import com.fuhu.test.smarthubtest.middleware.android.CheckUtil;
import com.fuhu.test.smarthubtest.middleware.componet.MailItem;
import com.fuhu.test.smarthubtest.middleware.service.GoogleRecognizeService;
import com.fuhu.test.smarthubtest.manager.IFTTTManager;
import com.fuhu.test.smarthubtest.manager.SpeechManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.fuhu.test.smarthubtest/";

    private TextView tv_response, ifttt_response;
    private ImageView iv_play;
    private Intent mParseService;

    private IntentFilter filter;
    private BroadcastReceiver receiver;

    private SpeechManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_response = (TextView) findViewById(R.id.result);
        ifttt_response = (TextView) findViewById(R.id.ifttt_result);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);

        if (mParseService == null) {
            mParseService = new Intent(this, GoogleRecognizeService.class);
        }

        if (!CheckUtil.isRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " start service");
            startService(mParseService);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
        if (CheckUtil.isRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " stop service");
            stopService(mParseService);
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
