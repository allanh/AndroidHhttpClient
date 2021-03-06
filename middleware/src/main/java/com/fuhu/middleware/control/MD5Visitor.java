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
import com.fuhu.middleware.contract.SilkMessageType;

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
        String key = command.getSilkMessageType().toString();

        // Appends payload id
        if (command.getSilkMessageType().equals(SilkMessageType.DEVICE_MESSAGE)) {
            key += command.getPayloadType().getId();
        }
//        Log.d(TAG, "key: " + key);
        return MD5Util.genMD5Key(key);
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
        String key = response.getType();

        if (response.getType().equals(SilkMessageType.DEVICE_MESSAGE.toString())) {
            key += response.getPayloadType().getId();
        }

        return MD5Util.genMD5Key(key);
    }
    public String genKey(IBleResponse response) {
        return null;
    }
}
