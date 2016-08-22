package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.IMailItem;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AMailItem implements IMailItem {
    /** The status of the response */
    private String status;

    /** The message of the response */
    private String message;

    /** The message type for Silk SDK */
    private String type;

    /** Original Json Object */
    private JSONObject originalJSONObject = null;

    /**
     * Serializes the specified object into its equivalent representation as a tree of
     * {@link JsonElement}s.
     */
    public JsonElement toJsonTree(final Gson gson) {
        return gson.toJsonTree(this);
    }

    /**
     * Creates a new {@code JSONObject} with name/value mappings from the JSON
     * string.
     * @throws JSONException
     */
    public JSONObject toJSONObject(final Gson gson) throws JSONException {
        return new JSONObject(gson.toJson(this));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
    
    public JSONObject getOriginalJSONObject(){
        return originalJSONObject;
    }
    
    public void setOriginalJSONObject(final JSONObject jsonObject){
        Log.d("AMailItem", "set original jsonObject" + ((jsonObject != null) ? jsonObject.toString() : null));
        if (jsonObject != null) {
            originalJSONObject = jsonObject;
        }
    }
}
