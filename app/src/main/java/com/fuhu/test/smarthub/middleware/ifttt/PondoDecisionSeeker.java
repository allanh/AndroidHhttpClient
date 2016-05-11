package com.fuhu.test.smarthub.middleware.ifttt;

import android.content.Context;

import com.fuhu.test.smarthub.callback.TrackCallback;
import com.fuhu.test.smarthub.middleware.componet.Log;
import com.fuhu.test.smarthub.middleware.componet.TrackItem;
import com.fuhu.test.smarthub.middleware.componet.TrackItem.VoiceTrackingList;
import com.fuhu.test.smarthub.middleware.manager.TextToSpeechManager;

import java.util.ArrayList;
import java.util.List;

public class PondoDecisionSeeker extends DecisionSeeker {
    private static final String TAG = PondoDecisionSeeker.class.getSimpleName();
    private static List<PondoIFTTT> mPondoIFTTTList = new ArrayList<PondoIFTTT>();
    public static PondoDecisionSeeker INSTANCE;
    public static Context mContext;

    public synchronized static PondoDecisionSeeker getInstance(final Context context) {
        mContext = context;
        if (INSTANCE == null) {
            INSTANCE = new PondoDecisionSeeker();
        }
        return INSTANCE;
    }

//    public PondoIFTTT onSTT(final List<String> searchWords){
//        PondoIFTTT rtnType = null;
//        return rtnType;
//    }

//    private boolean found(final String keywords, final PondoIFTTT myroot){
//        return false;
//    }

    public void onTTS(final List<PondoIFTTT> myroot,final String sentence) {
        Log.d(TAG, "onTTS: " + sentence);
        // TTS
        TextToSpeechManager.getInstance(mContext).speakOut(sentence);

        // save list
        if (myroot != null) {
            mPondoIFTTTList.addAll(myroot);
        }
    }

    public void onComplete(final List<PondoIFTTT> myroot) {
        //TODO send all list to nicky
        Log.d(TAG, "send to nicky");

        if (myroot != null && myroot.size() > 0) {
            TrackItem trackItem = new TrackItem();

            for (PondoIFTTT pondoIFTTT: myroot) {
                Log.d(TAG, "Pondo: " + pondoIFTTT.getMainRecoWords() + " isFound: " + pondoIFTTT.isFound());
                VoiceTrackingList voiceTracking = new VoiceTrackingList();
                voiceTracking.setIsSuccess(pondoIFTTT.isFound());
                voiceTracking.setText(pondoIFTTT.getMainRecoWords());
//                voiceTracking.setInput(pondoIFTTT.getMainRecoWords());
                voiceTracking.setTimestamp(System.currentTimeMillis());

                trackItem.getVoiceTrackingList().add(voiceTracking);
            }

            sendToTrackingServer(trackItem);
        }

        // clear list
        mPondoIFTTTList.clear();
    }

    private void sendToTrackingServer(final TrackItem trackItem) {
        if (trackItem != null) {
            TrackCallback.reqSend(mContext, new TrackCallback() {
                @Override
                public void onResultReceived(TrackItem trackItem) {
                    Log.d(TAG, "onResultReceived");
                }

                @Override
                public void onFailed(String status, String message) {
                    Log.d(TAG, "onFailed: " + message);
                }
            }, trackItem);
        }
    }
}
