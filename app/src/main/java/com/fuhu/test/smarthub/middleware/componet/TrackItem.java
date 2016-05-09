package com.fuhu.test.smarthub.middleware.componet;


import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

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
     * Status
     */
    private String 	status;

    public String getStatus() {
        return (status != null)? status : ErrorCodeHandler.UNKNOWN_EXCEPTION.getCode();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Message
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * VoiceTracking
     */
    public List<VoiceTracking> getVoiceTracking() {
        if(VoiceTracking == null){
            VoiceTracking = new ArrayList<VoiceTracking>();
        }
        return VoiceTracking;
    }

    public void setVoiceTracking(List<VoiceTracking> VoiceTracking) {
        this.VoiceTracking = VoiceTracking;
    }

    private List<VoiceTracking> VoiceTracking;

//    public static class VoiceTracking{
//        public boolean isSuccess() {
//            return isSuccess;
//        }
//
//        public boolean getIsSuccess() {
//            return isSuccess;
//        }
//
//        public void setIsSuccess(boolean isSuccess) {
//            this.isSuccess = isSuccess;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public long getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(long timestamp) {
//            this.timestamp = timestamp;
//        }
//
//        private boolean isSuccess;
//        private String text;
//        private long timestamp;
//    }

    public static class VoiceTracking{
        public boolean isSuccess() {
            return isSuccess;
        }

        public boolean getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public List<String> getResponse() {
            return response;
        }

        public void setResponse(List<String> response) {
            this.response = response;
        }

        public long getConfidence() {
            return confidence;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        private boolean isSuccess;
        private String input;
        private List<String> response;
        private int confidence;
        private long timestamp;
    }
}
