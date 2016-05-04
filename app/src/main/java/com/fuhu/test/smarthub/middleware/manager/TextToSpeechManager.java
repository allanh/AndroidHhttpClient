package com.fuhu.test.smarthub.middleware.manager;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Helper to speak the string using TTS engine
 */
public class TextToSpeechManager implements TextToSpeech.OnInitListener {
    private static final String TAG = TextToSpeechManager.class.getSimpleName();

    private static TextToSpeechManager INSTANCE;

    private static TextToSpeech mTTS;

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
        if (text != null) {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void shutdown() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }
}
