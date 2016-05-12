package com.fuhu.test.smarthub.middleware.componet;

import android.content.Context;

import java.util.List;

public interface IPostOfficeProxy {
    public void onMailItemUpdate(final ICommand myCommand, final AMailItem queryITem, final List<AMailItem> result, final Object... parameters);
//    public void onMailRequest(final Context mContext, final IMailItem queryItem, final MailTask mMailTask, final Object... parameter);
    public void onMailRequest(final Context mContext, final MailTask mMailTask);
    public void onMailDeliver(final ICommand mCommand, final boolean isForceDelivery, final Object... parameter);
}