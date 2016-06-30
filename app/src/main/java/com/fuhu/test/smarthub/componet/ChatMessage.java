package com.fuhu.test.smarthub.componet;

public class ChatMessage {
    private String sender;
    private String message;
    private long timestamp;

    public ChatMessage(String sender, String message, long timestamp){
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return this.sender + ": " + this.message;
    }

    @Override
    public int hashCode() {
        return (this.sender + this.message).hashCode();
    }
}
