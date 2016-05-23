package com.fuhu.middleware.componet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AMailItem implements IMailItem{
    public JsonElement toJsonTree(Gson gson) {
        return gson.toJsonTree(this);
    }

    public JSONObject toJSONObject(Gson gson) throws JSONException {
        return new JSONObject(gson.toJson(this));
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
