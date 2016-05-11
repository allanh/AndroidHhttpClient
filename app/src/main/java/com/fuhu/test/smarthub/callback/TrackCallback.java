package com.fuhu.test.smarthub.callback;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.componet.AMailItem;
import com.fuhu.test.smarthub.middleware.componet.HTTPHeader;
import com.fuhu.test.smarthub.middleware.componet.HttpCommand;
import com.fuhu.test.smarthub.middleware.componet.HttpCommandBuilder;
import com.fuhu.test.smarthub.middleware.componet.IJsonParser;
import com.fuhu.test.smarthub.middleware.componet.IMailItem;
import com.fuhu.test.smarthub.middleware.componet.IMailReceiveCallback;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.TrackItem;
import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;
import com.fuhu.test.smarthub.middleware.contract.GSONUtil;
import com.fuhu.test.smarthub.middleware.contract.MailBox;
import com.fuhu.test.smarthub.middleware.contract.OkHTTPRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackCallback implements IMailReceiveCallback {
    private static final String TAG = TrackCallback.class.getSimpleName();

    public static void reqSend(final Context context, final TrackCallback trackCallback, final TrackItem trackItem) {
        Log.d(TAG, "reqSend");
        HttpCommand trackCommand = new HttpCommandBuilder()
                .setID(2)
                .setURL(OkHTTPRequest.getAPI_Track())
                .setMethod(HttpCommand.Method.POST)
                .setHeader(HTTPHeader.getTrackingHeader(context))
                .setDataObject(trackItem, "voiceTrackingList")
                .setJsonParser(mJsonParser)
                .build();

        MailBox.getInstance().deliverMail(context, trackCallback, trackCommand);
    }

    private static IJsonParser mJsonParser = new IJsonParser() {
        @Override
        public List<AMailItem> JSONParse(JSONObject JSONFormat) {
            List<AMailItem> retrieveItem = new ArrayList<AMailItem>();
            String resCode= ErrorCodeHandler.Default.getCode();

            Log.d("request", "ReqSendToTrack JSONFormat: " + JSONFormat);
            try {
                if(JSONFormat != null && JSONFormat.has("status")){
                    resCode = JSONFormat.getString("status");
                }else{
                    return ErrorCodeHandler.genErrorItem(ErrorCodeHandler.UNKNOWN_EXCEPTION);
                }

                // Convert JSONObject to Object
                TrackItem mailItem = GSONUtil.fromJSON(JSONFormat, TrackItem.class);
                retrieveItem.add(mailItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retrieveItem;
        }
    };

    @Override
    public void onMailReceive(List<IMailItem> trackItemList) {
        Log.d(TAG, "send track onMailReceive:" + trackItemList.size());
        if (trackItemList.size() > 0 && trackItemList.get(0) != null && trackItemList.get(0) instanceof TrackItem) {
            TrackItem trackItem = (TrackItem) trackItemList.get(0);
            onResultReceived(trackItem);
        } else {
            onFailed(ErrorCodeHandler.NO_MAILITEM_DATA.getCode(),
                    ErrorCodeHandler.NO_MAILITEM_DATA.toString());
        }
    }

    abstract public void onResultReceived(TrackItem trackItem);
    abstract public void onFailed(String status, String message);
}