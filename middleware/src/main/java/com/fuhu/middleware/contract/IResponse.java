package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.AMailItem;

import java.io.Serializable;

public interface IResponse  extends Serializable {
    public Class<? extends AMailItem> getDataModel();
    public AMailItem getDataObject();
    public String getBody();

    /**
     * Gets the MD5 Key of this request
     * @param imd5Visitor
     * @return
     */
    public String genMD5Key(IMD5Visitor imd5Visitor);
}
