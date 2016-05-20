package com.fuhu.middleware.componet;

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
        return dataObject;
    }

    public MockResponse setDataObject(Class<? extends AMailItem> dataModel, AMailItem dataObject) {
        this.dataModel = dataModel;
        this.dataObject = dataObject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public MockResponse setBody(String body) {
        this.body = body;
        return this;
    }
}
