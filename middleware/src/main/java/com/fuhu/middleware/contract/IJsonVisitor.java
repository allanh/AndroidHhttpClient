package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.AMailItem;

public interface IJsonVisitor {
    public AMailItem parse(IResponse response, ICommand command);
    public AMailItem parse(IHttpResponse response, ICommand command);
    public AMailItem parse(IWebRtcResponse response, ICommand command);
    public AMailItem parse(IBleResponse response, ICommand command);
}
