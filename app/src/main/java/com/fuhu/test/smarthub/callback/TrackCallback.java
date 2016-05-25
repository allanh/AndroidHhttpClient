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
import com.fuhu.test.smarthub.componet.TrackItem;
import com.fuhu.middleware.componet.Log;

public abstract class TrackCallback implements IMailReceiveCallback {
    private static final String TAG = TrackCallback.class.getSimpleName();

    public static void reqSend(final Context context, final TrackCallback trackCallback, final TrackItem trackItem) {
        Log.d(TAG, "reqSend");
//        try {
//            String jsonSring = "{\"voiceTrackingList\":[{\"isSuccess\": true, \"text\":\"how are you;\", \"timestamp\":142345678967},{\"isSuccess\": true, \"text\":\"how are you\", \"timestamp\":142345678967}]}";

            HttpCommand trackCommand = new HttpCommandBuilder()
                    .setID("1")
                .setURL(NabiHttpRequest.getAPI_Track())
//                    .setURL("http://192.168.30.23:8080/IITService/tracking/voicetracking/test")
                    .setMethod(HttpCommand.Method.POST)
                    .setHeaders(HTTPHeader.getTrackingHeader(context))
                    .setDataModel(TrackItem.class)
//                    .setJSONObject(new JSONObject(jsonSring))
                    .setDataObject(trackItem, "voiceTrackingList")
//                    .useMockData(true)
                    .setShouldCache(true)
                    .build();

            MailBox.getInstance().deliverMail(context, trackCommand, trackCallback);

//        } catch (JSONException je) {
//            je.printStackTrace();
//        }
    }

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send track onMailReceive");
        if (mailItem != null && mailItem instanceof TrackItem) {
            TrackItem trackItem = (TrackItem) mailItem;
            Log.d(TAG, "status: " + trackItem.getStatus());

            if (ErrorCodeHandler.isSuccess(mailItem.getStatus())) {
                onResultReceived(trackItem);
            } else {
                onFailed(trackItem.getStatus(), trackItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeList.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(TrackItem trackItem);
    abstract public void onFailed(String status, String message);
}