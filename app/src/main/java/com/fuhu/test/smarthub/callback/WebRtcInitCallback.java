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
import com.fuhu.test.smarthub.componet.InitItem;

public abstract class WebRtcInitCallback implements IMailReceiveCallback {
    private static final String TAG = WebRtcInitCallback.class.getSimpleName();

    public static void reqInitialize(final Context context, final WebRtcInitCallback webRtcCallback, final InitItem initItem) {
        Log.d(TAG, "reqInitialize");

        WebRtcCommand initCommand = new WebRtcCommandBuilder()
                .setID("1")
                .setDataModel(InitItem.class)
                .setDataObject(initItem)
                .useMockData(true)
                .build();

        MailBox.getInstance().deliverMail(context, initCommand, webRtcCallback);
    }

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send init onMailReceive");
        if (mailItem != null) {
            Log.d(TAG, "status: " + mailItem.getStatus());

            if (ErrorCodeHandler.isSuccess(mailItem.getStatus())) {
                onResultReceived(mailItem);
            } else {
                onFailed(mailItem.getStatus(), mailItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeList.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(AMailItem mailItem);
    abstract public void onFailed(String status, String message);
}
