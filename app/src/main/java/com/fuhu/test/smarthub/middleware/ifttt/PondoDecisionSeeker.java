package com.fuhu.test.smarthub.middleware.ifttt;

import android.content.Context;

import com.fuhu.test.smarthub.middleware.componet.Log;
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
        // TODO TTS
        TextToSpeechManager.getInstance(mContext).speakOut(sentence);

        // TODO save list
        if (myroot != null) {
            mPondoIFTTTList.addAll(myroot);
        }
    }

    public void onComplete(final List<PondoIFTTT> myroot) {
        //TODO send all list to nicky
        Log.d(TAG, "send to nicky");

        mPondoIFTTTList.clear();
    }
}
