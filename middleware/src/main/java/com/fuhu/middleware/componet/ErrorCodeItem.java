package com.fuhu.middleware.componet;

public class ErrorCodeItem extends AMailItem {
    private String 	errorCode		 = null;
    private String 	errorContent	 = null;

    public ErrorCodeItem(final String errorCode, final String errorContent){
        this.errorCode = errorCode;
        this.errorContent = errorContent;
    }

    public String getCode() {
        return errorCode;
    }

    public void setCode(final String errorCode){
        this.errorCode = errorCode;
    }

    public String getContent() {
        return errorContent;
    }

    public void setContent(final String errorContent){
        this.errorContent = errorContent;
    }
}
