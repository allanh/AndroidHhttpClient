package com.fuhu.middleware.componet;

import java.util.List;

public interface IErrorCodeList {
    public String getCode();
    public void setCode(final String errorCode);
    public String getContent();
    public void setContent(final String errorContent);
    public List<ErrorCodeItem> getItemList();
}
