package com.fuhu.middleware.contract;

public interface ICommandVisitor {
    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(ICommand iCommand);

    /**
     * Checks if the data model and the url has been set up
     */
    public boolean isValid(IHttpCommand iHttpCommand);

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(IWebRtcCommand iRtpCommand);

    /**
     * Checks if the data model have been set up
     */
    public boolean isValid(IBleCommand iBleCommand);
}
