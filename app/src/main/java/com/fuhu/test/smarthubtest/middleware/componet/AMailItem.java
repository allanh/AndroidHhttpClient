package com.fuhu.test.smarthubtest.middleware.componet;

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
}
