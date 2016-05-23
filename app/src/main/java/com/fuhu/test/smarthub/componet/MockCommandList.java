package com.fuhu.test.smarthub.componet;

import android.content.Context;

import com.fuhu.middleware.MiddlewareApp;
import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.ErrorCodeList;
import com.fuhu.middleware.componet.HTTPHeader;
import com.fuhu.middleware.componet.IHttpCommand;
import com.fuhu.middleware.componet.IPostOfficeProxy;
import com.fuhu.middleware.componet.ISchedulingActionProxy;
import com.fuhu.middleware.componet.Priority;
import com.fuhu.middleware.contract.NabiHttpRequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public enum MockCommandList implements IHttpCommand {
    ReqTracking("1", IHttpCommand.Method.POST, NabiHttpRequest.getAPI_Track(), TrackItem.class) {
        @Override
        public Map<String, String> getHeaders() {
            return HTTPHeader.getTrackingHeader(MiddlewareApp.getApplicationContext());
        }

        @Override
        public AMailItem getDataObject() {
            TrackItem trackItem = new TrackItem();
            return trackItem;
        }
    },

    ReqSendToIFTTT("2", IHttpCommand.Method.POST, NabiHttpRequest.getAPI_IFTTT(), IFTTTItem.class) {
        @Override
        public Map<String, String> getHeaders() {
            return HTTPHeader.getDefaultHeader();
        }

        @Override
        public AMailItem getDataObject() {
            IFTTTItem iftttItem = new IFTTTItem();
            iftttItem.setValue1("testIFTTT");
            return iftttItem;
        }
    }
    ;

    /** The unique identifier of the request */
    private String mId;

    /** Priority value */
    private Priority mPriority;

    /** URL of this request */
    private String mUrl;

    /**
     * Request method of this request.
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

    private MockCommandList(String id, int method, String url, Class<? extends AMailItem> dataModel) {
        this.mId = id;
        this.mMethod = method;
        this.mUrl = url;
        this.mDataModel = dataModel;
    }

    @Override
    public String getID() {
        return mId;
    }

    @Override
    public String getURL() {
        return mUrl;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    @Override
    public int getMethod() {
        return mMethod;
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHttpHeaders;
    }

    @Override
    public Class<? extends AMailItem> getDataModel() {
        return mDataModel;
    }

    @Override
    public AMailItem getDataObject() {
        return mDataObject;
    }

    @Override
    public JSONObject getJSONObject() {
        return mJsonObject;
    }

    @Override
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
