package com.fuhu.middleware.control;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.MessageItem;
import com.fuhu.middleware.componet.WebRtcCommand;
import com.fuhu.middleware.contract.GSONUtil;
import com.fuhu.middleware.contract.IBleResponse;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpResponse;
import com.fuhu.middleware.contract.IJsonVisitor;
import com.fuhu.middleware.contract.IResponse;
import com.fuhu.middleware.contract.IWebRtcResponse;
import com.fuhu.middleware.contract.SilkMessageType;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonVisitor implements IJsonVisitor {
    private static final String TAG = JsonVisitor.class.getSimpleName();
    /**
     * Parsing the json string for IResponse
     */
    public AMailItem parse(IResponse response, ICommand command) {
        try {
            return GSONUtil.fromJSON(response.getBody(), command.getDataModel());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
    }

    /**
     * Parsing the json string for HttpResponse
     */
    public AMailItem parse(IHttpResponse response, ICommand command) {
        try {
            return GSONUtil.fromJSON(response.getBody(), command.getDataModel());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
    }

    /**
     * Parsing the json string for WebRtcResponse
     */
    public AMailItem parse(IWebRtcResponse response, ICommand command) {
        try {
            WebRtcCommand rtcCommand = (WebRtcCommand) command;

            // Checks if the message type is DEVICE_MESSAGE
            if (SilkMessageType.DEVICE_MESSAGE.equals(rtcCommand.getSilkMessageType())) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                MessageItem item = new MessageItem();

                item.setType(SilkMessageType.DEVICE_MESSAGE.toString());

                if (jsonObject.has("deviceId")) {
                    item.setDeviceId(jsonObject.getString("deviceId"));
                }

                if (jsonObject.has("payloadId")) {
                    item.setPayloadId(jsonObject.getString("payloadId"));
                }

                if (jsonObject.has("payload")) {
                    String payloadString = jsonObject.getJSONObject("payload").toString();
                    item.setPayload(GSONUtil.fromJSON(payloadString, rtcCommand.getPayloadModel()));
                }
                return item;
            } else { // Other message
                return GSONUtil.fromJSON(response.getBody(), command.getDataModel());
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
    }

    /**
     * Parsing the json string for BleResponse
     */
    public AMailItem parse(IBleResponse response, ICommand command) {
        try {
            return GSONUtil.fromJSON(response.getBody(), command.getDataModel());
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
    }
}
