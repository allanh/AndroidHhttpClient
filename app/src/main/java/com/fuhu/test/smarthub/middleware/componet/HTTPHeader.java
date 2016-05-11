package com.fuhu.test.smarthub.middleware.componet;

import android.content.Context;
import android.os.Build;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public enum HTTPHeader{
    API_key("APIKey"){
        @Override
        public String getValue(final Context context) {
            return "8ebdd190-763f-11e5-a837-0800200c9a66";
        }
    },

    PLAN_API_Key("APIKey") {
        @Override
        public String getValue(final Context context) {
            return "e85beeda-0eb6-47d8-880b-680684b92a19";
        }
    },

    Token("Token"){
        @Override
        public String getValue(final Context context) {
            return null;
        }
    },

    GCM_KEY("Authorization") {
        @Override
        public String getValue(final Context context) {
            return "key=AIzaSyDQzs8Byra6QQrMYzxbUc5XtKYQ6JDwWdk";
        }
    },

    UserToken("UserToken"){
        @Override
        public String getValue(final Context context) {
            return "e85beeda-0eb6-47d8-880b-680684b92a19";
        }
    },

    KidToken("KidToken"){
        @Override
        public String getValue(final Context context) {
            return "AIzaSyDQzs8Byra6QQrMYzxbUc5XtKYQ6JDwWdk";
        }
    },

    Track_API_Key("APIKey") {
        @Override
        public String getValue(final Context context) {
            return "46900aad65547b9e1a0922c40225eca81707ca8d794d3322cece832c226787";
        }
    },

    APP_NAME("APPName") {
        @Override
        public String getValue(final Context context) {
            return context.getPackageName();
        }
    },

    APP_Version("APPVersion") {
        @Override
        public String getValue(final Context context) {
            return "v1.0.1";
        }
    },

    OSVersion("OSVersion") {
        @Override
        public String getValue(final Context context) {
            return "android " + Build.VERSION.RELEASE;
        }
    },

    TimeZone("TimeZone") {
        @Override
        public String getValue(final Context context) {
            return Calendar.getInstance().getTimeZone().getID();
        }
    }
    ;

    private String name="",
            value="";

    private HTTPHeader(final String name){
        this.setName(name);
    }
    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    public String getValue(final Context context) {
        return null;
    }

    /**
     * get Default HTTP Header
     * @return
     */
    public static Map<String, String> getDefaultHeader() {
        Map<String, String> headerPair = new HashMap<String, String>();
        headerPair.put("Content-Type", "application/json");
        headerPair.put("Accept", "application/json");
        return headerPair;
    }

    /**
     * get HTTP Header by header key
     * @return
     */
    public static Map<String, String> getHeader(final Context context, HTTPHeader... headers) {
        Map<String, String> headerPair = getDefaultHeader();
        if (headers != null) {
            for (HTTPHeader header : headers) {
                headerPair.put(header.getName(), header.getValue(context));
            }
            return headerPair;
        }
        return null;
    }

    public static Map<String, String> getTrackingHeader(final Context context) {
        Map<String, String> headerPair = getDefaultHeader();
        headerPair.put(UserToken.getName(), UserToken.getValue(context));
        headerPair.put(KidToken.getName(), KidToken.getValue(context));
        headerPair.put(Track_API_Key.getName(), Track_API_Key.getValue(context));
        headerPair.put(APP_NAME.getName(), APP_NAME.getValue(context));
        headerPair.put(APP_Version.getName(), APP_Version.getValue(context));
        headerPair.put(OSVersion.getName(), OSVersion.getValue(context));
        headerPair.put(TimeZone.getName(), TimeZone.getValue(context));
        return headerPair;
    }
}