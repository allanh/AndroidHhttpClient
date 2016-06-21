package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.AMailItem;

import java.io.Serializable;

public interface IResponse  extends Serializable {
    public String getURL();
    public Class<? extends AMailItem> getDataModel();
    public AMailItem getDataObject();
    public String getBody();
}
