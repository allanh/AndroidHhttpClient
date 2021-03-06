package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.PayloadType;
import com.fuhu.middleware.contract.GSONUtil;
import com.fuhu.middleware.contract.ICommandBuilder;
import com.fuhu.middleware.contract.SilkMessageType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebRtcCommandBuilder implements ICommandBuilder {
    private static final String TAG = WebRtcCommandBuilder.class.getSimpleName();
    private String id;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private JSONObject jsonObject;
    private boolean useMockData;
    private boolean shouldCache;
    private Map<String, DataPart> dataPartMap;
    private SilkMessageType mSilkMessageType;
    private PayloadType mPayloadType;
    private Class<? extends AMailItem> mPayloadModel;

    public WebRtcCommandBuilder() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.useMockData = false;
        this.shouldCache = true;
    }

    private WebRtcCommandBuilder(WebRtcCommand webRtpCommand) {
        this.id = webRtpCommand.getID();
        this.dataModel = webRtpCommand.getDataModel();
        this.dataObject = webRtpCommand.getDataObject();
        this.jsonObject = webRtpCommand.getJSONObject();
        this.useMockData = webRtpCommand.useMockData();
        this.shouldCache = webRtpCommand.shouldCache();
        this.dataPartMap = webRtpCommand.getDataPartMap();
        this.mSilkMessageType = webRtpCommand.getSilkMessageType();
        this.mPayloadType = webRtpCommand.getPayloadType();
        this.mPayloadModel = webRtpCommand.getPayloadModel();
    }

    /**
     * Attaches id to the request. It can be used later to cancel the request. If the id
     * is less than zero, the request is canceled by using the current time in milliseconds
     * since January 1, 1970 00:00:00.0 UTC as the tag.
     */
    @Override
    public WebRtcCommandBuilder setID(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getID() {
        return id;
    }

    /**
     * Sets the data model of the dataObject
     */
    @Override
    public WebRtcCommandBuilder setDataModel(Class<? extends AMailItem> dataModel) {
        this.dataModel = dataModel;
        return this;
    }

    @Override
    public Class<? extends AMailItem> getDataModel() {
        return dataModel;
    }

    /**
     * Sets a AMailItem of parameters to be used for a POST or PUT request.
     */
    @Override
    public WebRtcCommandBuilder setDataObject(AMailItem mailItem, String... keys) {
        this.dataObject = mailItem;
        if (mailItem != null) {
            // Get message type for Silk SDK
            SilkMessageType messageType = SilkMessageType.lookup(mailItem.getType());
            if (messageType != null) {
                this.mSilkMessageType = messageType;
            }

            // Checks if the message type is {@link SilkMessageType#DEVICE_MESSAGE}
            if (SilkMessageType.DEVICE_MESSAGE.equals(messageType)) {
                return setDataObject(mailItem);
            }

            try {
                this.jsonObject = GSONUtil.toJSON(mailItem, keys);
            } catch (JSONException je) {
                je.printStackTrace();
            }

            Log.d(TAG, "json: " + this.jsonObject.toString());
        }
        return this;
    }

    public WebRtcCommandBuilder setDataObject(AMailItem mailItem) {
        this.dataObject = mailItem;
        if (mailItem != null) {
            // Get message type for Silk SDK
            SilkMessageType messageType = SilkMessageType.lookup(mailItem.getType());
            if (messageType != null) {
                this.mSilkMessageType = messageType;
            }

            /**
             * Checks if the message type is {@link SilkMessageType#DEVICE_MESSAGE}
             * to set the payload type and data model of payload
             */
            if (SilkMessageType.DEVICE_MESSAGE.equals(messageType)) {
                MessageItem messageItem = (MessageItem) mailItem;
                setPayloadType(messageItem.getPayloadId());
                setPayloadModel(messageItem.getPayloadModel());
            }

            // convert all key-value pairs
            try {
                this.jsonObject = GSONUtil.toJSON(mailItem);
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public AMailItem getDataObject() {
        return dataObject;
    }

    /**
     * Sets a JSONObject of parameters to be used for a POST or PUT request.
     */
    @Override
    public WebRtcCommandBuilder setJSONObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        // parsing message type for Silk SDK
        if (jsonObject != null && jsonObject.has("type")) {
            try {
                String type = jsonObject.getString("type");
                SilkMessageType messageType = SilkMessageType.lookup(type);
                if (messageType != null) {
                    this.mSilkMessageType = messageType;
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public JSONObject getJSONObject() {
        return jsonObject;
    }

    /**
     * Whether or not the mock data is using
     */
    @Override
    public WebRtcCommandBuilder useMockData(boolean use) {
        this.useMockData = use;
        return this;
    }

    @Override
    public boolean useMockData() {
        return useMockData;
    }

    /**
     * Set whether or not responses to this request should be cached
     * @param shouldCache
     * @return
     */
    @Override
    public WebRtcCommandBuilder setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    @Override
    public boolean shouldCache() {
        return shouldCache;
    }

    public WebRtcCommandBuilder addDataPart(String key, DataPart dataPart) {
        if (key != null && dataPart != null && dataPart.getFile() != null) {
            if (this.dataPartMap == null) {
                this.dataPartMap = new HashMap<String, DataPart>();
            }

            dataPartMap.put(key, dataPart);
        }

        return this;
    }

    @Override
    public WebRtcCommandBuilder setDataPartMap(Map<String, DataPart> dataPartMap) {
        this.dataPartMap = dataPartMap;
        return this;
    }

    @Override
    public Map<String, DataPart> getDataPartMap() {
        return dataPartMap;
    }

    /**
     * Gets the silk message type of the command
     */
    public SilkMessageType getSilkMessageType() {
        return  mSilkMessageType;
    }

    /**
     * Sets the silk message type of the command
     */
    public WebRtcCommandBuilder setSilkMessageType(SilkMessageType silkMessageType) {
        this.mSilkMessageType = silkMessageType;
        return this;
    }

    /**
     * Gets the payload type of the command
     */
    public PayloadType getPayloadType() {
        return mPayloadType;
    }

    /**
     * Sets the payload type of the command
     * @param payloadId payload string
     */
    public WebRtcCommandBuilder setPayloadType(String payloadId) {
        this.mPayloadType = PayloadType.lookup(payloadId);
        return this;
    }

    /**
     * Sets the payload type of the command
     * @param payloadType payload object
     */
    public WebRtcCommandBuilder setPayloadType(PayloadType payloadType) {
        this.mPayloadType = payloadType;
        return this;
    }

    /**
     * Gets the data model of the payload data
     */
    public Class<? extends AMailItem> getPayloadModel() {
        return mPayloadModel;
    }

    /**
     * Sets the data model of the payload data
     */
    public WebRtcCommandBuilder setPayloadModel(Class<? extends AMailItem> payloadModel) {
        this.mPayloadModel = payloadModel;
        return this;
    }

    public WebRtcCommand build() {
        if (mSilkMessageType == null) throw new IllegalStateException("Invalid message type");
        if (dataModel ==null) throw new IllegalStateException("dataModel == null");
        if (SilkMessageType.DEVICE_MESSAGE.equals(mSilkMessageType)
                && (mPayloadType == null || mPayloadModel == null)) {
            throw new IllegalStateException("Invalid Payload parameters");
        }
        return new WebRtcCommand(this);
    }

}
