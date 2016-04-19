package com.fuhu.test.smarthubtest.callback;

import android.content.Context;

import com.fuhu.test.smarthubtest.middleware.SmartHubCommand;
import com.fuhu.test.smarthubtest.middleware.componet.IMailItem;
import com.fuhu.test.smarthubtest.middleware.componet.IMailReceiveCallback;
import com.fuhu.test.smarthubtest.middleware.componet.Log;
import com.fuhu.test.smarthubtest.middleware.componet.MailItem;
import com.fuhu.test.smarthubtest.middleware.contract.ErrorCodeHandler;
import com.fuhu.test.smarthubtest.middleware.contract.MailBox;

import java.util.List;


public abstract class IFTTTCallback implements IMailReceiveCallback {
    private static final String TAG = IFTTTCallback.class.getSimpleName();

    public static void reqSend(final Context context, final IFTTTCallback mIftttCallback, final MailItem mailItem) {
        Log.d(TAG, "reqSend");
        MailBox.getInstance().deliverMail(context, SmartHubCommand.ReqSendToIFTTT, mIftttCallback, mailItem);
    }

    @Override
    public void onMailReceive(List<IMailItem> mailItemList) {
        Log.d(TAG, "send IFTTT onMailReceive:" + mailItemList.size());
        if (mailItemList.size() > 0 && mailItemList.get(0) != null && mailItemList.get(0) instanceof MailItem) {
            MailItem mailItem = (MailItem) mailItemList.get(0);

            // check result
            if (ErrorCodeHandler.isSuccess(mailItem.getStatus())) {
                onIftttReceived(mailItem);
            } else {
                ErrorCodeHandler errorCode = ErrorCodeHandler.lookup(mailItem.getStatus());
                String message = (errorCode != null)? errorCode.toString()
                        : ErrorCodeHandler.UNKNOWN_EXCEPTION.toString();
                onFailed(mailItem.getStatus(), message);
            }
        } else {
            onFailed(ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode(),
                    ErrorCodeHandler.UNKNOWN_EXCEPTION.toString());
        }
    }

    abstract public void onIftttReceived(MailItem mailItem);
    abstract public void onFailed(String status, String message);
}