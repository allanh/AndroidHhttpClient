package com.fuhu.test.smarthub.componet;

import com.fuhu.middleware.componet.AMailItem;

public class ClientItem extends AMailItem {
    /** Client Id assigned to this request  */
    private String id;

    /** The device type of the client */
    private String clientDeviceType;

    /** The Client name */
    private String clientName;

    /** When it receives a message, it pulls out the JSON_CALL_USER field, call_user */
    private String callUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientDeviceType() {
        return clientDeviceType;
    }

    public void setClientDeviceType(String clientDeviceType) {
        this.clientDeviceType = clientDeviceType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCallUser() {
        return callUser;
    }

    public void setCallUser(String callUser) {
        this.callUser = callUser;
    }
}
