package com.fuhu.middleware.componet;

import android.content.Context;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ICommand extends Serializable{
    /**
     * Gets the ID of this request
     */
    public String getID();

    /**
     * Gets the address of this request
     */
    public String getAddress();

    /**
     * Gets the data object of this request
     */
    public AMailItem getDataObject();

    /**
     * Gets the json object of this request
     */
    public JSONObject getJSONObject();

    /**
     * Gets the data model of this request
     */
    public Class<? extends AMailItem> getDataModel();

    /**
     * Whether or not the mock data is using
     */
    public boolean useMockData();

    /**
     * responses to this request should be cached
     */
    public boolean shouldCache();

    /**
     * Gets the data part map of this request
     */
    public Map<String, DataPart> getDataPartMap();

    public Object doAction(final Context mContext, final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public Object doNextAction(final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public void onCommandComplete(final IPostOfficeProxy mPostOfficeProxy, final ISchedulingActionProxy mISchedulingActionProxy, final AMailItem queryITem, final List<AMailItem> result, final Object... parameters);
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeList errorCode);
}
