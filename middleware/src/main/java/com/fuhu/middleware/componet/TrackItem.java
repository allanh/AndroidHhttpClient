package com.fuhu.middleware.componet;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackItem extends AMailItem{
    private static final String TAG= MailItem.class.getSimpleName();

    /**
     * Original Json format
     */
    private String originalJsonFormat = null;

    public void setOriginalJsonFormat(JSONObject JSONFormat){
        if (JSONFormat != null) {
            originalJsonFormat = JSONFormat.toString();
        }
    }

    public String getOriginalJsonFormat(){
        return originalJsonFormat;
    }

    /**
     * voiceTrackingList
     */
    public List<VoiceTrackingList> getVoiceTrackingList() {
        if(voiceTrackingList == null){
            voiceTrackingList = new ArrayList<VoiceTrackingList>();
        }
        return voiceTrackingList;
    }

    public void setVoiceTrackingList(List<VoiceTrackingList> voiceTrackingList) {
        this.voiceTrackingList = voiceTrackingList;
    }

    private List<VoiceTrackingList> voiceTrackingList;

    public static class VoiceTrackingList{
        public boolean isSuccess() {
            return isSuccess;
        }

        public boolean getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        private boolean isSuccess;
        private String text;
        private String response;
        private long timestamp;
    }
}
