package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IJsonVisitor;
import com.fuhu.middleware.contract.PayloadType;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IWebRtcResponse;
import com.fuhu.middleware.contract.SilkMessageType;

public class MockWebRtcResponse implements IWebRtcResponse {
    private String type;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private String body;
    private PayloadType payloadType;

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

    public MockWebRtcResponse setType(SilkMessageType silkMessageType) {
        if (silkMessageType != null) {
            this.type = silkMessageType.toString();
        }
        return this;
    }

    /** The message type for device communication */
    public PayloadType getPayloadType() {
        return payloadType;
    }

    public MockWebRtcResponse setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    @Override
    public String genMD5Key(IMD5Visitor imd5Visitor) {
        return imd5Visitor.genKey(this);
    }

    @Override
    public AMailItem parseJson(IJsonVisitor iJsonVisitor, ICommand command) {
        return iJsonVisitor.parse(this, command);
    }
}
