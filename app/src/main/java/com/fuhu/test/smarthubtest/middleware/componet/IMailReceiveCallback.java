package com.fuhu.test.smarthubtest.middleware.componet;

import java.util.List;

public interface IMailReceiveCallback {
    public void onMailReceive(List<IMailItem> mailItemList);
}