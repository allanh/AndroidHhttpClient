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
