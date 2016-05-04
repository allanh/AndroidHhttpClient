package com.fuhu.test.smarthub.activity;

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
import com.fuhu.test.smarthub.middleware.manager.WifiP2PHandler;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_response;
    private ImageView iv_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_response = (TextView) findViewById(R.id.result);

        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_play.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO STT
        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).startService();
            SpeechRecognizeManager.getInstance(this).registerReceiver(
                    TextToSpeechManager.getInstance(this), tv_response);
        }

        if (SmartHubConfig.enableGCM) {
            GCMManager.getInstance(this).startService();
            GCMManager.getInstance(this).registerReceiver();
        }

        if (SmartHubConfig.enableWifiP2P) {
            WifiP2PHandler.getInstance(this).registerReceiver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (SmartHubConfig.isRecognizingSpeech) {
            SpeechRecognizeManager.getInstance(this).stop();
        }

        if (SmartHubConfig.enableGCM) {
            GCMManager.getInstance(this).stop();
        }

        if (SmartHubConfig.enableWifiP2P) {
            WifiP2PHandler.getInstance(this).stop();
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
