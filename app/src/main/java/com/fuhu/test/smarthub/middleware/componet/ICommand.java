package com.fuhu.test.smarthub.middleware.componet;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public interface ICommand extends Serializable{
    public String getAddress();
    public Object doAction(final Context mContext, final IMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public Object doNextAction(final IMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public void onCommandComplete(final IPostOfficeProxy mPostOfficeProxy, final ISchedulingActionProxy mISchedulingActionProxy, final IMailItem queryITem, final List<IMailItem> result, final Object... parameters);
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, IMailItem queryItem, ErrorCodeHandler errorCode);
    public int getID();
    public JSONObject genJson(final AMailItem queryItem);
}
