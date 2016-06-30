package com.fuhu.test.smarthub.callback;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.componet.WebRtcCommand;
import com.fuhu.middleware.componet.WebRtcCommandBuilder;
import com.fuhu.middleware.contract.IMailReceiveCallback;
import com.fuhu.middleware.control.ErrorCodeHandler;
import com.fuhu.middleware.control.MailBox;
import com.fuhu.test.smarthub.componet.AppStatus;

public abstract class AppStatusCallback implements IMailReceiveCallback {
    private static final String TAG = AppStatusCallback.class.getSimpleName();

    public static void reqGetAppStatus(final Context context, final AppStatusCallback appStatusCallback, final AppStatus appStatus) {
        Log.d(TAG, "reqGetAppStatus");

        WebRtcCommand appStatusCommand = new WebRtcCommandBuilder()
                .setID("2")
                .setDataModel(AppStatus.class)
                .setDataObject(appStatus)
                .useMockData(true)
                .build();

        MailBox.getInstance().deliverMail(context, appStatusCommand, appStatusCallback);
    }

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send AppStatus onMailReceive");
        if (mailItem != null && mailItem instanceof AppStatus) {
            Log.d(TAG, "status: " + mailItem.getStatus());

            if (ErrorCodeHandler.isSuccess(mailItem.getStatus())) {
                onResultReceived((AppStatus)mailItem);
            } else {
                onFailed(mailItem.getStatus(), mailItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeList.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(AppStatus appStatus);
    abstract public void onFailed(String status, String message);
}
