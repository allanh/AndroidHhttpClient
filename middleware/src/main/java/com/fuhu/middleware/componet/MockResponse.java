package com.fuhu.middleware.componet;


import com.fuhu.middleware.contract.ErrorCodeHandler;
import com.fuhu.middleware.contract.GSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A response for making a mock response
 */
public class MockResponse implements IResponse {
    private String url;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private String body;

    /**
     * Default constructor
     */
    public MockResponse() {
        body = "{\"status\":\"0\"}";
    }

    public String getURL() {
        return url;
    }

    /**
     * Add a parameter to be sent in the multipart request
     *
     * @param url The url of the paramter
     * @return The Multipart request for chaining calls
     */
    public MockResponse setURL(String url) {
        this.url = url;
        return this;
    }

    public Class<? extends AMailItem> getDataModel() {
        return dataModel;
    }

    public AMailItem getDataObject() {
        if (dataObject != null) {
            return dataObject;
        } else if (dataModel != null && body != null) {
            try {
                return GSONUtil.fromJSON(new JSONObject(body), dataModel);
            } catch (JSONException je) {
                je.printStackTrace();
                return ErrorCodeHandler.genErrorItem(ErrorCodeList.GSON_PARSE_ERROR);
            }
        }
        return null;
    }

    public MockResponse setDataObject(AMailItem dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    public String getBody() {
        return body;
    }

    /** @deprecated */
    @Deprecated
    public MockResponse setBody(Class<? extends AMailItem> dataModel, String body) {
        this.dataModel = dataModel;
        this.body = body;
        return this;
    }

    public MockResponse setBody(String body) {
        this.body = body;
        return this;
    }
}
