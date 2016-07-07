package com.fuhu.middleware.control;

import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.ICommandVisitor;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IWebRtcCommand;
import com.fuhu.middleware.contract.SilkMessageType;

public class CommandVisitor implements ICommandVisitor {
    private static final String TAG = CommandVisitor.class.getSimpleName();

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(ICommand iCommand) {
        return (iCommand.getDataModel() != null);
    }

    /**
     * Checks if the data model and the url has been set up
     */
    public boolean isValid(IHttpCommand iHttpCommand) {
        return (iHttpCommand.getURL() != null && iHttpCommand.getDataModel() != null);
    }

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(IWebRtcCommand iRtpCommand) {
        SilkMessageType type = iRtpCommand.getSilkMessageType();
        boolean result = (type != null && iRtpCommand.getDataModel() != null);

        /** Checks if payload type is exist */
        if (SilkMessageType.DEVICE_MESSAGE.equals(type)) {
            result = (iRtpCommand.getPayloadType() != null);
        }

        return result;
    }

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(IBleCommand iBleCommand) {
        return (iBleCommand.getDataModel() != null);
    }
}
