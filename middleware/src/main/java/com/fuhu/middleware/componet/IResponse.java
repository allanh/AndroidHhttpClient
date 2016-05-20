package com.fuhu.middleware.componet;

import java.io.Serializable;

public interface IResponse  extends Serializable {
    public String getURL();
    public Class<? extends AMailItem> getDataModel();
    public AMailItem getDataObject();
    public String getBody();
}
