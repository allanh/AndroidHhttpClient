package com.fuhu.test.smarthub.middleware.manager;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.fuhu.test.smarthub.middleware.componet.ITTSListener;

import java.util.Locale;

/**
 * Helper to speak the string using TTS engine
 */
public class TextToSpeechManager implements TextToSpeech.OnInitListener {
    private static final String TAG = TextToSpeechManager.class.getSimpleName();

    private static TextToSpeechManager INSTANCE;

    private static TextToSpeech mTTS;

    private ITTSListener mTTSListener;
    
    /**
     * Default constructor
     */
    public TextToSpeechManager(Context context) {
        if (mTTS == null) {
            mTTS = new TextToSpeech(context, this);
        }
    }

    public static synchronized TextToSpeechManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TextToSpeechManager(context);
        }
        return INSTANCE;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTTS.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                Log.d(TAG, "This language is not supported");
            }
        } else {
            Log.d(TAG, "Initialization Failed!");
        }
    }

    /**
     * Speaks the string using the specified queuing strategy and speech parameters.
     * @param text The string of text to be spoken
     */
    public void speakOut(String text) {
        if (mTTS != null && text != null) {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void setTtsListener() {
        if (Build.VERSION.SDK_INT >= 15) {
            int listenerResult = mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId) {
                    mTTSListener.onDone();
                }

                @Override
                public void onError(String utteranceId) {
                    mTTSListener.onError();
                }

                @Override
                public void onStart(String utteranceId) {
                    mTTSListener.onStart();
                }
            });
            if (listenerResult != TextToSpeech.SUCCESS) {
                Log.e(TAG, "failed to add utterance progress listener");
            }
        } else {
            int listenerResult = mTTS.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(String utteranceId) {
                    mTTSListener.onDone();
                }
            });
            if (listenerResult != TextToSpeech.SUCCESS) {
                Log.e(TAG, "failed to add utterance completed listener");
            }
        }
    }

    public void shutdown() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }
}
