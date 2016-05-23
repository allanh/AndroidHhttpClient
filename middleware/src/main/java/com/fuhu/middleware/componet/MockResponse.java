package com.fuhu.middleware.componet;


import com.fuhu.middleware.contract.GSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MockResponse implements IResponse {
    private String url;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private String body;

    /** Creates a new mock response with an empty body. */
    public MockResponse() {
        body = "{\"status\":\"0\"";
    }

    public String getURL() {
        return url;
    }

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

    public MockResponse setBody(Class<? extends AMailItem> dataModel, String body) {
        this.dataModel = dataModel;
        this.body = body;
        return this;
    }
}
