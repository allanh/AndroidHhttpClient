package com.fuhu.middleware.contract;

public class Constants {
    /**
     * Recognize service
     */
    public static final String RECEIVE_RECOGNIZE_RESULT = "com.fuhu.middleware.receiveRecognizeResult";

    /**
     * WebRTC
     */
    public static final String SHARED_PREFS = "com.fuhu.middleware.SHARED_PREFS";
    public static final String USER_NAME    = "com.fuhu.middleware.SHARED_PREFS.USER_NAME";
    public static final String CALL_USER    = "com.fuhu.middleware.SHARED_PREFS.CALL_USER";

    public static final String STDBY_SUFFIX = "-stdby-fuhu";

    public static final String PUB_KEY = "pub-c-a14d123f-2443-46fe-8de1-02fc1f3c140d"; // Your Pub Key
    public static final String SUB_KEY = "sub-c-ef1bc474-263a-11e6-bfbc-02ee2ddab7fe"; // Your Sub Key

    public static final String JSON_CALL_USER = "call_user";
    public static final String JSON_CALL_TIME = "call_time";
    public static final String JSON_OCCUPANCY = "occupancy";
    public static final String JSON_STATUS    = "status";

    /**
     * JSON for user messages
     */
    public static final String JSON_USER_MSG  = "user_message";
    public static final String JSON_MSG_UUID  = "msg_uuid";
    public static final String JSON_MSG       = "msg_message";
    public static final String JSON_TIME      = "msg_timestamp";

    public static final String STATUS_AVAILABLE = "Available";
    public static final String STATUS_OFFLINE   = "Offline";
    public static final String STATUS_BUSY      = "Busy";

    /**
     * Character encoding
     */
    public static final String UTF8 = "UTF-8";
    public static final String LATIN1 = "ISO-8859-1";
    public static final String BIG5 = "BIG5";
}