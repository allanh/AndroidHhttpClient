package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.GSONUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageItem extends AMailItem {
    private String deviceId;
    private String payloadId;
    private AMailItem payload;
    private Class<? extends AMailItem> payloadModel;

    /**
     * Creates a new {@code JSONObject} with name/value mappings from the JSON
     * string.
     * @throws JSONException
     */
    public JSONObject toJSONObject(Gson gson) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.getType());
        jsonObject.put("deviceId", this.deviceId);
        jsonObject.put("payloadId", this.payloadId);

        if (payload != null) {
            JSONObject payloadJson = GSONUtil.toJSON(payload);
            jsonObject.put("payload", payloadJson);
        }
        return jsonObject;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets the payload id for this MailItem. {@link com.fuhu.middleware.contract.PayloadType}
     * @return
     */
    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    /**
     * Gets the payload
     */
    public AMailItem getPayload() {
        return payload;
    }

    /**
     * Sets the payload and payload model
     */
    public void setPayload(AMailItem payload) {
        this.payload = payload;

        //
        if (payload != null) {
            payloadModel = payload.getClass();
        }
    }

    /**
     * Gets the data model of payload
     */
    public Class<? extends AMailItem> getPayloadModel() {
        return payloadModel;
    }

    /**
     * Sets the data model of payload
     */
    public void setPayloadModel(Class<? extends AMailItem> payloadModel) {
        this.payloadModel = payloadModel;
    }
}
