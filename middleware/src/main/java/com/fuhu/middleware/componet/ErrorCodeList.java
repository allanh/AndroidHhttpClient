package com.fuhu.middleware.componet;

import com.fuhu.middleware.contract.IErrorCodeList;

import java.util.ArrayList;
import java.util.List;

public enum ErrorCodeList implements IErrorCodeList {
    /**
     * declare INSTANCE of this Enum
     */
    INSTANCE,

    Default("-1","Default"),
    Success("0","success"),
    OK("1","OK"),

    UNKNOWN_EXCEPTION("-2","UNKNOWN EXCEPTION"),
    UNKNOWN_ERROR("-3","UNKNOWN ERROR"),
    NO_MAILITEM_DATA("-4","NO MAILITEM DATA"),

    /**
     * Command
     */
    COMMAND_NULL("-10", "COMMAND NULL"),
    URL_NULL("-11", "URL NULL"),
    DATA_CLASS_NULL("-12", "DATA CLASS NULL"),
    GSON_PARSE_ERROR("-13", "GSON PARSE NULL"),
    INVALID_PARAMETER("-14", "Parameter Missing or Invalid"),

    /**
     * Volley
     */
    VOLLEY_TIMEOUT("-9000", "VOLLEY TIMEOUT"),
    VOLLEY_AUTH_FAILURE("-9001", "VOLLEY AUTH FAILURE"),
    VOLLEY_SERVER_ERROR("-9002", "VOLLEY SERVER ERROR"),
    VOLLEY_NETWORK_ERROR("-9003", "VOLLEY NETWORK ERROR"),
    VOLLEY_PARSE_ERROR("-9004", "VOLLEY PARSE ERROR"),

    /**
     * PubNub
     */
    PUBNUB_ERROR("-9010", "PubnubError"),
    ;

    private String 	errorCode		 = null;
    private String 	errorContent	 = null;

    private ErrorCodeList() {}

    private ErrorCodeList(final String errorCode, final String errorContent){
        this.errorCode = errorCode;
        this.errorContent = errorContent;
    }

    private static List<ErrorCodeItem> errorCodeItemList = new ArrayList<ErrorCodeItem>();

    static{
        for(ErrorCodeList tmp: ErrorCodeList.values()){
            errorCodeItemList.add(new ErrorCodeItem(tmp.errorCode, tmp.errorContent));
        }
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

    public List<ErrorCodeItem> getItemList() {
        return errorCodeItemList;
    }
}
