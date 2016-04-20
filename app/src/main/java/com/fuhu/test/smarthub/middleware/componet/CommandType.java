package com.fuhu.test.smarthub.middleware.componet;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public enum CommandType {
    Exit("exit") {
        @Override
        public String getResponse() {
            return this.toString();
        }
    },
    WhatDay("what day is it") {
        @Override
        public String getResponse() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dfDate_day;
            dfDate_day = new SimpleDateFormat("dd/MM/yyyy");
            return  " Today is " + dfDate_day.format(c.getTime());
        }
    },
    WhatTime("what time is it") {
        @Override
        public String getResponse() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dfDate_day;
            dfDate_day = new SimpleDateFormat("HH:mm:ss");
            return "The time is " + dfDate_day.format(c.getTime());
        }
    },
    WhoAreYou("who are you") {
        @Override
        public String getResponse() {
            return "My name is Mimori - Responsive Android Language program";
        }
    }
    ;

    @SuppressLint("UseSparseArrays")
    private static HashMap<String, CommandType> lookupTable  = new HashMap<String, CommandType>();

    static{
        for(CommandType tmp: CommandType.values()){
            lookupTable.put(tmp.identifier, tmp);
        }
    }

    public static CommandType lookup(final String id){
        return lookupTable.get(id);
    }

    private String identifier;

    private CommandType(final String identifier){
        this.identifier=identifier;
    }

    @Override
    public String toString(){
        return this.identifier;
    }

    public boolean equals(CommandType type) {
        if (type != null) {
            return (this.identifier.equals(type.toString()));
        } else {
            return false;
        }
    }

    public String getResponse() {
        return null;
    }

    public boolean equals(String command) {
        if (command != null) {
            return (this.identifier.equals(command));
        } else {
            return false;
        }
    }

    public boolean contains(String command) {
        return this.identifier.contains(command);
    }
}