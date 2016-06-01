package com.fuhu.middleware.componet;

import org.json.JSONObject;

public interface IRtcCommand extends ICommand {
    public String getID();

    public Class<? extends AMailItem> getDataModel();

    public AMailItem getDataObject();

    public JSONObject getJSONObject();

    public boolean useMockData();
}
