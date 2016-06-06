package com.fuhu.middleware.componet;

import org.json.JSONObject;

import java.util.Map;

public interface ICommandBuilder {
    /**
     * Attaches id to the request. It can be used later to cancel the request. If the id
     * is less than zero, the request is canceled by using the current time in milliseconds
     * since January 1, 1970 00:00:00.0 UTC as the tag.
     */
    public ICommandBuilder setID(String id);
    public String getID();

    /**
     * Sets the {@link Priority} of this request; {@link Priority#NORMAL} by default.
     */
    public ICommandBuilder setPriority(Priority priority);
    public Priority getPriority();

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
    public ICommandBuilder setHeaders(Map<String, String> headers);
    public ICommandBuilder addHeader(String Key, String value);
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
     * Sets the class of the dataObject
     */
    public ICommandBuilder setDataModel(Class<? extends AMailItem> dataModel);
    public Class<? extends AMailItem> getDataModel();

    /**
     * Whether or not the mock data is using
     * @param use
     * @return
     */
    public ICommandBuilder useMockData(boolean use);
    public boolean useMockData();

    /**
     * Set whether or not responses to this request should be cached
     * @param shouldCache
     * @return
     */
    public ICommandBuilder setShouldCache(boolean shouldCache);
    public boolean shouldCache();

    public ICommandBuilder setDataPartMap(Map<String, DataPart> dataPartMap);
    public Map<String, DataPart> getDataPartMap();

    public ICommand build();
}
