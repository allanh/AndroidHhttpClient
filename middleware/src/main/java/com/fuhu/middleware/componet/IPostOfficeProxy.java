package com.fuhu.middleware.componet;

import android.content.Context;

public interface IPostOfficeProxy {
    public void onMailItemUpdate(final ICommand myCommand, final AMailItem queryITem, final AMailItem result, final Object... parameters);
//    public void onMailRequest(final Context mContext, final IMailItem queryItem, final MailTask mMailTask, final Object... parameter);
    public void onMailRequest(final Context mContext, final MailTask mMailTask);
    public void onMailDeliver(final ICommand mCommand, final boolean isForceDelivery, final Object... parameter);
}