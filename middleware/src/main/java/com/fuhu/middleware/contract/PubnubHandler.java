package com.fuhu.middleware.contract;

import android.content.Context;

import org.webrtc.PeerConnectionFactory;

import me.kevingleason.pnwebrtc.PnRTCClient;
import me.kevingleason.pnwebrtc.PnRTCListener;

public class PubnubHandler {
    private static final String TAG = PubnubHandler.class.getSimpleName();
    private static PubnubHandler instance;
    private static Context mContext;
//    private static Pubnub mPubNub;
    private PnRTCClient mPnRTCClient;
    private PeerConnectionFactory mPcFactory;

    public PubnubHandler(Context mContext) {
        if (mContext != null) {
            this.mContext = mContext.getApplicationContext();
        }
    }

    public static PubnubHandler getInstance(Context mContext) {
        if(instance==null){
            synchronized(PubnubHandler.class) {
                if(instance==null) {
                    instance = new PubnubHandler(mContext);
                }
            }
        }
        return instance;
    }

    public void init() {
//        this.mPubNub = new Pubnub(Constants.PUB_KEY, Constants.SUB_KEY);

        PeerConnectionFactory.initializeAndroidGlobals(
                this,  // Context
                true,  // Audio Enabled
                true,  // Video Enabled
                true);  // Hardware Acceleration Enabled

        this.mPcFactory = new PeerConnectionFactory();
        this.mPnRTCClient = new PnRTCClient(Constants.PUB_KEY, Constants.SUB_KEY);
//        this.mPnRTCClient = new PnRTCClient(Constants.PUB_KEY, Constants.SUB_KEY, Constants.USER_NAME);

    }

    // VCA Code
    private class MyRTCListener extends PnRTCListener {
        // Override methods you plan on using
    }

}
