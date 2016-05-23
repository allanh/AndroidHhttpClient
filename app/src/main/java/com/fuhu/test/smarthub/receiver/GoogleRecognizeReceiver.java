package com.fuhu.test.smarthub.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

import com.fuhu.middleware.componet.ActionPreferences;
import com.fuhu.test.smarthub.callback.IFTTTCallback;
import com.fuhu.test.smarthub.componet.IFTTTItem;
import com.fuhu.middleware.componet.Log;
import com.fuhu.test.smarthub.manager.IFTTTManager;
import com.fuhu.test.smarthub.manager.TextToSpeechManager;

public class GoogleRecognizeReceiver extends BroadcastReceiver {
    private static final String TAG = GoogleRecognizeReceiver.class.getSimpleName();
    private Context mContext;
    private TextToSpeechManager mSpeechManager;
    private TextView mTextView;

    public GoogleRecognizeReceiver(Context context, TextToSpeechManager speechManager, TextView textView) {
        this.mContext = context;
        this.mSpeechManager = speechManager;
        this.mTextView = textView;
    }

    public static IntentFilter getFilter() {
        // create a new BroadcastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionPreferences.RECEIVE_RECOGNIZE_RESULT);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "receive broadcast");
        if (intent != null && intent.getExtras() != null) {
            String response = intent.getStringExtra("result");
            Log.d(TAG, "result: " + response);

            if (mTextView != null) {
                mTextView.setText(response);
            }

            // speak response
//            if (mSpeechManager != null) {
//                mSpeechManager.speakOut(response);
//            }

            if (mContext != null) {
                // Send to IFTTT
                IFTTTManager.sendToIFTTT(mContext,
                        new IFTTTCallback() {
                            public void onIftttReceived(IFTTTItem iftttItem) {

                            }

                            public void onFailed(String status, String message) {

                            }
                        },
                        response);
            }
        }
    }
}
