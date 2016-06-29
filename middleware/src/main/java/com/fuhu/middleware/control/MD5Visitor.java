package com.fuhu.middleware.control;

import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IWebRtpCommand;
import com.fuhu.middleware.contract.MD5Util;

public class MD5Visitor implements IMD5Visitor {
    private static final String TAG = MD5Visitor.class.getSimpleName();

    public String getKey(ICommand command) {
        return MD5Util.genMD5Key(command.getID());
    }

    public String getKey(IHttpCommand command) {
        return MD5Util.genMD5Key(command.getURL());
    }

    public String getKey(IWebRtpCommand command) {
        return MD5Util.genMD5Key(command.getID());
    }

    public String getKey(IBleCommand command) {
        return MD5Util.genMD5Key(command.getID());
    }
}
