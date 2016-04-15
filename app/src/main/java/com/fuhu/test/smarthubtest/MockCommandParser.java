package com.fuhu.test.smarthubtest;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MockCommandParser implements ICommandParser {
    private static final String TAG = MockCommandParser.class.getSimpleName();

    public String parseCommand(String text){
        String retString = "I'm sorry, Dave. I'm afraid I can't do that.";

        // find the command type by text
        CommandType commandType = CommandType.lookupByCommand(text);

        Log.d(TAG, "command: " + text + " type: " + commandType);

        if (commandType != null) {
            Calendar c = Calendar.getInstance();

            SimpleDateFormat dfDate_day;
            switch (commandType) {
                case WhatTime:
                    dfDate_day = new SimpleDateFormat("HH:mm:ss");
                    retString = "The time is " + dfDate_day.format(c.getTime());
                    break;

                case WhatDay:
                    dfDate_day = new SimpleDateFormat("dd/MM/yyyy");
                    retString = " Today is " + dfDate_day.format(c.getTime());
                    break;

                case WhoAreYou:
                    retString = "My name is Mimori - Responsive Android Language program";
                    break;

                case Exit:
                    retString = CommandType.Exit.toString();
                    break;

                default:
                    retString = text;
                    break;
            }
        } else {
            retString = text;
        }

        Log.d(TAG, "response: " + retString);
        return retString;
    }
}
