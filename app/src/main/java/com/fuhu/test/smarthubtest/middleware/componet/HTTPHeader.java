package com.fuhu.test.smarthubtest.middleware.componet;

import java.util.HashMap;
import java.util.Map;

public enum HTTPHeader{
    API_key("APIKey"){
        @Override
        public String getValue() {
            return "8ebdd190-763f-11e5-a837-0800200c9a66";
        }
    },
    PLAN_API_Key("APIKey") {
        @Override
        public String getValue() {
            return "e85beeda-0eb6-47d8-880b-680684b92a19";
        }
    },
    Token("Token"){
        @Override
        public String getValue() {
            return null;
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
    public String getValue() {
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
    public static Map<String, String> getHeader(HTTPHeader... headers) {
        Map<String, String> headerPair = getDefaultHeader();
        if (headers != null) {
            for (HTTPHeader header : headers) {
                headerPair.put(header.getName(), header.getValue());
            }
            return headerPair;
        }
        return null;
    }
}
