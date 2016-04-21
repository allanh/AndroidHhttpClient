package com.fuhu.test.smarthub.middleware.componet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fuhu.test.smarthub.middleware.contract.MockCommandParser;

public abstract class ARecognizeService extends Service implements IRecognizeService {
    private static final String TAG = ARecognizeService.class.getSimpleName();

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

    public abstract void createRecognizer();

    public abstract void startRecognizing();

    public abstract void stopRecognizing();
}
