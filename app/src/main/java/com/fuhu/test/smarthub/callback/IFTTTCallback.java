package com.fuhu.test.smarthub.callback;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.componet.IFTTTItem;
import com.fuhu.test.smarthub.middleware.componet.IMailItem;
import com.fuhu.test.smarthub.middleware.componet.IMailReceiveCallback;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

import java.util.List;


public abstract class IFTTTCallback implements IMailReceiveCallback {
    private static final String TAG = IFTTTCallback.class.getSimpleName();

    public static void reqSend(final Context context, final IFTTTCallback mIftttCallback, final IFTTTItem iftttItem) {
        Log.d(TAG, "reqSend");
//        MailBox.getInstance().deliverMail(context, SmartHubCommand.ReqSendToIFTTT, mIftttCallback, iftttItem);
    }

    @Override
    public void onMailReceive(List<IMailItem> iftttItemList) {
        Log.d(TAG, "send IFTTT onMailReceive:" + iftttItemList.size());
        if (iftttItemList.size() > 0 && iftttItemList.get(0) != null && iftttItemList.get(0) instanceof IFTTTItem) {
            IFTTTItem iftttItem = (IFTTTItem) iftttItemList.get(0);
            onIftttReceived(iftttItem);
        } else {
            onFailed(ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode(),
                    ErrorCodeHandler.UNKNOWN_EXCEPTION.toString());
        }
    }

    abstract public void onIftttReceived(IFTTTItem iftttItem);
    abstract public void onFailed(String status, String message);
}