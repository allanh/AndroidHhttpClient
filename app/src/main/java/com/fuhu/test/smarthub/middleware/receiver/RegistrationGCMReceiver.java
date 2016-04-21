package com.fuhu.test.smarthub.middleware.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fuhu.test.smarthub.middleware.componet.ActionPreferences;

public class RegistrationGCMReceiver extends BroadcastReceiver {
    private static final String TAG = RegistrationGCMReceiver.class.getSimpleName();

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
}
