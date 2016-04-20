package com.fuhu.test.smarthub.middleware.componet;

import com.fuhu.test.smarthub.middleware.contract.ErrorCodeHandler;

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
     * IFTTT
     */
    private String value1;

    private String value2;

    private String value3;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public void setValues(String [] values) {
        for (int i = 0; i < values.length; i++) {

        }
    }

}
