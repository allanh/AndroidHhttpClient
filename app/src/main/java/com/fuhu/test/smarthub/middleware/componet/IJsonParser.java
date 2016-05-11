package com.fuhu.test.smarthub.middleware.componet;

import org.json.JSONObject;

import java.util.List;

public interface IJsonParser {
    public List<AMailItem> JSONParse(JSONObject json);
}
