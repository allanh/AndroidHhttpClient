package com.fuhu.test.smarthub.middleware.componet;

import java.util.List;

public interface IMailReceiveCallback {
    public void onMailReceive(List<IMailItem> mailItemList);
}