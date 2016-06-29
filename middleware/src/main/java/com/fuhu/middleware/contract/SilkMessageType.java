package com.fuhu.middleware.contract;

import android.content.Context;

import com.fuhu.middleware.componet.AMailItem;

import java.util.HashMap;
import java.util.Map;

public enum SilkMessageType {
    /**
     * declare INSTANCE of this Enum
     */
    INSTANCE,

    /**
     * General
     */
    INITIALIZED("INITIALIZED"),

    /**
     * Account
     */
    ACCOUNT_LOGIN("ACCOUNT_LOGIN"),
    ACCOUNT_LOGOUT("ACCOUNT_LOGOUT"),

    /**
     * App status
     */
    APP_STATUS("APP_STATUS"),

    /**
     * Device management
     */
    DEVICE_LIST("DEVICE_LIST"),
    DEVICE_STATUS("DEVICE_STATUS"),
    DEVICE_CONFIG_ACCEPT("DEVICE_CONFIG_ACCEPT"),
    DEVICE_CONFIG_DECLINE("DEVICE_CONFIG_DECLINE"),
    DEVICE_CONFIG_WIFI_NETWORKS("DEVICE_CONFIG_WIFI_NETWORKS"),
    DEVICE_CONFIG_WIFI_CONFIG("DEVICE_CONFIG_WIFI_CONFIG"),
    DEVICE_CONFIG_SUCCESS("DEVICE_CONFIG_SUCCESS"),
    DEVICE_CONFIG_ABORT("DEVICE_CONFIG_ABORT"),
    DEVICE_CONFIG_ERROR("DEVICE_CONFIG_ERROR"),
    DEVICE_CONFIG_FATAL_ERROR("DEVICE_CONFIG_FATAL_ERROR"),
    DEVICE_REMOVE("DEVICE_REMOVE"),
    DEVICE_REMOVE_SUCCESS("DEVICE_REMOVE_SUCCESS"),
    DEVICE_REMOVE_ERROR("DEVICE_REMOVE_ERROR"),

    /**
     * Client management
     */
    CLIENT_LIST("CLIENT_LIST"),
    CLIENT_REMOVE("CLIENT_REMOVE"),
    CLIENT_REMOVE_ERROR("CLIENT_REMOVE_ERROR"),

    /**
     * Device communication
     */
    DEVICE_MESSAGE("DEVICE_MESSAGE"),

    /**
     * Camera LiveStream
     */
    ;

    private String 	typeCode = null;

    private SilkMessageType() {}

    private SilkMessageType(final String typeCode){
        this.typeCode = typeCode;
    }

    private static Map<String, SilkMessageType> lookupTable = new HashMap<String, SilkMessageType>();

    static{
        for(SilkMessageType tmp: SilkMessageType.values()){
            lookupTable.put(tmp.getCode(), tmp);
        }
    }

    public String getCode() {
        return typeCode;
    }

    public void setCode(final String typeCode){
        this.typeCode = typeCode;
    }

    /**
     * Finding message type for Silk SDK
     * @param typeCode
     * @return
     */
    public static SilkMessageType lookup(final String typeCode){
        return lookupTable.get(typeCode);
    }

    public Object doAction(final Context mContext, final AMailItem queryItem, final IPostOfficeProxy mPostOfficeProxy, final Object... obj) {
        return null;
    }

}
