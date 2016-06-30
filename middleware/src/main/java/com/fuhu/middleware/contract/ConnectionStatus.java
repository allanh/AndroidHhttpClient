package com.fuhu.middleware.contract;


import java.util.HashMap;
import java.util.Map;

public enum ConnectionStatus {
    NOT_CONNECTED("NOT_CONNECTED"),
    CONNECTING("CONNECTING"),
    CONNECTED("CONNECTED");

    private String status;
    private ConnectionStatus(String status) {
        this.status = status;
    }

    private static Map<String, ConnectionStatus> lookupTable = new HashMap<String, ConnectionStatus>();

    static{
        for(ConnectionStatus tmp: ConnectionStatus.values()){
            lookupTable.put(tmp.toString(), tmp);
        }
    }

    public String toString() {
        return status;
    }

    /**
     * Finding the connection status
     */
    public static ConnectionStatus lookup(final String connectionStatus){
        return lookupTable.get(connectionStatus);
    }

}
