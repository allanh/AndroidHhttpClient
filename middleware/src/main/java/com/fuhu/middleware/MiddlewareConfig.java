package com.fuhu.middleware;

public class MiddlewareConfig {

    // display additional info via Wings button for server team
    private static boolean isDebuggingMode = true;

    // is turn on tracking function or not
    public static final boolean isNeedToTrack = true;

    // is turn on recognizing function or not
    public static final boolean isRecognizingSpeech = true;

    // is turn on GCM function or not
//    public static final boolean enableGCM = true;

    // is turn on Wifi peer to peer function or not
//    public static final boolean enableWifiP2P = false;

    /**
     * Is turn on debug mode or not
     * @param enable
     */
    public static void enableDebugMode(final boolean enable) {
        isDebuggingMode = enable;
    }

    /**
     * Get debugging mode
     * @return
     */
    public static boolean isDebuggingMode() {
        return isDebuggingMode;
    }
}
