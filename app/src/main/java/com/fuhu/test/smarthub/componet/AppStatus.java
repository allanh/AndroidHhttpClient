package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;

public class AppStatus extends AMailItem {
    private String clientConnectionStatus;
    private String clientConnectionError;
    private String bleStatus;

    public String getClientConnectionStatus() {
        return clientConnectionStatus;
    }

    public void setClientConnectionStatus(String clientConnectionStatus) {
        this.clientConnectionStatus = clientConnectionStatus;
    }

    public String getClientConnectionError() {
        return clientConnectionError;
    }

    public void setClientConnectionError(String clientConnectionError) {
        this.clientConnectionError = clientConnectionError;
    }

    public String getBleStatus() {
        return bleStatus;
    }

    public void setBleStatus(String bleStatus) {
        this.bleStatus = bleStatus;
    }


}
