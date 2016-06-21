package com.fuhu.middleware.contract;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.DataPart;
import com.fuhu.middleware.componet.ErrorCodeList;

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

    /**
     * Checking if the command is valid using ICommandVisitor
     * @param iCommandVisitor
     * @return
     */
    public boolean isValid(ICommandVisitor iCommandVisitor);

    /**
     * Gets the MD5 Key of this request
     * @param imd5Visitor
     * @return
     */
    public String getMD5Key(IMD5Visitor imd5Visitor);

    /**
     * Send this request to Server
     * @param iPostOfficeVisitor
     */
    public void sendRequest(Context context, IPostOfficeVisitor iPostOfficeVisitor);

    public Object doAction(final Context mContext, final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public Object doNextAction(final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public void onCommandComplete(final IPostOfficeProxy mPostOfficeProxy, final ISchedulingActionProxy mISchedulingActionProxy, final AMailItem queryITem, final List<AMailItem> result, final Object... parameters);
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeList errorCode);
}
