package com.fuhu.test.smarthub.middleware.componet;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

import java.io.Serializable;
import java.util.List;

public interface ICommand extends Serializable{
    public String getID();
    public String getAddress();
    public Object doAction(final Context mContext, final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public Object doNextAction(final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj);
    public void onCommandComplete(final IPostOfficeProxy mPostOfficeProxy, final ISchedulingActionProxy mISchedulingActionProxy, final AMailItem queryITem, final List<AMailItem> result, final Object... parameters);
    public void onCommandFailed(final Context mContext, IPostOfficeProxy mPostOfficeProxy, AMailItem queryItem, ErrorCodeHandler errorCode);
}
