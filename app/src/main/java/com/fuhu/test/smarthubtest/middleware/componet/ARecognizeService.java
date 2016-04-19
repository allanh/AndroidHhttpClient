package com.fuhu.test.smarthubtest.middleware.componet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fuhu.test.smarthubtest.middleware.contract.MockCommandParser;

public abstract class ARecognizeService extends Service implements IRecognizeService {
    private static final String TAG = ARecognizeService.class.getSimpleName();

    public static final String ACTION_RECEIVE_RESPONSE = "com.fuhu.test.smarthubtest.receiveResponse";

    // create a new command parser
    private static ICommandParser mCommandParser = new MockCommandParser();

    @Override
    public String recognize(String text) {
        return mCommandParser.parseCommand(text);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopRecognizing();
        super.onDestroy();
    }

    public abstract void createRecognizer();

    public abstract void startRecognizing();

    public abstract void stopRecognizing();
}
