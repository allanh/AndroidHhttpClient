package com.fuhu.middleware.contract;

public interface IMD5Visitor {
    public String getKey(ICommand command);
    public String getKey(IHttpCommand command);
    public String getKey(IWebRtcCommand command);
    public String getKey(IBleCommand command);

    public String genKey(IResponse response);
    public String genKey(IHttpResponse response);
    public String genKey(IWebRtcResponse response);
    public String genKey(IBleResponse response);
}
