package com.fuhu.middleware.control;

import com.fuhu.middleware.contract.IBleCommand;
import com.fuhu.middleware.contract.ICommand;
import com.fuhu.middleware.contract.ICommandVisitor;
import com.fuhu.middleware.contract.IHttpCommand;
import com.fuhu.middleware.contract.IRtpCommand;

public class CommandVisitor implements ICommandVisitor {
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
    public boolean isValid(IRtpCommand iRtpCommand) {
        return (iRtpCommand.getDataModel() != null);
    }

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(IBleCommand iBleCommand) {
        return (iBleCommand.getDataModel() != null);
    }
}
