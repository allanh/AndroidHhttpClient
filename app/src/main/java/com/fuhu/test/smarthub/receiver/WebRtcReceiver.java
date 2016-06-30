package com.fuhu.test.smarthub.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.Constants;
import com.fuhu.test.smarthub.activity.VideoChatActivity;

public class WebRtcReceiver extends BroadcastReceiver {
    private static final String TAG = GoogleRecognizeReceiver.class.getSimpleName();
    private Context mContext;

    public WebRtcReceiver(Context context) {
        this.mContext = context;
    }

    public static IntentFilter getFilter() {
        // create a new BroadcastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.RECEIVE_WEBRTC_CALL_IN);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || intent.getAction() == null)
            return;

        String action = intent.getAction();
        Log.d(TAG, "receive broadcast: " + action);

        if (action.equals(Constants.RECEIVE_WEBRTC_CALL_IN)) {
            if (intent.getExtras() != null) {
                String response = intent.getStringExtra("result");
                Log.d(TAG, "result: " + response);

                // Consider Accept/Reject call here
                Intent chatIntent = new Intent(mContext, VideoChatActivity.class);
                chatIntent.putExtra(Constants.USER_NAME, intent.getStringExtra(Constants.USER_NAME));
                chatIntent.putExtra(Constants.JSON_CALL_USER, intent.getStringExtra(Constants.JSON_CALL_USER));
                mContext.startActivity(chatIntent);
            }
        } else if (action.equals(Constants.RECEIVE_GET_APP_STATUS)) {
            Log.d(TAG, "get app status");
        }
    }
}
