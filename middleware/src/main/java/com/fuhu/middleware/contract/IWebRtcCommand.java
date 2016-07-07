package com.fuhu.middleware.contract;

import com.fuhu.middleware.componet.AMailItem;

public interface IWebRtcCommand extends ICommand {
    public SilkMessageType getSilkMessageType();
    public PayloadType getPayloadType();
    public Class<? extends AMailItem> getPayloadModel();
}
