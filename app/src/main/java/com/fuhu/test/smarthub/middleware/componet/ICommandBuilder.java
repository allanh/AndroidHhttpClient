package com.fuhu.test.smarthub.middleware.componet;

import org.json.JSONObject;

import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

public interface ICommandBuilder {
    /**
     * Attaches id to the request. It can be used later to cancel the request. If the id
     * is less than zero, the request is canceled by using the current time in milliseconds
     * since January 1, 1970 00:00:00.0 UTC as the tag.
     */
    public ICommandBuilder setID(int id);
    public int getID();

    /**
     * Sets the {@link HttpCommand.Priority} of this request; {@link HttpCommand.Priority#NORMAL} by default.
     */
    public ICommandBuilder setPriority(HttpCommand.Priority priority);
    public HttpCommand.Priority getPriority();

    /**
     * Sets the {@link HttpCommand.Method} of this request.
     */
    public ICommandBuilder setMethod(int method);
    public int getMethod();

    /**
     * Sets the URL target of this request.
     */
    public ICommandBuilder setURL(String url);
    public String getURL();

    /**
     * Sets the headers. If this request already has any headers
     * with that name, they are all replaced.
     */
    public ICommandBuilder setHeader(Map<String, String> headers);
    public ICommandBuilder removeHeader(String headerKey);
    public Map<String, String> getHeaders();

    /**
     * Sets a AMailItem of parameters to be used for a POST or PUT request.
     */
    public ICommandBuilder setDataObject(AMailItem mailItem, String... keys);
    public AMailItem getDataObject();

    /**
     * Sets a JSONObject of parameters to be used for a POST or PUT request.
     */
    public ICommandBuilder setJSONObject(JSONObject jsonObject);
    public JSONObject getJSONObject();

        /**
         * Sets the socket factory used to secure HTTPS connections.
         *
         * <p>If unset, a lazily created SSL socket factory will be used.
         */
    public ICommandBuilder setSSLSocketFactory(SSLSocketFactory factory);
    public SSLSocketFactory getSSLSocketFactory();

    /**
     * Sets a JsonParser to parse the JSONObject from server.
     * @param jsonParser
     * @return
     */
    public ICommandBuilder setJsonParser(IJsonParser jsonParser);
    public IJsonParser getJsonParser();

    public ICommand build();
}
