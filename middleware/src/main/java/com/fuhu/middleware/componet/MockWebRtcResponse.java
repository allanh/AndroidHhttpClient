package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IWebRtcResponse;

public class MockWebRtcResponse implements IWebRtcResponse {
    private String type;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private String body;

    /**
     * Default constructor
     */
    public MockWebRtcResponse() {
        body = "{\"status\":\"0\"}";
    }

    @Override
    public Class<? extends AMailItem> getDataModel() {
        return dataModel;
    }

    @Override
    public AMailItem getDataObject() {
        return dataObject;
    }

    public MockWebRtcResponse setDataObject(AMailItem dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    @Override
    public String getBody() {
        return body;
    }

    public MockWebRtcResponse setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String getType() {
        return type;
    }

    public MockWebRtcResponse setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String genMD5Key(IMD5Visitor imd5Visitor) {
        return imd5Visitor.genKey(this);
    }
}
