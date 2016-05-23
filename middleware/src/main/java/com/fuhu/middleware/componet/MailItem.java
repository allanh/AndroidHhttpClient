package com.fuhu.middleware.componet;

import org.json.JSONObject;

public class MailItem extends AMailItem{
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
        return (status != null)? status : ErrorCodeList.UNKNOWN_EXCEPTION.getCode();
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

}
