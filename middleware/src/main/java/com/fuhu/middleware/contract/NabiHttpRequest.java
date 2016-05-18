package com.fuhu.middleware.contract;

public class NabiHttpRequest {
    private static final String TAG = NabiHttpRequest.class.getSimpleName();
    private static final String CHARSET_NAME = "UTF-8";
    private static final long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB

    /**
     * API URL
     */
    private static final String IFTTTServerSite = "https://maker.ifttt.com/trigger/%s/with/key/%s";
    private static final String IFTTTEvent = "aws_test";
    private static final String IFTTTKey = "bSFARZ-rJzNywhtItAh4IS";

    private static final String GCMServerSite = "https://gcm-http.googleapis.com/gcm/send";

    private static final String AWSServerSite  = ":4321/version-service/";

    private static final String TrackServerSite = "http://ec2-54-201-90-113.us-west-2.compute.amazonaws.com:8080";
    private static final String TrackServerPath = "/IITService/tracking/voicetracking";


    public static String getAPI_IFTTT() {
        return String.format(IFTTTServerSite, IFTTTEvent, IFTTTKey);
    }

    public static String getAPI_GCM() {
        return GCMServerSite;
    }

    public static String getAPI_Track() {
        return TrackServerSite + TrackServerPath;
    }
}
