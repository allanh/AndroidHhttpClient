package com.fuhu.test.smarthub.middleware.componet;

import java.io.Serializable;

public interface IRecognizeService extends Serializable {
    public String recognize(String text);
    public void sendResult(final String result);
}
