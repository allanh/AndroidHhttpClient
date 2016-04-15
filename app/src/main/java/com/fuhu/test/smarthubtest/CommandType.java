package com.fuhu.test.smarthubtest;

import android.annotation.SuppressLint;

import java.util.HashMap;

public enum CommandType {
    Exit("0", "exit"),
    WhatDay("1", "what day is it"),
    WhatTime("2", "what time is it"),
    WhoAreYou("3", "who are you")
    ;

    @SuppressLint("UseSparseArrays")
    private static HashMap<String, CommandType> lookupTable  = new HashMap<String, CommandType>();

    static{
        for(CommandType tmp: CommandType.values()){
            lookupTable.put(tmp._id, tmp);
        }
    }

    public static CommandType lookup(final String id){
        return lookupTable.get(id);
    }

    private String _id="-1";
    private String identifier;

    private CommandType(final String id, final String identifier){
        this._id = id;
        this.identifier=identifier;
    }

    @Override
    public String toString(){
        return this.identifier;
    }

    public String getID() {
        return _id;
    }

    public boolean equals(CommandType type) {
        if (type != null) {
            return (this.identifier.equals(type.toString()));
        } else {
            return false;
        }
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

    public static CommandType lookupByCommand(String command) {
        if (command != null) {
            for (String id : lookupTable.keySet()) {
                CommandType type = lookup(id);
                if (type.contains(command)) {
                    return type;
                }
            }
        }

        return null;
    }
}