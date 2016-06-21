package com.fuhu.middleware.contract;

public interface IMD5Visitor {
    public String getKey(ICommand command);
    public String getKey(IHttpCommand command);
    public String getKey(IRtpCommand command);
    public String getKey(IBleCommand command);
}
