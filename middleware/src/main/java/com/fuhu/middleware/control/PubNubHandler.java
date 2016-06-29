package com.fuhu.middleware.control;

import android.content.Context;

import com.fuhu.middleware.componet.Log;
import com.fuhu.middleware.contract.Constants;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

public class PubNubHandler {
    private static final String TAG = PubNubHandler.class.getSimpleName();
    private static PubNubHandler instance;
    private static Context mContext;
    private static Pubnub mPubNub;
    private String mCallUser;

    private long startTime;
    private static final int SOCKET_TIMEOUT = 15000;

    /*
     * A private Constructor prevents any other
     * class from instantiating.
     */
    private PubNubHandler(Context mContext) {
        if (mContext != null) {
            this.mContext = mContext.getApplicationContext();
        }

        this.mPubNub = new Pubnub(Constants.PUB_KEY, Constants.SUB_KEY);
    }

    public static PubNubHandler getInstance(Context mContext) {
        if(instance==null){
            synchronized(PubNubHandler.class) {
                if(instance==null) {
                    instance = new PubNubHandler(mContext);
                }
            }
        }
        return instance;
    }

    public static void release() {
        mContext = null;
        instance = null;
    }

    /**
     * Initialize PubNub
     */
    public void subscribe(final String clientName) {
        if (clientName != null) {
            String stdbyChannel = clientName + Constants.STDBY_SUFFIX;
            this.mPubNub.setUUID(clientName);

            try {
                this.mPubNub.subscribe(stdbyChannel, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        Log.d("MA-success", "MESSAGE: " + message.toString());
                        if (!(message instanceof JSONObject)) return; // Ignore if not JSONObject
                        JSONObject jsonMsg = (JSONObject) message;
                        try {
                            if (jsonMsg.has(Constants.JSON_CALL_USER)) {
                                // Consider Accept/Reject call here
                                mCallUser = jsonMsg.getString(Constants.JSON_CALL_USER);
//                            Intent intent = new Intent(WebRtcActivity.this, VideoChatActivity.class);
//                            intent.putExtra(Constants.USER_NAME, username);
//                            intent.putExtra(Constants.JSON_CALL_USER, user);
//                            startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //        this.mPubNub.whereNow(new Callback() {
    //            @Override
    //            public void successCallback(String channel, Object message) {
    //                super.successCallback(channel, message);
    //            }
    //        });
            } catch (PubnubException e) {
                e.printStackTrace();
            }
        }
    }
}
