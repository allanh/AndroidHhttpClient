package com.fuhu.middleware.contract;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;
import com.fuhu.middleware.componet.MailTask;

public interface IPostOfficeProxy {
    public void onMailItemUpdate(final ICommand myCommand, final AMailItem queryITem, final AMailItem result, final Object... parameters);
//    public void onMailRequest(final Context mContext, final IMailItem queryItem, final MailTask mMailTask, final Object... parameter);
    public void onMailRequest(final Context mContext, final MailTask mMailTask);
    public void onMailDeliver(final ICommand mCommand, final boolean isForceDelivery, final Object... parameter);
}