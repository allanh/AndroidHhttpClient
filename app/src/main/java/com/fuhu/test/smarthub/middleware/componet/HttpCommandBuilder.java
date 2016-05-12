package com.fuhu.test.smarthub.middleware.componet;

import com.fuhu.test.smarthub.middleware.componet.HttpCommand.Method;
import com.fuhu.test.smarthub.middleware.componet.HttpCommand.Priority;
import com.fuhu.test.smarthub.middleware.contract.GSONUtil;

import org.json.JSONObject;

import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

public class HttpCommandBuilder implements ICommandBuilder {
    private String id;
    private Priority priority;
    private String url;
    private int method;
    private Map<String, String> headers;
    private Class<? extends AMailItem> dataClass;
    private AMailItem dataObject;
    private JSONObject jsonObject;
    private SSLSocketFactory sslSocketFactory;

    public HttpCommandBuilder() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.priority = Priority.NORMAL;
        this.method = HttpCommand.Method.GET;
        this.headers = HTTPHeader.getDefaultHeader();

        // TODO set default SslSocketFactory
    }

    private HttpCommandBuilder(HttpCommand httpCommand) {
        this.id = httpCommand.getID();
        this.priority = httpCommand.getPriority();
        this.url = httpCommand.getURL();
        this.method = httpCommand.getMethod();
        this.headers = httpCommand.getHeaders();
        this.dataClass = httpCommand.getDataClass();
        this.dataObject = httpCommand.getDataObject();
        this.jsonObject = httpCommand.getJSONObject();
        this.sslSocketFactory = httpCommand.getSSlSocketFactory();
    }

    /**
     * Attaches id to the request. It can be used later to cancel the request. If the id
     * is less than zero, the request is canceled by using the current time in milliseconds
     * since January 1, 1970 00:00:00.0 UTC as the tag.
     */
    public HttpCommandBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public String getID() {
        return id;
    }

    /**
     * Sets the {@link Priority} of this request; {@link Priority#NORMAL} by default.
     */
    public HttpCommandBuilder setPriority(Priority priority) {
        if (priority != null) {
            this.priority = priority;
        } else {
            this.priority = Priority.NORMAL;
        }
        return this;
    }
    
    public Priority getPriority() {
        return priority;
    }


    /**
     * Sets the {@link Method} of this request.
     */
    public HttpCommandBuilder setMethod(int method) {
        this.method = method;
        return this;
    }

    public int getMethod() {
        return method;
    }

    /**
     * Sets the URL target of this request.
     */
    public HttpCommandBuilder setURL(String url) {
        if (url == null) throw new IllegalArgumentException("url == null");
        this.url = url;
        return this;
    }

    public String getURL() {
        return url;
    }

    /**
     * Sets the headers. If this request already has any headers
     * with that name, they are all replaced.
     */
    public HttpCommandBuilder setHeader(Map<String, String> headers) {
        if (headers != null) {
            this.headers = headers;
        } else {
            this.headers = HTTPHeader.getDefaultHeader();
        }
        return this;
    }

    public HttpCommandBuilder removeHeader(String headerKey) {
        if (this.headers != null) {
            if (this.headers.containsKey(headerKey)) {
                this.headers.remove(headerKey);
            }
        }
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Class<? extends AMailItem> getDataClass() {
        return dataClass;
    }

    /**
     * Sets a AMailItem of parameters to be used for a POST or PUT request.
     */
    public HttpCommandBuilder setDataObject(Class<? extends AMailItem> dataClass, AMailItem mailItem, String... keys) {
        this.dataClass = dataClass;
        this.dataObject = mailItem;
        if (mailItem != null) {
            if (keys != null && keys.length > 0) {
                this.jsonObject = GSONUtil.toJSON(mailItem, keys);
            } else {
                // convert all key-value pairs
                this.jsonObject = GSONUtil.toJSON(mailItem);
            }
        }
        return this;
    }

    public AMailItem getDataObject() {
        return dataObject;
    }


    /**
     * Sets a JSONObject of parameters to be used for a POST or PUT request.
     */
    public HttpCommandBuilder setJSONObject(Class<? extends AMailItem> dataClass, JSONObject jsonObject) {
        this.dataClass = dataClass;
        this.jsonObject = jsonObject;
        return this;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }

    /**
     * Sets the socket factory used to secure HTTPS connections.
     *
     * <p>If unset, a lazily created SSL socket factory will be used.
     */
    public HttpCommandBuilder setSSLSocketFactory(SSLSocketFactory factory) {
        if (factory != null) {
            this.sslSocketFactory = factory;
        } else {
            // TODO to created ssl socket factory
        }
        return this;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return sslSocketFactory;
    }

    public HttpCommand build() {
        if (url == null) throw new IllegalStateException("url == null");
        return new HttpCommand(this);
    }
}