package com.fuhu.middleware.componet;

import android.util.Log;


public class MockCommandParser {
    private static final String TAG = MockCommandParser.class.getSimpleName();

    public String parseCommand(String text){
        String retString = "I'm sorry, Dave. I'm afraid I can't do that.";

        // find the command type by text
        CommandType commandType = CommandType.lookup(text);

        Log.d(TAG, "command: " + text + " type: " + commandType);

        if (commandType != null) {
            retString = commandType.getResponse();
        } else {
            retString = text;
        }

        Log.d(TAG, "response: " + retString);
        return retString;
    }
}
