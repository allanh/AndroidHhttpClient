package com.fuhu.test.smarthub.callback;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.componet.HTTPHeader;
import com.fuhu.test.smarthub.middleware.componet.HttpCommand;
import com.fuhu.test.smarthub.middleware.componet.HttpCommandBuilder;
import com.fuhu.test.smarthub.middleware.componet.IMailItem;
import com.fuhu.test.smarthub.middleware.componet.IMailReceiveCallback;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.TrackItem;
import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;
import com.fuhu.test.smarthub.middleware.contract.MailBox;
import com.fuhu.test.smarthub.middleware.contract.OkHTTPRequest;

import java.util.List;

public abstract class TrackCallback implements IMailReceiveCallback {
    private static final String TAG = TrackCallback.class.getSimpleName();

    public static void reqSend(final Context context, final TrackCallback trackCallback, final TrackItem trackItem) {
        Log.d(TAG, "reqSend");
        HttpCommand trackCommand = new HttpCommandBuilder()
                .setID("4")
                .setURL(OkHTTPRequest.getAPI_Track())
                .setMethod(HttpCommand.Method.POST)
                .setHeader(HTTPHeader.getTrackingHeader(context))
                .setDataObject(TrackItem.class, trackItem, "voiceTrackingList")
                .build();

        MailBox.getInstance().deliverMail(context, trackCallback, trackCommand);
    }

    @Override
    public void onMailReceive(List<IMailItem> trackItemList) {
        Log.d(TAG, "send track onMailReceive:" + trackItemList.size());
        if (trackItemList.size() > 0 && trackItemList.get(0) != null && trackItemList.get(0) instanceof TrackItem) {
            TrackItem trackItem = (TrackItem) trackItemList.get(0);
            Log.d(TAG, "status: " + trackItem.getStatus());
            onResultReceived(trackItem);
        } else {
            onFailed(ErrorCodeHandler.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeHandler.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(TrackItem trackItem);
    abstract public void onFailed(String status, String message);
}