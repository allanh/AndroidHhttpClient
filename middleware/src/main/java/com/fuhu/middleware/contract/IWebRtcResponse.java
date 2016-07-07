package com.fuhu.middleware.contract;

public interface IWebRtcResponse extends IResponse {
    /** The message type for Silk Type */
    public String getType();

    /** The payload type for device communication */
    public PayloadType getPayloadType();
}
