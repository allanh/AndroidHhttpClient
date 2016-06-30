package com.fuhu.middleware.control;

import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.IBleResponse;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IHttpResponse;
import com.fuhu.middleware.contract.IMD5Visitor;
import com.fuhu.middleware.contract.IResponse;
import com.fuhu.middleware.contract.IWebRtcCommand;
import com.fuhu.middleware.contract.IWebRtcResponse;
import com.fuhu.middleware.contract.MD5Util;

public class MD5Visitor implements IMD5Visitor {
    private static final String TAG = MD5Visitor.class.getSimpleName();

    /**
     * Gets MD5 key for the command
     */
    public String getKey(ICommand command) {
        return null;
    }

    public String getKey(IHttpCommand command) {
        return MD5Util.genMD5Key(command.getURL());
    }

    public String getKey(IWebRtcCommand command) {
        return MD5Util.genMD5Key(command.getSilkMessageType().toString());
    }

    public String getKey(IBleCommand command) {
        return null;
    }


    /**
     * Generates MD5 key for the response
     */
    public String genKey(IResponse response) {
        return null;
    }
    public String genKey(IHttpResponse response) {
        return  MD5Util.genMD5Key(response.getURL());
    }
    public String genKey(IWebRtcResponse response) {
        return MD5Util.genMD5Key(response.getType());
    }
    public String genKey(IBleResponse response) {
        return null;
    }
}
