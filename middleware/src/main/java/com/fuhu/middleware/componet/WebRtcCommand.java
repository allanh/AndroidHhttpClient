package com.fuhu.middleware.componet;

import android.content.Context;

import com.fuhu.middleware.contract.ICommandVisitor;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IPostOfficeProxy;
import com.fuhu.middleware.contract.IPostOfficeVisitor;
import com.fuhu.middleware.contract.IWebRtcCommand;
import com.fuhu.middleware.contract.ISchedulingActionProxy;
import com.fuhu.middleware.contract.SilkMessageType;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class WebRtcCommand implements IWebRtcCommand {
    /** The unique identifier of the request */
    private String mId;

    /** The class of the data object */
    private Class<? extends AMailItem> mDataModel;

    /** The data object of the request */
    private AMailItem mDataObject;

    /** The json object of the request */
    private JSONObject mJsonObject;

    /** The data part map of the request */
    private Map<String, DataPart> mDataPartMap;

    /** Whether or not using mock data */
    private boolean mUseMockData = false;

    /** Whether or not responses to this request should be cached */
    private boolean shouldCache = true;

    /** The message type of the request */
    private SilkMessageType mSilkMessageType;

    public WebRtcCommand(WebRtcCommandBuilder builder) {
        this.mId = builder.getID() != null ? builder.getID() : String.valueOf(System.currentTimeMillis());
        this.mDataModel = builder.getDataModel();
        this.mDataObject = builder.getDataObject();
        this.mJsonObject = builder.getJSONObject();
        this.mUseMockData = builder.useMockData();
        this.shouldCache = builder.shouldCache();
        this.mDataPartMap = builder.getDataPartMap();
        this.mSilkMessageType = builder.getSilkMessageType();
    }

    @Override
    public String getID() {
        return mId;
    }

    @Override
    public String getAddress() {
        return mId;
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
    public Map<String, DataPart> getDataPartMap() {
        return mDataPartMap;
    }

    @Override
    public boolean useMockData() {
        return mUseMockData;
    }

    @Override
    public boolean shouldCache() {
        return shouldCache;
    }

    @Override
    public SilkMessageType getSilkMessageType() {
        return mSilkMessageType;
    }

    @Override
    public boolean isValid(ICommandVisitor iCommandVisitor) {
        return iCommandVisitor.isValid(this);
    }

    @Override
    public String getMD5Key(IMD5Visitor imd5Visitor) {
        return imd5Visitor.getKey(this);
    }

    @Override
    public void sendRequest(Context context, IPostOfficeVisitor iPostOfficeVisitor) {
        iPostOfficeVisitor.sendRequest(context, this);
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
