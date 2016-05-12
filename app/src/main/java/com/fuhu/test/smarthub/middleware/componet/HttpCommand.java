package com.fuhu.test.smarthub.middleware.componet;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;


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
    private Class<? extends AMailItem> mDataClass;

    /** The data object of the request */
    private AMailItem mDataObject;

    /** The json object of the request */
    private JSONObject mJsonObject;

    private SSLSocketFactory mSslSocketFactory;

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
        this.mId = builder.getID() != null ? builder.getID() : String.valueOf(System.currentTimeMillis() % 60000);
        this.mPriority = builder.getPriority();
        this.mUrl = builder.getURL();
        this.mMethod = builder.getMethod();
        this.mHttpHeaders = builder.getHeaders();
        this.mDataClass = builder.getDataClass();
        this.mDataObject = builder.getDataObject();
        this.mJsonObject = builder.getJSONObject();
        this.mSslSocketFactory = builder.getSSLSocketFactory();
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

    public Class<? extends AMailItem> getDataClass() {
        return mDataClass;
    }

    public AMailItem getDataObject() {
        return mDataObject;
    }

    public JSONObject getJSONObject() {
        return mJsonObject;
    }

    public SSLSocketFactory getSSlSocketFactory() {
        return mSslSocketFactory;
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
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeHandler errorCode) {}
}
