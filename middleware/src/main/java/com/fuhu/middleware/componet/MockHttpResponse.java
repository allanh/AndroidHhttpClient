package com.fuhu.middleware.componet;


import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpResponse;
import com.fuhu.middleware.contract.IJsonVisitor;
import com.fuhu.middleware.contract.IMD5Visitor;

/**
 * A response for making a mock response
 */
public class MockHttpResponse implements IHttpResponse {
    private String url;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private String body;

    /**
     * Default constructor
     */
    public MockHttpResponse() {
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
    public MockHttpResponse setURL(String url) {
        this.url = url;
        return this;
    }

    public Class<? extends AMailItem> getDataModel() {
        return dataModel;
    }

    public AMailItem getDataObject() {
        return dataObject;
    }

    public MockHttpResponse setDataObject(AMailItem dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    public String getBody() {
        return body;
    }

    /** @deprecated */
    @Deprecated
    public MockHttpResponse setBody(Class<? extends AMailItem> dataModel, String body) {
        this.dataModel = dataModel;
        this.body = body;
        return this;
    }

    public MockHttpResponse setBody(String body) {
        this.body = body;
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
