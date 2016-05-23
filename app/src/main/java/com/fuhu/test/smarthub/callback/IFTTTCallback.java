package com.fuhu.test.smarthub.callback;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.HTTPHeader;
import com.fuhu.middleware.componet.HttpCommand;
import com.fuhu.middleware.componet.HttpCommandBuilder;
import com.fuhu.middleware.componet.IMailReceiveCallback;
import com.fuhu.middleware.contract.ErrorCodeHandler;
import com.fuhu.middleware.contract.MailBox;
import com.fuhu.middleware.contract.NabiHttpRequest;
import com.fuhu.test.smarthub.componet.IFTTTItem;
import com.fuhu.middleware.componet.Log;


public abstract class IFTTTCallback implements IMailReceiveCallback {
    private static final String TAG = IFTTTCallback.class.getSimpleName();

    public static void reqSend(final Context context, final IFTTTCallback mIftttCallback, final IFTTTItem iftttItem) {
        Log.d(TAG, "reqSend ifttt");
        HttpCommand iftttCommand = new HttpCommandBuilder()
                .setID("2")
                .setURL(NabiHttpRequest.getAPI_IFTTT())
                .setMethod(HttpCommand.Method.POST)
                .setHeaders(HTTPHeader.getDefaultHeader())
                .setDataModel(IFTTTItem.class)
                .setDataObject(iftttItem, "value1")
                .build();

        MailBox.getInstance().deliverMail(context, iftttCommand, mIftttCallback);
    }

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send IFTTT onMailReceive:");
        if (mailItem != null && mailItem instanceof IFTTTItem) {
            IFTTTItem iftttItem = (IFTTTItem) mailItem;

            Log.d(TAG, "status: " + iftttItem.getStatus());
            // Checks if request is success
            if (ErrorCodeHandler.isSuccess(iftttItem.getStatus())) {
                onIftttReceived(iftttItem);
            } else {
                onFailed(iftttItem.getStatus(), iftttItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.UNKNOWN_EXCEPTION.getCode(),
                    ErrorCodeList.UNKNOWN_EXCEPTION.toString());
        }
    }

    abstract public void onIftttReceived(IFTTTItem iftttItem);
    abstract public void onFailed(String status, String message);
}