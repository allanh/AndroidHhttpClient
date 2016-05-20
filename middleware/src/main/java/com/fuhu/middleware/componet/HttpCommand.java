package com.fuhu.middleware.componet;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class HttpCommand implements ICommand {
    /** The unique identifier of the request */
    private String mId;

    /** Priority value */
    private Priority mPriority;

    /** URL of this request */
    private String mUrl;

    /**
     * Request method of this request.  Currently supports GET, POST, PUT, DELETE, HEAD, OPTIONS,
     * TRACE, and PATCH.
     */
    private int mMethod;

    /** HTTP headers of the request */
    private Map<String, String> mHttpHeaders;

    /** The class of the data object */
    private Class<? extends AMailItem> mDataModel;

    /** The data object of the request */
    private AMailItem mDataObject;

    /** The json object of the request */
    private JSONObject mJsonObject;

    private boolean mUseMockData = false;

    /**
     * Priority values.  Requests will be processed from higher priorities to
     * lower priorities, in FIFO order.
     */
    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    /**
     * Supported request methods.
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    public HttpCommand(ICommandBuilder builder) {
        this.mId = builder.getID() != null ? builder.getID() : String.valueOf(System.currentTimeMillis());
        this.mPriority = builder.getPriority();
        this.mUrl = builder.getURL();
        this.mMethod = builder.getMethod();
        this.mHttpHeaders = builder.getHeaders();
        this.mDataModel = builder.getDataModel();
        this.mDataObject = builder.getDataObject();
        this.mJsonObject = builder.getJSONObject();
        this.mUseMockData = builder.useMockData();
    }

    @Override
    public String getID() {
        return mId;
    }

    public String getURL() {
        return mUrl;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public int getMethod() {
        return mMethod;
    }

    public Map<String, String> getHeaders() {
        return mHttpHeaders;
    }

    public Class<? extends AMailItem> getDataModel() {
        return mDataModel;
    }

    public AMailItem getDataObject() {
        return mDataObject;
    }

    public JSONObject getJSONObject() {
        return mJsonObject;
    }

    public boolean useMockData() {
        return mUseMockData;
    }

    @Override
    public String getAddress() {
        return mId;
    }

    @Override
    public Object doAction(final Context mContext, final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj) {
        return null;
    }

    @Override
    public Object doNextAction(final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj) {
        return null;
    }

    @Override
    public void onCommandComplete(final IPostOfficeProxy mPostOfficeProxy, final ISchedulingActionProxy mISchedulingActionProxy, final AMailItem queryITem, final List<AMailItem> result, final Object... parameters) {}

    @Override
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeList errorCode) {}
}
