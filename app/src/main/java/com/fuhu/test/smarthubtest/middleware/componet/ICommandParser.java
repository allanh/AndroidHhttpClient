package com.fuhu.test.smarthubtest.middleware.componet;

import java.io.Serializable;

public interface ICommandParser extends Serializable {
    public String parseCommand(String message);
}
