package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.AMailItem;

public interface IMailReceiveCallback {
    public void onMailReceive(AMailItem mailItem);
}