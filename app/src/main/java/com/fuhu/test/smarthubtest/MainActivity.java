package com.fuhu.test.smarthubtest;

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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RESOURCE_URI_PATH = "android.resource://com.fuhu.test.smarthubtest/";

    private TextView tv_response;
    private ImageView iv_play;
    private Intent mParseService;

    private IntentFilter filter;
    private BroadcastReceiver receiver;

    private SpeechManager mSpeechManager;
//    private MediaPlayer mMediaPlayer;

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
                    String response = intent.getStringExtra("response");
                    Log.d(TAG, "response: " + response);

                    tv_response.setText(response);

                    // speak response
                    mSpeechManager.speakOut(response);
                }
            }
        };

        // Create a new MediaPlayer
//        mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        iv_play.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.pause();
//                    iv_play.setImageResource(R.drawable.play);
//                } else {
//                    playSong(R.raw.baby1);
//                    iv_play.setImageResource(R.drawable.pause);
//                }
//
//                return false;
//            }
//        });
    }
//    private void playSong(int resId) {
//        try {
//            mMediaPlayer.reset();
//            mMediaPlayer.setDataSource(this, Uri.parse(RESOURCE_URI_PATH + resId));
//            mMediaPlayer.prepare();
//            mMediaPlayer.setLooping(true);
//            mMediaPlayer.start();
//
////            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
////
////                public void onCompletion(MediaPlayer arg0) {
////                    iv_play.setImageResource(R.drawable.play);
////                }
////
////            });
//
//        } catch (IOException e) {
//            Log.v(getString(R.string.app_name), e.getMessage());
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);

        if (mParseService == null) {
            mParseService = new Intent(this, GoogleRecognizeService.class);
        }

        if (!CheckUtil.isMyServiceRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " start service");
            startService(mParseService);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
        if (CheckUtil.isMyServiceRunning(this, GoogleRecognizeService.class)) {
            Log.d(TAG, " stop service");
            stopService(mParseService);
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpeechManager != null) {
            mSpeechManager.shutdown();
        }

//        if(mMediaPlayer != null) {
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
        super.onDestroy();
    }
}
