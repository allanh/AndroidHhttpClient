package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.GSONUtil;
import com.fuhu.middleware.contract.ICommandBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpCommandBuilder implements ICommandBuilder {
    private Priority priority;
    private String url;
    private int method;
    private Map<String, String> headers;
    
    private String id;
    private Class<? extends AMailItem> dataModel;
    private AMailItem dataObject;
    private JSONObject jsonObject;
    private boolean useMockData;
    private boolean shouldCache;
    private Map<String, DataPart> dataPartMap;

    public HttpCommandBuilder() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.useMockData = false;
        this.shouldCache = true;
        this.priority = Priority.NORMAL;
        this.method = HttpCommand.Method.GET;
        this.headers = HTTPHeader.getDefaultHeader();
    }

    private HttpCommandBuilder(HttpCommand httpCommand) {
        this.priority = httpCommand.getPriority();
        this.url = httpCommand.getURL();
        this.method = httpCommand.getMethod();
        this.headers = httpCommand.getHeaders();
        this.id = httpCommand.getID();

        this.dataModel = httpCommand.getDataModel();
        this.dataObject = httpCommand.getDataObject();
        this.jsonObject = httpCommand.getJSONObject();
        this.useMockData = httpCommand.useMockData();
        this.shouldCache = httpCommand.shouldCache();
        this.dataPartMap = httpCommand.getDataPartMap();
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
     * Sets the Method of this request.
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
    public HttpCommandBuilder setHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers = headers;
        } else {
            this.headers = HTTPHeader.getDefaultHeader();
        }
        return this;
    }

    public HttpCommandBuilder addHeader(String key, String value) {
        if (this.headers != null) {
            if (this.headers.containsKey(key)) {
                this.headers.remove(key);
            }
            this.headers.put(key, value);
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


    /**
     * Attaches id to the request. It can be used later to cancel the request. If the id
     * is less than zero, the request is canceled by using the current time in milliseconds
     * since January 1, 1970 00:00:00.0 UTC as the tag.
     */
    @Override
    public HttpCommandBuilder setID(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getID() {
        return id;
    }

    /**
     * Sets the class of the dataObject
     */
    @Override
    public HttpCommandBuilder setDataModel(Class<? extends AMailItem> dataModel) {
        this.dataModel = dataModel;
        return this;
    }

    @Override
    public Class<? extends AMailItem> getDataModel() {
        return dataModel;
    }

    /**
     * Sets a AMailItem of parameters to be used for a POST or PUT request.
     */
    @Override
    public HttpCommandBuilder setDataObject(AMailItem mailItem, String... keys) {
        this.dataObject = mailItem;
        if (mailItem != null) {
            try {
                if (keys != null && keys.length > 0) {
                    this.jsonObject = GSONUtil.toJSON(mailItem, keys);
                } else {
                    // convert all key-value pairs
                    this.jsonObject = GSONUtil.toJSON(mailItem);
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return this;
    }

    public HttpCommandBuilder setDataObject(AMailItem mailItem) {
        this.dataObject = mailItem;
        if (mailItem != null) {
            // convert all key-value pairs
            try {
                this.jsonObject = GSONUtil.toJSON(mailItem);
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public AMailItem getDataObject() {
        return dataObject;
    }

    /**
     * Sets a JSONObject of parameters to be used for a POST or PUT request.
     */
    @Override
    public HttpCommandBuilder setJSONObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    @Override
    public JSONObject getJSONObject() {
        return jsonObject;
    }

    /**
     * Whether or not the mock data is using
     */
    @Override
    public HttpCommandBuilder useMockData(boolean use) {
        this.useMockData = use;
        return this;
    }

    @Override
    public boolean useMockData() {
        return useMockData;
    }

    /**
     * Set whether or not responses to this request should be cached
     * @param shouldCache
     * @return
     */
    @Override
    public HttpCommandBuilder setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
        return this;
    }

    @Override
    public boolean shouldCache() {
        return shouldCache;
    }

    public HttpCommandBuilder addDataPart(String key, DataPart dataPart) {
        if (key != null && dataPart != null && dataPart.getFile() != null) {
            if (this.dataPartMap == null) {
                this.dataPartMap = new HashMap<String, DataPart>();
            }

            dataPartMap.put(key, dataPart);
        }

        return this;
    }

    @Override
    public HttpCommandBuilder setDataPartMap(Map<String, DataPart> dataPartMap) {
        this.dataPartMap = dataPartMap;
        return this;
    }

    @Override
    public Map<String, DataPart> getDataPartMap() {
        return dataPartMap;
    }

    public HttpCommand build() {
        if (url == null) throw new IllegalStateException("url == null");
        if (dataModel ==null) throw new IllegalStateException("dataModel == null");
        return new HttpCommand(this);
    }
}