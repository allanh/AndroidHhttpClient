package com.fuhu.middleware.contract;

import java.io.Serializable;

public interface IRecognizeService extends Serializable {
    public String recognize(String text);
    public void sendResult(final String result);
}
