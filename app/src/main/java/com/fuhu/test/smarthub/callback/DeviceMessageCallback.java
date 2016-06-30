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
import com.fuhu.test.smarthub.componet.DeviceMessage;

public abstract class DeviceMessageCallback  implements IMailReceiveCallback {
    private static final String TAG = DeviceMessageCallback.class.getSimpleName();

    public static void reqSendMessage(final Context context, final DeviceMessageCallback deviceMessageCallback, final DeviceMessage deviceMessage) {
        Log.d(TAG, "reqSendMessage");

        WebRtcCommand deviceMessageCommand = new WebRtcCommandBuilder()
                .setID("3")
                .setDataModel(DeviceMessage.class)
                .setDataObject(deviceMessage)
                .useMockData(true)
                .build();

        MailBox.getInstance().deliverMail(context, deviceMessageCommand, deviceMessageCallback);
    }

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send DeviceMessage onMailReceive");
        if (mailItem != null && mailItem instanceof DeviceMessage) {
            if (ErrorCodeHandler.isSuccess(mailItem.getStatus())) {
                onResultReceived((DeviceMessage)mailItem);
            } else {
                onFailed(mailItem.getStatus(), mailItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeList.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(DeviceMessage deviceMessage);
    abstract public void onFailed(String status, String message);
}
